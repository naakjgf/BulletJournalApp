package cs3500.pa05.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class SchedulePopup extends Alert {
  public SchedulePopup(AlertType alertType, String contentText,
                       ButtonType... buttons) {
    super(alertType, contentText, buttons);
  }
}
