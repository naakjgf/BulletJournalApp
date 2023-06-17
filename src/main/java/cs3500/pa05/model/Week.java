package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Week in a Bujo Schedule.
 */
public class Week {
  private List<Task> tasks;
  private List<Event> events;
  private int weekNumber;

  /**
   * Constructor for a Week object.
   *
   * @param weekNumber Week number.
   * @param events List of Event objects.
   * @param tasks List of task objects.
   */
  public Week(@JsonProperty int weekNumber, @JsonProperty("events") List<Event> events, @JsonProperty List<Task> tasks) {
    this.weekNumber = weekNumber;
    this.tasks = tasks;
    this.events = events;
  }

  /**
   * @return Current week number
   */
  public int getWeekNumber() {
    return weekNumber;
  }



}
