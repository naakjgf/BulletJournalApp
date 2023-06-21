package cs3500.pa05.view;

import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Represents a password request dialogue.
 */
public class PasswordView extends Dialog<String> {
  private PasswordField passwordField;
  private PasswordField confirmPasswordField;
  private boolean newPassword;

  /**
   * Constructor for a password view.
   */
  public PasswordView() {
    newPassword = false;
  }

  /**
   * Initializes the password view with the appropriate JavaFX components.
   */
  private void initializePasswordView() {
    setTitle("Password");
    setHeaderText("Please enter password.");

    passwordField = new PasswordField();
    passwordField.setPromptText("Password");

    VBox vbox = new VBox();
    vbox.getChildren().add(createHbox(passwordField));

    if (newPassword) {
      confirmPasswordField = new PasswordField();
      confirmPasswordField.setPromptText("Confirm Password");
      vbox.getChildren().add(createHbox(confirmPasswordField));
    }

    getDialogPane().setContent(vbox);
    Platform.runLater(() -> passwordField.requestFocus());
    setConverter();
  }

  /**
   * Adds the needed elements to close the PasswordView.
   */
  private void setConverter() {
    ButtonType passwordButtonType = new ButtonType("Okay", ButtonData.OK_DONE);
    getDialogPane().getButtonTypes().addAll(passwordButtonType, ButtonType.CANCEL);

    setResultConverter(dialogButton -> {
      if (dialogButton == passwordButtonType) {
        return passwordField.getText();
      }
      return null;
    });
  }

  /**
   * Creates an HBox with the given password field.
   *
   * @param passwordField Password field to add to HBox.
   * @return HBox with password field.
   */
  private HBox createHbox(PasswordField passwordField) {
    HBox hbox = new HBox();
    hbox.getChildren().add(passwordField);
    hbox.setPadding(new Insets(20));
    HBox.setHgrow(passwordField, Priority.ALWAYS);
    return hbox;
  }

  /**
   * Requests a password from the user using the dialogue.
   *
   * @param newPassword Whether to request a new password
   *                    (Should there be a confirmation password or not)
   * @return Password
   */
  public String requestPassword(boolean newPassword) {
    this.newPassword = newPassword;
    initializePasswordView();
    Optional<String> result;
    do {
      result = showAndWait();
      if (result.isEmpty()) {
        return "";
      }
      String password = result.get();
      if (newPassword && !isValidPassword(password)) {
        showWarningAlert();
      }
    } while (newPassword && !isValidPassword(result.get()));

    return result.get();
  }

  /**
   * Checks if the given password is valid.
   *
   * @param password Password to check.
   * @return Whether the password is valid.
   */
  private boolean isValidPassword(String password) {
    if (password.length() < 8) {
      return false;
    }
    if (newPassword && !password.equals(confirmPasswordField.getText())) {
      return false;
    }
    return true;
  }

  /**
   * Shows a warning alert if password requirements are not met.
   */
  private void showWarningAlert() {
    Alert alert = new Alert(AlertType.WARNING);
    alert.setTitle("Password Requirement Not Met");
    alert.setHeaderText(null);
    String warningMessage =
        newPassword ? "Passwords must be longer than 7 characters and match." :
            "Your password must be longer than 7 characters.";
    alert.setContentText(warningMessage);
    alert.showAndWait();
  }
}
