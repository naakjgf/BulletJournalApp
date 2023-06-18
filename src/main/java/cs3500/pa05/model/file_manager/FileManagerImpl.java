package cs3500.pa05.model.file_manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import cs3500.pa05.model.Settings;
import cs3500.pa05.model.Week;
import cs3500.pa05.model.file_manager.json.BujoJson;
import java.util.List;
import java.util.Set;

public class FileManagerImpl implements FileManager {
  private String fileName;
  private String jsonContent;
  private BujoSerializer serializer;
  private BujoDeserializer deserializer;


  public FileManagerImpl(String filename) {
    this.fileName = filename;
    this.jsonContent = FileReader.readFileContents(fileName);
    this.deserializer = new BujoDeserializer();
    this.serializer = new BujoSerializer();
    updateJsonContent();
  }

  private void updateJsonContent() {
    this.jsonContent = FileReader.readFileContents(fileName);
  }

  @Override
  public List<Week> loadWeeksFromFile() {
    List<Week> weeks;

    try {
      weeks = deserializer.jsonToWeeks(this.jsonContent);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Malformed Bujo file: " + this.fileName);
    }

    return weeks;
  }

  @Override
  public Settings loadSettingsFromFile() {
    Settings settings;

    try {
      settings = deserializer.jsonToSettings(this.jsonContent);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Malformed Bujo file: " + this.fileName);
    }

    return settings;
  }

  @Override
  public void saveSettingsToFile(Settings settings) {

  }

  @Override
  public void saveWeeksToFile(List<Week> weeks, int currentWeek) {
      BujoJson saveBujo = new BujoJson(weeks, loadSettingsFromFile());


  }
}
