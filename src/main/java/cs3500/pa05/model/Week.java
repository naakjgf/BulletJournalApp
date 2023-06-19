package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.nio.file.Path;
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
   * Constructor for rebuilding a Week object using Jackson.
   *
   * @param weekNumber Week number.
   * @param events List of Event objects.
   * @param tasks List of task objects.
   */
  @JsonCreator
  public Week(@JsonProperty("weekNumber") int weekNumber, @JsonProperty("events") List<Event> events, @JsonProperty("tasks") List<Task> tasks) {
    this.weekNumber = weekNumber;
    this.tasks = tasks;
    this.events = events;
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
   * Delete an item from the tasks or event list if the ID mathes.
   * @param item item to delete.
   */
  public void deleteItem(ScheduleItem item) {
    if (item instanceof Task) {
      tasks.remove(item);
    } else if (item instanceof Event) {
      events.remove(item);
    }
  }
}
