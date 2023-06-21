package cs3500.pa05.view.manager;

import cs3500.pa05.enums.DayOfWeek;
import cs3500.pa05.model.ScheduleItem;
import cs3500.pa05.model.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.Callback;

/**
 * Abstract class for editing or creating a task.
 */
public abstract class TaskManagerView extends ItemManagerView {
  protected void validateInput(String name, DayOfWeek day, javafx.event.ActionEvent ev) {
    if (name.isEmpty() || day == null) {
      ev.consume();
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setContentText("Please fill all fields!");
      alert.showAndWait();
    }
  }

  protected Callback<ButtonType, ScheduleItem> createResultConverter(TextField nameField,
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
}
