package cs3500.pa05.view.manager;

import cs3500.pa05.model.ScheduleItem;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

/**
 * An item manager view which extends dialog and is meant for editing or creating items.
 */
public abstract class ItemManagerView extends Dialog<ScheduleItem> {
  protected DialogPane setupDialogPane() {
    DialogPane dialogPane = new DialogPane();
    setDialogPane(dialogPane);
    return dialogPane;
  }

  protected void fillFieldsAlert() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setContentText("Please fill all fields!");
    alert.showAndWait();
  }
}
