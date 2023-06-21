package cs3500.pa05.model;

import cs3500.pa05.model.filemanager.FileManager;
import cs3500.pa05.model.filemanager.json.BujoJson;

/**
 * Represents a manager for a Bullet Journal Schedule.
 */
public interface ScheduleManager {

  /**
   * Load data from bujo file into the model.
   *
   * @param bujoJson BujoJson to load into ScheduleManager.
   */
  void loadData(BujoJson bujoJson);

  /**
   * Save data to Bujo file using FileManager.
   */
  void saveData();

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
   * Retrieves current week number.
   *
   * @return current week number
   */
  int getCurrentWeekNum();

  /**
   * Creates a new week and sets current week to it.
   *
   * @return Return's the new week's week number.
   */
  int createNewWeek();

  /**
   * Loads a specific FileManager instance in.
   *
   * @param fileManager FileManager to set.
   */
  void setFileManager(FileManager fileManager);

  /**
   * @return boolean determining whether the filemanager has been set already or not.
   */
  boolean hasFileManager();

  /**
   * @return total number of weeks in schedule manager.
   */
  int getNumWeeks();

  /**
   * Retrieves the settings from the schedule manager.
   *
   * @return Settings object.
   */
  Settings getSettings();

  /**
   * Load a Bujo file as a template.
   *
   * @param bujoJson Bujo file data to load as template.
   */
  void loadTemplate(BujoJson bujoJson);
}
