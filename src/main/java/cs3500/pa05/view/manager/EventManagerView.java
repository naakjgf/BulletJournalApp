package cs3500.pa05.view.manager;

import cs3500.pa05.enums.DayOfWeek;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.ScheduleItem;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;


/**
 * Abstract class for editing or creating an event.
 */
public abstract class EventManagerView extends ItemManagerView {

  /**
   * Populates and creates a time picker with the local time.
   *
   * @param startTime start time of the event.
   * @return A spinner of local time.
   */
  protected Spinner<LocalTime> setupTimePicker(Optional<Long> startTime) {
    Spinner<LocalTime> timeSpinner = createTimePicker(); // the method we previously defined
    startTime.ifPresent(time -> {
      LocalTime localTime =
          Instant.ofEpochSecond(time).atZone(ZoneId.systemDefault()).toLocalTime();
      timeSpinner.getValueFactory().setValue(localTime);
    });
    return timeSpinner;
  }

  /**
   * Creates a time picker for editing an event's start time.
   *
   * @return A spinner of local time.
   */
  protected Spinner<LocalTime> createTimePicker() {
    Spinner<LocalTime> timeSpinner = new Spinner<>();
    timeSpinner.setValueFactory(
        new SpinnerValueFactory<LocalTime>() {
          {
            setConverter(new LocalTimeStringConverter(FormatStyle.SHORT));
          }

          @Override
          public void decrement(int steps) {
            try {
              LocalTime time = getValue();

              setValue(time.minusMinutes(steps));
            } catch (Exception e) {
              setValue(LocalTime.now());
            }
          }

          @Override
          public void increment(int steps) {
            try {
              LocalTime time = getValue();

              setValue(time.plusMinutes(steps));
            } catch (Exception e) {
              setValue(LocalTime.now());
            }
          }
        });

    timeSpinner.getValueFactory().setValue(LocalTime.now());

    return timeSpinner;
  }

  /**
   * Validates the input for creating or editing an event.
   *
   * @param name name of the event
   * @param day day of the week for the event
   * @param date date of the event
   * @param time time of the event
   * @param duration duration of the event
   * @param ev event that triggered the validation
   */
  protected void validateInput(String name, DayOfWeek day, LocalDate date, LocalTime time,
                               String duration,
                               javafx.event.ActionEvent ev) {
    if (name.isEmpty() || day == null || date == null || time == null) {
      ev.consume();
      fillFieldsAlert();
    }

    try {
      Long.parseLong(duration);
    } catch (NumberFormatException ex) {
      ev.consume();
      fillFieldsAlert();
    }
  }

  /**
   * Sets up the date picker for the event manager view.
   *
   * @param epochTimeOpt optional epoch time
   * @return date picker for the event manager view
   */
  protected DatePicker setupDatePicker(Optional<Long> epochTimeOpt) {
    DatePicker startTimePicker = new DatePicker();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    startTimePicker.setConverter(new StringConverter<>() {
      @Override
      public String toString(LocalDate localDate) {
        return (localDate != null) ? formatter.format(localDate) : "";
      }

      @Override
      public LocalDate fromString(String string) {
        return (string != null && !string.isEmpty())
            ? LocalDate.parse(string, formatter)
            : null;
      }
    });
    startTimePicker.setPromptText("Start Date");

    epochTimeOpt.ifPresent(epochTime -> {
      LocalDate date =
          Instant.ofEpochSecond(epochTime).atZone(ZoneId.systemDefault()).toLocalDate();
      startTimePicker.setValue(date);
    });

    return startTimePicker;
  }


  /**
   * Sets up the time picker for the event manager view.
   *
   * @param nameField name field for the event manager view.
   * @param descriptionField description field for the event manager view.
   * @param dayOfWeekComboBox day of week combo box for the event manager view.
   * @param startDatePicker start date picker for the event manager view.
   * @param startTimePicker start time picker for the event manager view.
   * @param durationField duration field for the event manager view.
   * @param createBtnType create button of a specific type for the event manager view.
   * @return callback for the event manager view.
   */
  protected Callback<ButtonType, ScheduleItem> createResultConverter(TextField nameField,
      TextField descriptionField,
      ComboBox<DayOfWeek> dayOfWeekComboBox,
      DatePicker startDatePicker,
      Spinner<LocalTime> startTimePicker,
      TextField durationField,
      ButtonType createBtnType) {
    return buttonType -> {
      if (buttonType == createBtnType) {
        LocalDate startDate = startDatePicker.getValue();
        LocalTime startTime = startTimePicker.getValue();
        Instant startInstant =
            LocalDateTime.of(startDate, startTime).atZone(ZoneId.systemDefault()).toInstant();
        return new Event(nameField.getText(), descriptionField.getText(),
            dayOfWeekComboBox.getValue(), startInstant.getEpochSecond(),
            Long.parseLong(durationField.getText()));
      }
      return null;
    };
  }
}
