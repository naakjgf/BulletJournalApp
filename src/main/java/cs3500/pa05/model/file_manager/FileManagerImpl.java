package cs3500.pa05.model.file_manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import cs3500.pa05.model.Settings;
import cs3500.pa05.model.Week;
import cs3500.pa05.model.file_manager.json.BujoJson;
import java.util.List;

/**
 * Implementation for a Bujo File Manager.
 */
public class FileManagerImpl implements FileManager {
  private final String filepath;
  private final BujoSerializer serializer;
  private final BujoDeserializer deserializer;
  private String jsonContent;

  /**
   * Constructor for a FileManager.
   *
   * @param filepath Filepath for Bujo file.
   */
  public FileManagerImpl(String filepath) {
    this.filepath = filepath;
    this.jsonContent = FileReaderWriter.readFileContents(this.filepath);
    this.deserializer = new BujoDeserializer();
    this.serializer = new BujoSerializer();
    updateJsonContent();
  }

  private void updateJsonContent() {
    this.jsonContent = FileReaderWriter.readFileContents(this.filepath);
  }

  @Override
  public List<Week> loadWeeksFromFile() {
    List<Week> weeks;

    try {
      weeks = deserializer.jsonToWeeks(this.jsonContent);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Malformed Bujo file: " + this.filepath);
    }

    return weeks;
  }

  @Override
  public Settings loadSettingsFromFile() {
    Settings settings;

    try {
      settings = deserializer.jsonToSettings(this.jsonContent);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Malformed Bujo file: " + this.filepath);
    }

    return settings;
  }

  @Override
  public void saveSettingsToFile(Settings settings) {
    BujoJson saveBujo = new BujoJson(loadWeeksFromFile(), settings);
    String serializedBujo = serializer.bujoToJson(saveBujo);
    FileReaderWriter.writeFileContents(filepath, serializedBujo);
  }

  @Override
  public void saveWeeksToFile(List<Week> weeks, int currentWeek) {
    Settings settings = loadSettingsFromFile();
    settings.setCurrentWeek(currentWeek);

    BujoJson saveBujo = new BujoJson(weeks, settings);
    String serializedBujo = serializer.bujoToJson(saveBujo);
    FileReaderWriter.writeFileContents(filepath, serializedBujo);
  }
}
