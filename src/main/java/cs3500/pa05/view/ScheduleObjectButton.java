package cs3500.pa05.view;

import cs3500.pa05.enums.ItemAction;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.ScheduleItem;
import cs3500.pa05.model.Task;
import java.util.function.Consumer;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ScheduleObjectButton extends Button {

  public ScheduleObjectButton(ScheduleItem item, Consumer<ItemAction> callback)
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
    this.setStyle("-fx-background-color: #D8BFD8; -fx-text-fill: #4B0082; -fx-border-color: #8A2BE2; -fx-border-width: 2px; -fx-padding: 5px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
    this.setOnMouseEntered(e -> this.setStyle("-fx-background-color: #8A2BE2; -fx-text-fill: white; -fx-border-color: #8A2BE2; -fx-border-width: 2px; -fx-padding: 5px; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
    this.setOnMouseExited(e -> this.setStyle("-fx-background-color: #D8BFD8; -fx-text-fill: #4B0082; -fx-border-color: #8A2BE2; -fx-border-width: 2px; -fx-padding: 5px; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
    this.setOnAction(e -> {
      ScheduleItemAlert alert = new ScheduleItemAlert(item, callback);
      alert.showAndWait();
    });
  }
}
