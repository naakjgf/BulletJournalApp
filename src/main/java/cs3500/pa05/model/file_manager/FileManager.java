package cs3500.pa05.model.file_manager;

import cs3500.pa05.model.Settings;
import cs3500.pa05.model.Week;
import cs3500.pa05.model.file_manager.json.BujoJson;
import java.util.List;

/**
 * Representation of a Bujo file manager. Handles reading and writing of .bujo files.
 */
public interface FileManager {
  /**
   * Loads Bujo data from a Bujo File
   * @return BujoJson object
   */
  BujoJson loadFromFile();

  /**
   * Save Bujo data to a bujo file.
   * @param bujoJson bujo data object to save to file.
   */
  void saveToFile(BujoJson bujoJson);
}
