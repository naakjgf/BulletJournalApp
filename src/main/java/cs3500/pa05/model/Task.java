package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.enums.DayOfWeek;
import java.util.UUID;

/**
 * An object representing a Task on the Weekly schedule.
 */
public class Task implements TaskInterface {
  private String name;
  private final String id;
  private boolean complete;
  private String description;
  private DayOfWeek day;

  /**
   * JSON Constructor for a Task object with completion and ID status.
   *
   * @param name        Name of the task.
   * @param description Description.
   * @param day         Day of the week that the task belongs to
   * @param complete    Completion status of task
   * @param id          ID of task
   */
  @JsonCreator
  public Task(@JsonProperty("name") String name,
              @JsonProperty("description") String description,
              @JsonProperty("day") DayOfWeek day,
              @JsonProperty("complete") boolean complete,
              @JsonProperty("id") String id) {
    this.day = day;
    this.name = name;
    this.description = description;
    this.complete = complete;
    this.id = id;
  }


  /**
   * Constructor for a Task object without completion or ID status.
   *
   * @param name        Name of the task.
   * @param description Description.
   * @param day         Day of the week that the task belongs to
   */
  public Task(String name, String description, DayOfWeek day) {
    this.day = day;
    this.name = name;
    this.description = description;
    this.complete = false;
    this.id = UUID.randomUUID().toString();
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

  @Override
  public String getId() {
    return id;
  }

  @Override
  public boolean isComplete() {
    return this.complete;
  }

  @Override
  public void setCompletion(boolean isCompleted) {
    this.complete = isCompleted;
  }
}
