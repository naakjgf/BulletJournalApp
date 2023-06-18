package cs3500.pa05.model.file_manager;

import cs3500.pa05.model.Settings;
import cs3500.pa05.model.Week;
import java.util.List;

/**
 * Representation of a Bujo file manager. Handles reading and writing of .bujo files.
 */
public interface FileManager {
  /**
   * Load List of weeks from a Bujo File
   * @return List of Week objects
   */
  List<Week> loadWeeksFromFile();

  /**
   * Load a Settings object from the settings of a bujo file.
   *
   * @return Settings object.
   */
  Settings loadSettingsFromFile();

  /**
   * Save settings from a Settings object to the settings of a bujo file.
   * @param settings Settings object to save to file
   */
  void saveSettingsToFile(Settings settings);


  /**
   * Save list of Weeks to Bujo file.
   *
   * @param weeks List of weeks to save
   */
  void saveWeeksToFile(List<Week> weeks, int currentWeek);
}
