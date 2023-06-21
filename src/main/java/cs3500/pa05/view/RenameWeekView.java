package cs3500.pa05.view;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Dialog for renaming a week.
 */
public class RenameWeekView extends Dialog<String> {
  private final TextField nameField;

  /**
   * Constructor for RenameWeekView.
   */
  public RenameWeekView() {
    DialogPane dialogPane = new DialogPane();
    this.setTitle("Set week name");
    this.setDialogPane(dialogPane);

    this.nameField = new TextField();
    this.nameField.setPromptText("New Name");

    setConverter();

    VBox renameVbox = new VBox();
    renameVbox.getChildren().add(this.nameField);


    dialogPane.setContent(renameVbox);
  }

  /**
   * Adds the needed elements to close the rename week view.
   */
  private void setConverter() {
    ButtonType passwordButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);

    getDialogPane().getButtonTypes().add(passwordButtonType);

    setResultConverter(dialogButton -> {
      if (dialogButton == passwordButtonType) {
        return nameField.getText();
      }
      return null;
    });
  }
}
