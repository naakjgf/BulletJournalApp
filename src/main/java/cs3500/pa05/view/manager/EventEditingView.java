package cs3500.pa05.view.manager;

import cs3500.pa05.enums.DayOfWeek;
import cs3500.pa05.model.Event;
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
 * A view for creating events
 */
public class EventEditingView extends EventManagerView {
  private TextField nameField;
  private TextField descriptionField;
  private DatePicker startDatePicker;
  private Spinner<LocalTime> startTimePicker;
  private TextField durationField;
  private ComboBox<DayOfWeek> dayOfWeekComboBox;
  private ButtonType createBtnType;
  private final Event event;

  /**
   * EventCreationView constructor
   *
   * @param event Event to edit (will not be mutated).
   */
  public EventEditingView(Event event) {
    this.event = event;
    this.setTitle("Edit Event: " + this.event.getName());
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
   * Initializes the fields of the view to edit events.
   */
  private void initializeFields() {
    nameField = new TextField(event.getName());
    nameField.setPromptText("Event Name");
    nameField.setEditable(true);

    descriptionField = new TextField(event.getDescription());
    descriptionField.setPromptText("Description");
    descriptionField.setEditable(true);

    startDatePicker = setupDatePicker(Optional.of(event.getStartTime()));
    startDatePicker.setPromptText("Start Date");
    startDatePicker.setEditable(true);

    startTimePicker = setupTimePicker(Optional.of(event.getStartTime()));
    startTimePicker.setEditable(true);

    durationField = new TextField(String.valueOf(event.getDuration()));
    durationField.setPromptText("Duration");
    durationField.setEditable(true);

    dayOfWeekComboBox = new ComboBox<>();
    dayOfWeekComboBox.getItems().addAll(DayOfWeek.values());
    dayOfWeekComboBox.setValue(event.getDayOfWeek());

    createBtnType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
  }
}
