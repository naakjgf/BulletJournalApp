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
    this.deserializer = new BujoDeserializer();
    this.serializer = new BujoSerializer();
  }

  /**
   * Updates the jsonContent field with the contents of the file.
   */
  private void updateJsonContent() {
    this.jsonContent = FileReaderWriter.readFileContents(this.filepath);
  }

  @Override
  public BujoJson loadFromFile() {
    updateJsonContent();
    BujoJson bujoJson;

    try {
      System.out.println("CONTENT: " + this.jsonContent);
      bujoJson = deserializer.jsonToBujo(this.jsonContent);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw new RuntimeException("Malformed Bujo file: " + this.filepath);
    }

    return bujoJson;
  }


  @Override
  public void saveToFile(BujoJson saveBujo) {
    String serializedBujo = serializer.bujoToJson(saveBujo);
    FileReaderWriter.writeFileContents(filepath, serializedBujo);
  }
}
