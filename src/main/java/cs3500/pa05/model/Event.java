package cs3500.pa05.model;

import cs3500.pa05.enums.DayOfWeek;

/**
 * An object representing an Event on the Weekly schedule.
 */
public class Event implements EventInterface {
  private long startTime;
  private long duration;
  private final String name;
  private String description;
  private DayOfWeek day;

  public Event(String name, String description, DayOfWeek day, long startTime, long duration) {
    this.day = day;
    this.name = name;
    this.description = description;
    this.duration = duration;
    this.startTime = startTime;
  }

  @Override
  public long getStartTime() {
    return startTime;
  }

  @Override
  public long getDuration() {
    return duration;
  }

  @Override
  public void setStartTime(long startTimeEpoch) {
    this.startTime = startTimeEpoch;
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
  public String getDescription() {
    return description;
  }

  @Override
  public DayOfWeek getDayOfWeek() {
    return day;
  }

  @Override
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public void setDayOfWeek(DayOfWeek day) {
    this.day = day;

  }
}
