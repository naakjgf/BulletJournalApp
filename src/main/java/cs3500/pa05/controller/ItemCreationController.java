package cs3500.pa05.controller;

import cs3500.pa05.enums.DayOfWeek;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.ScheduleItem;
import cs3500.pa05.model.Task;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class ItemCreationController {

  private DialogPane setupDialogPane(Dialog<?> dialog, String title) {
    dialog.setTitle(title);
    DialogPane dialogPane = new DialogPane();
    dialog.setDialogPane(dialogPane);

    return dialogPane;
  }

  private void validateInput(String name, String description, DayOfWeek day,
                             javafx.event.ActionEvent ev) {
    if (name.isEmpty() || description.isEmpty() || day == null) {
      ev.consume();
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setContentText("Please fill all fields!");
      alert.showAndWait();
    }
  }

  public void editItem(ScheduleItem item) {
    // TODO
  }

  /**
   * Constructs a dialogue for task creation and passes the task back using a Consumer.
   * @param f Consumer to callback with created task.
   */
  public void createNewTask(Consumer<Task> f) {
    Dialog<Task> dialog = new Dialog<>();
    DialogPane dialogPane = setupDialogPane(dialog, "Create a new Task");

    TextField nameField = new TextField();
    nameField.setPromptText("Name");

    TextField descriptionField = new TextField();
    descriptionField.setPromptText("Description");

    ComboBox<DayOfWeek> dayOfWeekComboBox = new ComboBox<>();
    dayOfWeekComboBox.getItems().addAll(DayOfWeek.values());

    ButtonType createBtnType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
    dialogPane.getButtonTypes().addAll(createBtnType, ButtonType.CANCEL);

    dialogPane.lookupButton(createBtnType).addEventFilter(javafx.event.ActionEvent.ACTION, ev -> {
      validateInput(nameField.getText(), descriptionField.getText(), dayOfWeekComboBox.getValue(),
          ev);
    });

    dialog.setResultConverter(
        createTaskResultConverter(nameField, descriptionField, dayOfWeekComboBox, createBtnType));

    VBox dialogVbox = new VBox(10);
    dialogVbox.setPadding(new Insets(20, 20, 20, 20));
    dialogVbox.getChildren().addAll(nameField, descriptionField, dayOfWeekComboBox);

    dialogPane.setContent(dialogVbox);

    Optional<Task> result = dialog.showAndWait();

    result.ifPresent(f);
  }

  private Callback<ButtonType, Task> createTaskResultConverter(TextField nameField,
                                                               TextField descriptionField,
                                                               ComboBox<DayOfWeek> dayOfWeekComboBox,
                                                               ButtonType createBtnType) {
    return buttonType -> {
      if (buttonType == createBtnType) {
        return new Task(nameField.getText(), descriptionField.getText(),
            dayOfWeekComboBox.getValue());
      }
      return null;
    };
  }

  /**
   * Constructs a dialogue for event creation and passes the event back using a Consumer.
   * @param f Consumer to callback with created event.
   */
  public void createNewEvent(Consumer<Event> f) {
    Dialog<Event> dialog = new Dialog<>();
    DialogPane dialogPane = setupDialogPane(dialog, "Create a new Event");

    TextField nameField = new TextField();
    nameField.setPromptText("Name");

    TextField descriptionField = new TextField();
    descriptionField.setPromptText("Description");

    DatePicker startTimePicker = setupDatePicker();

    TextField durationField = new TextField();
    durationField.setPromptText("Duration (in minutes)");

    ComboBox<DayOfWeek> dayOfWeekComboBox = new ComboBox<>();
    dayOfWeekComboBox.getItems().addAll(DayOfWeek.values());

    ButtonType createBtnType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
    dialogPane.getButtonTypes().addAll(createBtnType, ButtonType.CANCEL);

    dialogPane.lookupButton(createBtnType).addEventFilter(javafx.event.ActionEvent.ACTION, ev -> {
      validateInput(nameField.getText(), descriptionField.getText(), dayOfWeekComboBox.getValue(),
          ev);

      try {
        Long.parseLong(durationField.getText());
      } catch (NumberFormatException ex) {
        ev.consume();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Invalid number format for duration!");
        alert.showAndWait();
      }
    });

    dialog.setResultConverter(
        createEventResultConverter(nameField, descriptionField, dayOfWeekComboBox, startTimePicker,
            durationField, createBtnType));

    VBox dialogVbox = new VBox(10);
    dialogVbox.setPadding(new Insets(20, 20, 20, 20));
    dialogVbox.getChildren()
        .addAll(nameField, descriptionField, dayOfWeekComboBox, startTimePicker, durationField);

    dialogPane.setContent(dialogVbox);

    Optional<Event> result = dialog.showAndWait();

    result.ifPresent(f);
  }

  private Callback<ButtonType, Event> createEventResultConverter(TextField nameField,
                                                                 TextField descriptionField,
                                                                 ComboBox<DayOfWeek> dayOfWeekComboBox,
                                                                 DatePicker startTimePicker,
                                                                 TextField durationField,
                                                                 ButtonType createBtnType) {
    return buttonType -> {
      if (buttonType == createBtnType) {
        Instant startTime =
            startTimePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant();
        return new Event(nameField.getText(), descriptionField.getText(),
            dayOfWeekComboBox.getValue(), startTime.getEpochSecond(),
            Long.parseLong(durationField.getText()));
      }
      return null;
    };
  }

  private DatePicker setupDatePicker() {
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
    return startTimePicker;
  }
}

