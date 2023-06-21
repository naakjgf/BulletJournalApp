package cs3500.pa05.controller;

import cs3500.pa05.model.Settings;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Controller for a settings editor modal.
 */
public class SettingsController {

  /**
   * Sets up the dialog pane for a dialog.
   *
   * @param dialog Dialog to setup.
   * @param title Title of the dialog.
   * @return The dialog pane.
   */
  private DialogPane setupDialogPane(Dialog<?> dialog, String title) {
    dialog.setTitle(title);
    DialogPane dialogPane = new DialogPane();
    dialog.setDialogPane(dialogPane);

    return dialogPane;
  }

  /**
   * Opens a settings modal and edits the passed Settings object.
   *
   * @param settings Settings object to edit.
   */
  public void openSettingsModal(Settings settings) {
    VBox vbox = new VBox();
    vbox.setPadding(new Insets(10));

    Label tasksLabel = new Label("Maximum number of tasks. (0 is unlimited)");
    tasksLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    tasksLabel.setMaxWidth(180);
    tasksLabel.setWrapText(true);

    TextField tasksField = new TextField();
    tasksField.setText(Integer.toString(settings.getMaximumTasks()));

    Label eventsLabel = new Label("Maximum number of events. (0 is unlimited)");
    eventsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    eventsLabel.setMaxWidth(200);
    eventsLabel.setWrapText(true);
    eventsLabel.setPadding(new Insets(10, 0, 0, 0));

    TextField eventsField = new TextField();
    eventsField.setText(Integer.toString(settings.getMaximumEvents()));

    Dialog<String> dialog = new Dialog<>();
    DialogPane dialogPane = setupDialogPane(dialog, "Settings");

    ButtonType saveBtnType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
    dialogPane.getButtonTypes().addAll(saveBtnType, ButtonType.CANCEL);

    dialogPane.lookupButton(saveBtnType).addEventFilter(javafx.event.ActionEvent.ACTION, ev -> {
      String tasksText = tasksField.getText();
      String eventsText = eventsField.getText();
      if (validateInput(tasksText) && validateInput(eventsText)) {
        settings.setMaximumTasks(Integer.parseInt(tasksText));
        settings.setMaximumEvents(Integer.parseInt(eventsText));
        dialog.close();
      } else {
        ev.consume();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(
            "Please enter a valid number for maximum tasks and events (greater than or equal to 0)"
        );
        alert.showAndWait();
      }
    });

    VBox dialogVbox = new VBox(10);
    dialogVbox.setPadding(new Insets(20, 20, 20, 20));
    dialogVbox.getChildren().addAll(tasksLabel, tasksField, eventsLabel, eventsField);

    dialogPane.setContent(dialogVbox);

    dialog.showAndWait();
  }

  /**
   * Validates the input for a settings modal.
   *
   * @param input Input to validate.
   * @return True if the input is valid, false otherwise.
   */
  private boolean validateInput(String input) {
    try {
      int value = Integer.parseInt(input);
      return value >= 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
