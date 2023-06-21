package cs3500.pa05.view.manager;

import cs3500.pa05.enums.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * A dialog for creating events
 */
public class EventCreationView extends EventManagerView {
  private TextField nameField;
  private TextField descriptionField;
  private DatePicker startDatePicker;
  private Spinner<LocalTime> startTimePicker;
  private TextField durationField;
  private ComboBox<DayOfWeek> dayOfWeekComboBox;
  private ButtonType createBtnType;


  /**
   * EventCreationView constructor
   */
  public EventCreationView() {
    this.setTitle("Create a new Event");
    DialogPane dialogPane = this.setupDialogPane();

    // Init dialog fields
    initializeFields();

    // Create buttons
    dialogPane.getButtonTypes().addAll(createBtnType, ButtonType.CANCEL);

    // Validate input
    dialogPane.lookupButton(createBtnType).addEventFilter(ActionEvent.ACTION, ev ->
        validateInput(nameField.getText(), dayOfWeekComboBox.getValue(), startDatePicker.getValue(),
            startTimePicker.getValue(), durationField.getText(), ev));

    // Set result converter
    setResultConverter(
        createResultConverter(nameField, descriptionField, dayOfWeekComboBox, startDatePicker,
            startTimePicker, durationField, createBtnType));

    // Create dialog Vbox
    VBox dialogVbox = new VBox(10);
    dialogVbox.setPadding(new Insets(20, 20, 20, 20));
    dialogVbox.getChildren()
        .addAll(nameField, descriptionField, dayOfWeekComboBox, startDatePicker, startTimePicker,
            durationField);

    dialogPane.setContent(dialogVbox);
  }

  /**
   * Initializes the fields for the event creation view.
   */
  private void initializeFields() {
    nameField = new TextField();
    nameField.setPromptText("Event Name");

    descriptionField = new TextField();
    descriptionField.setPromptText("Description");

    startDatePicker = setupDatePicker(Optional.empty());
    startDatePicker.setPromptText("Start Date");

    startTimePicker = createTimePicker();
    startTimePicker.setEditable(true);

    durationField = new TextField();
    durationField.setPromptText("Duration (minutes)");

    dayOfWeekComboBox = new ComboBox<>();
    dayOfWeekComboBox.getItems().addAll(DayOfWeek.values());
    dayOfWeekComboBox.setPromptText("Day of Week");

    createBtnType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
  }
}
