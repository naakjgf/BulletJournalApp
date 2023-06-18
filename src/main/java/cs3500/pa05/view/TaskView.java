package cs3500.pa05.view;

import cs3500.pa05.enums.DayOfWeek;
import javafx.scene.control.Label;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import cs3500.pa05.model.Task;
import javafx.scene.text.Font;

public class TaskView extends VBox {
  private String id;
  private DayOfWeek dayOfWeek;

  public TaskView(Task task) {
    id = task.getId();
    this.dayOfWeek = task.getDayOfWeek();
    Label nameLabel = new Label("Task: " + task.getName());
    nameLabel.setFont(new Font("Roboto", 24));
    Label descLabel = new Label("Description: " + task.getDescription());
    Label statusLabel = new Label("Status: " + (task.isComplete() ? "Completed" : "Incomplete"));

    this.getChildren().addAll(nameLabel, descLabel, statusLabel);
  }
}
