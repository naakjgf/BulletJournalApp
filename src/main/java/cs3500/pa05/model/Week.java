package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a Week in a Bujo Schedule.
 */
public class Week {
  private List<Task> tasks;
  private List<Event> events;
  private int weekNumber;
  private String weekName;

  /**
   * Constructor for rebuilding a Week object using Jackson.
   *
   * @param weekNumber Week number.
   * @param events List of Event objects.
   * @param tasks List of task objects.
   */
  @JsonCreator
  public Week(@JsonProperty("weekNumber") int weekNumber, @JsonProperty("events") List<Event> events, @JsonProperty("tasks") List<Task> tasks, @JsonProperty("weekName") String weekName) {
    this.weekNumber = weekNumber;
    this.tasks = tasks;
    this.events = events;
    this.weekName = weekName;
  }

  /**
   * Constructor for creating a new Week object.
   *
   * @param weekNumber Week number
   */
  public Week(int weekNumber) {
    this.weekNumber = weekNumber;
    this.tasks = new ArrayList<>();
    this.events = new ArrayList<>();
  }

  /**
   * @return Name of the week.
   */
  @JsonGetter("weekName")
  public String getWeekName() {
    return weekName;
  }

  /**
   * @return List of events belonging to week.
   */
  @JsonGetter("events")
  public List<Event> getEvents() {
    return events;
  }

  /**
   * @return List of tasks belonging to week.
   */
  @JsonGetter("tasks")
  public List<Task> getTasks() {
    return tasks;
  }


  /**
   * @return Current week number
   */
  @JsonGetter("weekNumber")
  public int getWeekNumber() {
    return weekNumber;
  }


  /**
   * Adds a task to the task list.
   * @param task task to add.
   */
  public void addTask(Task task) {
    tasks.add(task);
  }

  /**
   * Adds and event to the event list.
   * @param event Event to add.
   */
  public void addEvent(Event event) {
    events.add(event);
  }

  /**
   * Sets the name of the Week.
   *
   * @param name name to set.
   */
  public void setWeekName(String name) {
    this.weekName = name;
  }

  /**
   * Delete an item from the tasks or event list if the ID mathes.
   *
   */
  public void deleteItem(ScheduleItem item) {
    if (item instanceof Task) {
      tasks.remove(item);
    } else if (item instanceof Event) {
      events.remove(item);
    }
  }
}
