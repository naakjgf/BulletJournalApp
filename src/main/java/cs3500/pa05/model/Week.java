package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Week in a Bujo Schedule.
 */
public class Week {
  private final List<Task> tasks;
  private final List<Event> events;
  private final int weekNumber;
  private String weekName;

  /**
   * Constructor for rebuilding a Week object using Jackson.
   *
   * @param weekNumber Week number.
   * @param weekName   User specified name of the week.
   * @param events     List of Event objects.
   * @param tasks      List of task objects.
   */
  @JsonCreator
  public Week(@JsonProperty("weekNumber") int weekNumber,
              @JsonProperty("events") List<Event> events, @JsonProperty("tasks") List<Task> tasks,
              @JsonProperty("weekName") String weekName) {
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
   * Sets the name of the Week.
   *
   * @param name name to set.
   */
  public void setWeekName(String name) {
    this.weekName = name;
  }

  /**
   *
   * @return List of events belonging to week.
   */
  @JsonGetter("events")
  public List<Event> getEvents() {
    return events;
  }

  /**
   *
   * @return List of tasks belonging to week.
   */
  @JsonGetter("tasks")
  public List<Task> getTasks() {
    return tasks;
  }

  /**
   *
   * @return Current week number
   */
  @JsonGetter("weekNumber")
  public int getWeekNumber() {
    return weekNumber;
  }

  /**
   * Adds a task to the task list.
   *
   * @param task task to add.
   */
  public void addTask(Task task) {
    tasks.add(task);
  }

  /**
   * Adds and event to the event list.
   *
   * @param event Event to add.
   */
  public void addEvent(Event event) {
    events.add(event);
  }

  /**
   * Delete an item from the tasks or event list if the ID mathes.
   *
   * @param item Item to delete.
   */
  public void deleteItem(ScheduleItem item) {
    if (item instanceof Task) {
      tasks.remove(item);
    } else if (item instanceof Event) {
      events.remove(item);
    }
  }
}
