package cs3500.pa05.view;

import cs3500.pa05.enums.DayOfWeek;
import cs3500.pa05.model.Event;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class EventView extends VBox {

  private String id;
  private DayOfWeek dayOfWeek;
  public EventView(Event event) {
    this.id = event.getId();
    this.dayOfWeek = event.getDayOfWeek();
    Label nameLabel = new Label(event.getName());
    Label descLabel = new Label(event.getDescription());
    Label startTimeLabel = new Label("Start Time: " + formatTime(event.getStartTime()));
    Label durationLabel = new Label("Duration: " + event.getDuration() + " minutes");

    this.getChildren().addAll(nameLabel, descLabel, startTimeLabel, durationLabel);
    this.setSpacing(1);
  }

  private String formatTime(long epochTime) {
    Instant instant = Instant.ofEpochMilli(epochTime);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        .withZone(ZoneId.systemDefault());
    return formatter.format(instant);
  }
}
