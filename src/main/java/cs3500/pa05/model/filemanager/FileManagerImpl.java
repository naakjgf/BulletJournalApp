package cs3500.pa05.model.filemanager;

import com.fasterxml.jackson.core.JsonProcessingException;
import cs3500.pa05.model.filemanager.json.BujoJson;
import java.security.GeneralSecurityException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

/**
 * Implementation for a Bujo File Manager.
 */
public class FileManagerImpl implements FileManager {
  private final String filepath;
  private final BujoSerializer serializer;
  private final BujoDeserializer deserializer;
  private String jsonContent;
  private final String password;

  /**
   * Constructor for a FileManager.
   *
   * @param filepath Filepath for Bujo file.
   * @param password Password to encrypt/decrypt bujo file with.
   */
  public FileManagerImpl(String filepath, String password) {
    this.filepath = filepath;
    this.deserializer = new BujoDeserializer();
    this.serializer = new BujoSerializer();
    this.password = password;
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
      bujoJson = deserializer.jsonToBujo(this.jsonContent, this.password);
    } catch (BadPaddingException | IllegalBlockSizeException | JsonProcessingException e) {
      // Incorrect Password
      return null;
    } catch (GeneralSecurityException e) {
      throw new RuntimeException("Unable to decrypt file: " + e.getMessage());
    }

    return bujoJson;
  }


  @Override
  public void saveToFile(BujoJson saveBujo) {
    String serializedBujo;

    try {
      serializedBujo = serializer.bujoToJson(saveBujo, this.password);
    } catch (GeneralSecurityException e) {
      throw new RuntimeException("Unable to encrypt file: " + e.getMessage());
    }

    FileReaderWriter.writeFileContents(filepath, serializedBujo);
  }
}
