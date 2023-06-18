package cs3500.pa05.view;

import cs3500.pa05.model.Event;
import cs3500.pa05.model.ScheduleItem;
import cs3500.pa05.model.Task;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ScheduleObjectButton extends Button {

  public ScheduleObjectButton(ScheduleItem item)
  {
    super();
    VBox vbox;
    if(item instanceof Event)
    {
      vbox = new EventView((Event) item);

    }
    else if (item instanceof Task)
    {
      vbox = new TaskView((Task) item);
    }
    else {
      throw new IllegalArgumentException();
    }
    this.setGraphic(vbox);
    this.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
    this.setOnAction(e -> {
      ScheduleItemAlert alert = new ScheduleItemAlert(item);
      alert.showAndWait();
    });
  }
}
