package cs3500.pa05.model.filemanager;

import cs3500.pa05.model.filemanager.json.BujoJson;

/**
 * Representation of a Bujo file manager. Handles reading and writing of .bujo files.
 */
public interface FileManager {
  /**
   * Loads Bujo data from a Bujo File
   *
   * @return BujoJson object
   */
  BujoJson loadFromFile();

  /**
   * Save Bujo data to a bujo file.
   *
   * @param saveBujo bujo data object to save to file.
   */
  void saveToFile(BujoJson saveBujo);
}
