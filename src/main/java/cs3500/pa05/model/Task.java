package cs3500.pa05.model;

import cs3500.pa05.enums.DayOfWeek;

/**
 * An object representing a Task on the Weekly schedule.
 */
public class Task implements TaskInterface {
  private boolean complete;
  private final String name;
  private String description;
  private DayOfWeek day;

  /**
   * Constructor for a Task object.
   *
   * @param name Name of the task.
   * @param description Description.
   * @param day Day of the week that the task belongs to
   */
  public Task(String name, String description, DayOfWeek day) {
    this.day = day;
    this.name = name;
    this.description = description;
    this.complete = false;
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

  @Override
  public boolean isComplete() {
    return this.complete;
  }

  @Override
  public void setCompletion(boolean isCompleted) {
    this.complete = isCompleted;
  }
}
