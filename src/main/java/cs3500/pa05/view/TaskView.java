package cs3500.pa05.view;

import cs3500.pa05.enums.DayOfWeek;
import cs3500.pa05.enums.ItemAction;
import cs3500.pa05.model.Task;
import java.util.function.Consumer;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Represents a Task object on the main Schedule area.
 */
public class TaskView extends VBox {

  /**
   * Constructor for the TaskView.
   *
   * @param task     Task object to construct view for.
   * @param callback Callback with an ItemAction performed on the item.
   */
  public TaskView(Task task, Consumer<ItemAction> callback) {
    Label nameLabel = new Label(task.getName());
    nameLabel.setFont(new Font("Roboto", 16));
    nameLabel.getStyleClass().add("label-hover-effect");

    CheckBox checkBox = new CheckBox();
    checkBox.setSelected(task.isComplete());
    checkBox.setOnAction(e -> {
      CheckBox cb = (CheckBox) e.getSource();
      task.setCompletion(cb.isSelected());
      callback.accept(ItemAction.CLOSE);
    });

    BorderPane titlePane = new BorderPane();
    titlePane.setLeft(nameLabel);
    titlePane.setRight(checkBox);

    Label descLabel = new Label(task.getDescription());

    this.getStyleClass().add("task");
    this.getChildren().addAll(titlePane, descLabel);
    nameLabel.setOnMouseClicked((MouseEvent e) -> {
      ScheduleItemAlert alert = new ScheduleItemAlert(task, callback);
      alert.show();
    });
  }
}
