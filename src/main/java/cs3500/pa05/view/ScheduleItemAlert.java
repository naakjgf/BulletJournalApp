package cs3500.pa05.view;

import cs3500.pa05.enums.ItemAction;
import cs3500.pa05.model.ScheduleItem;
import java.util.function.Consumer;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ScheduleItemAlert extends Dialog<Void> {
  public ScheduleItemAlert(ScheduleItem item, Consumer<ItemAction> callback) {
    super();
    setTitle(item.getName());
    setHeaderText(null);

    // Create buttons
    ButtonType editButtonType = new ButtonType("Edit", ButtonBar.ButtonData.NO);
    ButtonType deleteButtonType = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
    ButtonType closeButtonType = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
    getDialogPane().getButtonTypes().addAll(editButtonType, deleteButtonType, closeButtonType);

    // Set Content
    Label label = new Label(item.getDescription());
    label.setWrapText(true);
    GridPane grid = new GridPane();
    grid.add(label, 0, 0);
    getDialogPane().setContent(grid);
  }
}