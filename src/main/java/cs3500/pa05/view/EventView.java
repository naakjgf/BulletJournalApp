package cs3500.pa05.view;

import cs3500.pa05.enums.DayOfWeek;
import cs3500.pa05.enums.ItemAction;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.ScheduleItem;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class EventView extends VBox {

  private String id;
  private DayOfWeek dayOfWeek;
  public EventView(Event event, Consumer<ItemAction> callback) {
    this.id = event.getId();
    this.dayOfWeek = event.getDayOfWeek();
    Label nameLabel = new Label(event.getName());
    nameLabel.getStyleClass().add("label-hover-effect");
    nameLabel.setFont(new Font("Roboto", 16));
    Label descLabel = new Label(event.getDescription());
    this.getStyleClass().add("event");
    this.getChildren().addAll(nameLabel, descLabel);
    this.setSpacing(1);
    nameLabel.setOnMouseClicked((MouseEvent e) -> {ScheduleItemAlert alert = new ScheduleItemAlert(
        (ScheduleItem) event, callback);
      alert.show();});
  }


  public static String formatTime(long epochTime) {
    LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTime), ZoneId.systemDefault());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    return dateTime.format(formatter);
  }
}
