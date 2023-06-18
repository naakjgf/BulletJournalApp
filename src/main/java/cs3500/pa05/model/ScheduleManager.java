package cs3500.pa05.model;

import java.util.List;

/**
 * Represents a manager for a Bullet Journal Schedule.
 */
public interface ScheduleManager {
  /**
   * Retrieves a Week object given the week number.
   *
   * @param week Week number.
   * @return Week object.
   */
  Week getWeek(int week);

  /**
   * Load a list of weeks into the model.
   *
   * @param weeks List of weeks to load into the model.
   */
  void loadWeeks();

  /**
   * Save weeks to File using FileManager.
   */
  void saveWeeks();

  /**
   * Sets the current week to given week number.
   *
   * @param week Week number to set current week to.
   */
  void setCurrentWeek(int week);

  /**
   * Gets the current Week object.
   *
   * @return Current week's Week object.
   */
  Week getCurrentWeek();

  /**
   * Creates a new week and sets current week to it.
   *
   * @return Return's the new week's week number.
   */
  int createNewWeek();
}
