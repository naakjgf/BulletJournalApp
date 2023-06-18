package cs3500.pa05.controller;

import cs3500.pa05.enums.DayOfWeek;
import cs3500.pa05.model.Event;
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
import javafx.util.StringConverter;

public class ItemCreationController {

  /**
   * Constructs a dialogue for task creation, shows it to the user, and then passes the created task back up to the caller.
   *
   * @param f Consumer callback function to pass Task back up to.
   */
  public void createNewTask(Consumer<Task> f) {
    Dialog<Task> dialog = new Dialog<>();
    dialog.setTitle("Create a new Task");

    DialogPane dialogPane = new DialogPane();
    dialog.setDialogPane(dialogPane);

    TextField nameField = new TextField();
    nameField.setPromptText("Name");

    TextField descriptionField = new TextField();
    descriptionField.setPromptText("Description");

    ComboBox<DayOfWeek> dayOfWeekComboBox = new ComboBox<>();
    dayOfWeekComboBox.getItems().addAll(DayOfWeek.values());

    ButtonType createBtnType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
    dialogPane.getButtonTypes().addAll(createBtnType, ButtonType.CANCEL);

    dialogPane.lookupButton(createBtnType).addEventFilter(javafx.event.ActionEvent.ACTION, ev -> {
      String name = nameField.getText();
      String description = descriptionField.getText();
      DayOfWeek day = dayOfWeekComboBox.getValue();

      if (name.isEmpty() || description.isEmpty() || day == null) {
        ev.consume();  // stop the dialog from closing
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Please fill all fields!");
        alert.showAndWait();
      }
    });

    dialog.setResultConverter(buttonType -> {
      if (buttonType == createBtnType) {
        String name = nameField.getText();
        String description = descriptionField.getText();
        DayOfWeek day = dayOfWeekComboBox.getValue();

        return new Task(name, description, day);
      }
      return null;
    });

    VBox dialogVbox = new VBox(10);
    dialogVbox.setPadding(new Insets(20, 20, 20, 20));
    dialogVbox.getChildren().addAll(nameField, descriptionField, dayOfWeekComboBox);

    dialogPane.setContent(dialogVbox);

    Optional<Task> result = dialog.showAndWait();

    result.ifPresent(f);
  }

  /**
   * Constructs a dialogue for event creation, shows it to the user, and then passes the created event back up to the caller.
   *
   * @param f Consumer callback function to pass Event back up to.
   */
  public void createNewEvent(Consumer<Event> f) {
    Dialog<Event> dialog = new Dialog<>();
    dialog.setTitle("Create a new Event");

    DialogPane dialogPane = new DialogPane();
    dialog.setDialogPane(dialogPane);

    TextField nameField = new TextField();
    nameField.setPromptText("Name");

    TextField descriptionField = new TextField();
    descriptionField.setPromptText("Description");

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

    TextField durationField = new TextField();
    durationField.setPromptText("Duration (in minutes)");

    ComboBox<DayOfWeek> dayOfWeekComboBox = new ComboBox<>();
    dayOfWeekComboBox.getItems().addAll(DayOfWeek.values());

    ButtonType createBtnType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
    dialogPane.getButtonTypes().addAll(createBtnType, ButtonType.CANCEL);

    dialogPane.lookupButton(createBtnType).addEventFilter(javafx.event.ActionEvent.ACTION, ev -> {
      String name = nameField.getText();
      String description = descriptionField.getText();
      DayOfWeek day = dayOfWeekComboBox.getValue();
      LocalDate startTime = startTimePicker.getValue();

      long duration;
      try {
        duration = Long.parseLong(durationField.getText());
      } catch (NumberFormatException ex) {
        ev.consume();  // stop the dialog from closing
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Invalid number format for duration!");
        alert.showAndWait();
        return;
      }

      if (name.isEmpty() || description.isEmpty() || day == null || startTime == null) {
        ev.consume();  // stop the dialog from closing
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Please fill all fields!");
        alert.showAndWait();
      }
    });

    dialog.setResultConverter(buttonType -> {
      if (buttonType == createBtnType) {
        String name = nameField.getText();
        String description = descriptionField.getText();
        DayOfWeek day = dayOfWeekComboBox.getValue();
        Instant startTime = startTimePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant();
        long duration = Long.parseLong(durationField.getText());

        return new Event(name, description, day, startTime.getEpochSecond(), duration);
      }
      return null;
    });

    VBox dialogVbox = new VBox(10);
    dialogVbox.setPadding(new Insets(20, 20, 20, 20));
    dialogVbox.getChildren().addAll(nameField, descriptionField, dayOfWeekComboBox, startTimePicker, durationField);

    dialogPane.setContent(dialogVbox);

    Optional<Event> result = dialog.showAndWait();

    result.ifPresent(f);
  }
}
