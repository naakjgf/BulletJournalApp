package cs3500.pa05.view.manager;

import cs3500.pa05.model.ScheduleItem;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

/**
 * An item manager view which extends dialog and is meant for editing or creating items.
 */
public abstract class ItemManagerView extends Dialog<ScheduleItem> {

  /**
   * Sets up the dialog pane for the item manager view.
   *
   * @return dialog pane for the item manager view
   */
  protected DialogPane setupDialogPane() {
    DialogPane dialogPane = new DialogPane();
    setDialogPane(dialogPane);
    return dialogPane;
  }

  /**
   * Creates a warning alert and gives it text until the user gets rid of it.
   */
  protected void fillFieldsAlert() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setContentText("Please fill all fields!");
    alert.showAndWait();
  }
}
