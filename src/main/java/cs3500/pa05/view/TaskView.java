package cs3500.pa05.view;

import cs3500.pa05.enums.DayOfWeek;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import cs3500.pa05.model.Task;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TaskView extends VBox {
  private String id;
  private DayOfWeek dayOfWeek;

  public TaskView(Task task) {
    id = task.getId();
    this.dayOfWeek = task.getDayOfWeek();
    Label nameLabel = new Label(task.getName());
    nameLabel.setFont(new Font("Roboto", 16));
    nameLabel.setTextFill(Color.BLACK);
    Label descLabel = new Label(task.getDescription());
    nameLabel.setAlignment(Pos.CENTER);
    Label statusLabel = new Label("Status: " + (task.isComplete() ? "Completed" : "Incomplete"));

    this.getChildren().addAll(nameLabel, descLabel, statusLabel);
  }
}
