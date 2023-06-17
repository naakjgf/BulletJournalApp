package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * An object to store Bullet Journal settings.
 */
public class Settings {
  private int maximumTasks;
  private int maximumEvents;

  /**
   * Constructor for a Settings object
   *
   * @param maximumTasks Maximum number of tasks a user can create per week.
   * @param maximumEvents Maximum number of events a user can create per week.
   */
  public Settings(@JsonProperty int maximumTasks, @JsonProperty int maximumEvents) {
    this.maximumEvents = maximumEvents;
    this.maximumTasks = maximumTasks;
  }

  /**
   * Retrieves the settings value for the maximum number of events a user can create per week.
   *
   * @return int representing max events per week.
   */
  @JsonGetter
  public int getMaximumEvents() {
    return maximumEvents;
  }

  /**
   * Retrieves the settings value for the maximum number of tasks a user can create per week.
   *
   * @return int representing max tasks per week.
   */
  @JsonGetter
  public int getMaximumTasks() {
    return maximumTasks;
  }

  /**
   * Sets the settings value for the maximum number of tasks a user can create per week.
   *
   * @param maximumTasks int value to set maximumTasks to.
   */
  @JsonSetter
  public void setMaximumTasks(int maximumTasks) {
    this.maximumTasks = maximumTasks;
  }

  /**
   * Sets the settings value for the maximum number of events a user can create per week.
   *
   * @param maximumEvents int value to set maximumEvents to.
   */
  @JsonSetter
  public void setMaximumEvents(int maximumEvents) {
    this.maximumEvents = maximumEvents;
  }
}
