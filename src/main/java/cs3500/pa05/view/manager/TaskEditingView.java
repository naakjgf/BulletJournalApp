package cs3500.pa05.view.manager;

import cs3500.pa05.enums.DayOfWeek;
import cs3500.pa05.model.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * A dialog to edit a task.
 */
public class TaskEditingView extends TaskManagerView {
  private TextField nameField;
  private TextField descriptionField;
  private ComboBox<DayOfWeek> dayOfWeekComboBox;
  private ButtonType createBtnType;
  private final Task task;

  /**
   * TaskEditingView constructor.
   *
   * @param task Task to edit (will not be mutated).
   */
  public TaskEditingView(Task task) {
    this.task = task;
    this.setTitle("Create a new Task");
    DialogPane dialogPane = this.setupDialogPane();

    // Init dialog fields
    initializeFields();

    // Create buttons
    dialogPane.getButtonTypes().addAll(createBtnType, ButtonType.CANCEL);

    // Validate input
    dialogPane.lookupButton(createBtnType).addEventFilter(ActionEvent.ACTION,
        ev -> validateInput(nameField.getText(), dayOfWeekComboBox.getValue(), ev));

    // Set result converter
    setResultConverter(
        createResultConverter(nameField, descriptionField, dayOfWeekComboBox, createBtnType));

    // Create dialog Vbox
    VBox dialogVbox = new VBox(10);
    dialogVbox.setPadding(new Insets(20, 20, 20, 20));
    dialogVbox.getChildren().addAll(nameField, descriptionField, dayOfWeekComboBox);

    dialogPane.setContent(dialogVbox);
  }

  /**
   * Initializes the fields for task editting.
   */
  private void initializeFields() {
    nameField = new TextField(task.getName());
    nameField.setPromptText("Task Name");
    nameField.setEditable(true);

    descriptionField = new TextField(task.getDescription());
    descriptionField.setPromptText("Description");
    descriptionField.setEditable(true);

    dayOfWeekComboBox = new ComboBox<>();
    dayOfWeekComboBox.getItems().addAll(DayOfWeek.values());
    dayOfWeekComboBox.setPromptText("Day of Week");
    dayOfWeekComboBox.setValue(task.getDayOfWeek());

    createBtnType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
  }
}
