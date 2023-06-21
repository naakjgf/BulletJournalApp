package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.enums.DayOfWeek;
import java.util.UUID;

/**
 * An object representing an Event on the Weekly schedule.
 */
public class Event implements EventInterface {
  private final String id;
  private String name;
  private long startTime;
  private long duration;
  private String description;
  private DayOfWeek day;

  /**
   * JSON Constructor for an Event object with id.
   *
   * @param name        Name of the event.
   * @param description Description.
   * @param day         Day of the week the event belongs to.
   * @param startTime   The epoch time for the event starts
   * @param id          ID of the Event object
   * @param duration    The duration the event lasts, in milliseconds.
   */
  @JsonCreator
  public Event(@JsonProperty("name") String name, @JsonProperty("description") String description,
               @JsonProperty("day") DayOfWeek day, @JsonProperty("startTime") long startTime,
               @JsonProperty("duration") long duration, @JsonProperty("id") String id) {
    this.day = day;
    this.name = name;
    this.description = description;
    this.duration = duration;
    this.startTime = startTime;
    this.id = id;
  }

  /**
   * Constructor for an Event object without id.
   *
   * @param name        Name of the event.
   * @param description Description.
   * @param day         Day of the week the event belongs to.
   * @param startTime   The epoch time for the event starts
   * @param duration    The duration the event lasts, in milliseconds.
   */
  public Event(String name, String description, DayOfWeek day, long startTime, long duration) {
    this.day = day;
    this.name = name;
    this.description = description;
    this.duration = duration;
    this.startTime = startTime;
    this.id = UUID.randomUUID().toString();
  }

  /**
   * Constructor for an Event object without id.
   *
   * @return String of the ID for this event.
   */
  public String getId() {
    return id;
  }

  @Override
  public long getStartTime() {
    return startTime;
  }

  @Override
  public void setStartTime(long startTimeEpoch) {
    this.startTime = startTimeEpoch;
  }

  @Override
  public long getDuration() {
    return duration;
  }

  @Override
  public void setDuration(long durationMs) {
    this.duration = durationMs;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public DayOfWeek getDayOfWeek() {
    return day;
  }

  @Override
  public void setDayOfWeek(DayOfWeek day) {
    this.day = day;

  }
}
