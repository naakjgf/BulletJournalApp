package cs3500.pa05.view;

import cs3500.pa05.enums.DayOfWeek;
import cs3500.pa05.enums.ItemAction;
import cs3500.pa05.model.ScheduleItem;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Consumer;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import cs3500.pa05.model.Task;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TaskView extends VBox {
  private String id;
  private DayOfWeek dayOfWeek;

  public TaskView(Task task, Consumer<ItemAction> callback) {
    id = task.getId();
    this.dayOfWeek = task.getDayOfWeek();
    Label nameLabel = new Label(task.getName());
    nameLabel.setFont(new Font("Roboto", 16));
    nameLabel.getStyleClass().add("label-hover-effect");

    CheckBox checkBox = new CheckBox();
    checkBox.setSelected(task.isComplete());
    checkBox.setOnAction(e -> {
      CheckBox cb = (CheckBox) e.getSource();
      task.setCompletion(cb.isSelected());
      callback.accept(ItemAction.RERENDER);
    });

    BorderPane titlePane = new BorderPane();
    titlePane.setLeft(nameLabel);
    titlePane.setRight(checkBox);

    Label descLabel = new Label(task.getDescription());

    this.getStyleClass().add("task");
    this.getChildren().addAll(titlePane, descLabel);
    nameLabel.setOnMouseClicked((MouseEvent e) -> {ScheduleItemAlert alert = new ScheduleItemAlert(task, callback);
      alert.show();});
  }
}
