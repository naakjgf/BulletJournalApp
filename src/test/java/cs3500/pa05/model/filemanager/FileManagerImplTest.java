package cs3500.pa05.model.filemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.Settings;
import cs3500.pa05.model.Week;
import cs3500.pa05.model.filemanager.json.BujoJson;
import cs3500.pa05.model.filemanager.json.CryptoJson;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the FileManagerImpl class.
 */
public class FileManagerImplTest {

  private FileManagerImpl fileManager;
  private ObjectMapper objectMapper;
  private String password;
  private String tempFile;

  /**
   * Sets up the test environment with a new FileManagerImpl and ObjectMapper and sets password.
   */
  @BeforeEach
  public void setUp() {
    password = "testPassword";
    tempFile = "temp.txt";
    fileManager = new FileManagerImpl(tempFile, password);
    objectMapper = new ObjectMapper();
  }

  /**
   * Tests loadFromFile with a valid file string, turns it encrypted content into a BujoJson
   *
   * @throws Exception if there is an error with the encryption.
   */
  @Test
  public void testLoadFromFileValid() throws Exception {
    String validJsonContent = "{ \"data\": [], \"settings\": { \"currentWeek\": 0,"
        + " \"maximumEvents\": 0, \"maximumTasks\": 0 } }";
    String salt = CryptoManager.generateSalt(16);
    String encryptedContent = CryptoManager.encrypt(validJsonContent, password, salt);
    CryptoJson cryptoJson = new CryptoJson(encryptedContent, salt);
    ObjectMapper mapper = new ObjectMapper();
    FileReaderWriter.writeFileContents(tempFile, mapper.writeValueAsString(cryptoJson));
    BujoJson loadedBujoJson = fileManager.loadFromFile();

    List<Week> expectedWeeks = new ArrayList<>();
    Settings expectedSettings = new Settings(0, 0, 0);

    assertEquals(expectedWeeks, loadedBujoJson.weeks());
    assertEquals(expectedSettings.getCurrentWeek(), loadedBujoJson.settings().getCurrentWeek());
    assertEquals(expectedSettings.getMaximumEvents(), loadedBujoJson.settings().getMaximumEvents());
    assertEquals(expectedSettings.getMaximumTasks(), loadedBujoJson.settings().getMaximumTasks());
  }

  /**
   * Tests loadFromFile with an invalid file string, turns it encrypted content into an expected
   * null bujoJson
   *
   * @throws Exception if there is an error with the encryption.
   */
  @Test
  public void testLoadFromFileIncorrectPassword() throws Exception {
    String validJsonContent = "{ \"data\": [], \"settings\": { \"currentWeek\": 0,"
        + " \"maximumEvents\": 0, \"maximumTasks\": 0 } } }";
    String salt = CryptoManager.generateSalt(16);
    String encryptedContent = CryptoManager.encrypt(validJsonContent, password, salt);
    CryptoJson cryptoJson = new CryptoJson(encryptedContent, salt);
    ObjectMapper mapper = new ObjectMapper();
    FileReaderWriter.writeFileContents(tempFile, mapper.writeValueAsString(cryptoJson));

    FileManagerImpl wrongPasswordFileManager = new FileManagerImpl(tempFile, "WrongPassword");
    BujoJson loadedBujoJson = wrongPasswordFileManager.loadFromFile();

    assertNull(loadedBujoJson);
  }

  /**
   * Tests loadFromFile with a valid input yet again, turns encrypted content into an
   * expected bujoJson.
   */
  @Test
  public void testLoadFromFile() {
    List<Week> weeks = new ArrayList<>();
    BujoJson bujoJson = new BujoJson(weeks, new Settings(0, 0, 0));
    String salt = CryptoManager.generateSalt(16);
    String validJsonContent;
    try {
      String encryptedData =
          CryptoManager.encrypt(objectMapper.writeValueAsString(bujoJson), password, salt);
      CryptoJson cryptoJson = new CryptoJson(encryptedData, salt);
      validJsonContent = objectMapper.writeValueAsString(cryptoJson);
    } catch (Exception e) {
      throw new RuntimeException("Unable to encrypt file: " + e.getMessage());
    }

    FileReaderWriter.writeFileContents("temp.txt", validJsonContent);

    BujoJson loadedBujoJson = fileManager.loadFromFile();

    assertEquals(bujoJson.settings().getMaximumTasks(),
        loadedBujoJson.settings().getMaximumTasks());
    assertEquals(bujoJson.settings().getMaximumEvents(),
        loadedBujoJson.settings().getMaximumEvents());
  }

  /**
   * Tests saveToFile with a valid input bujoJson.
   */
  @Test
  public void testSaveToFile() {
    List<Week> weeks = new ArrayList<>();
    Settings settings = new Settings(0, 0, 0);
    BujoJson bujoJson = new BujoJson(weeks, settings);

    fileManager.saveToFile(bujoJson);

    BujoJson loadedBujoJson = fileManager.loadFromFile();

    assertEquals(bujoJson.settings().getMaximumTasks(),
        loadedBujoJson.settings().getMaximumTasks());
    assertEquals(bujoJson.settings().getMaximumEvents(),
        loadedBujoJson.settings().getMaximumEvents());
    assertEquals(bujoJson.settings().getCurrentWeek(),
        loadedBujoJson.settings().getCurrentWeek());
    assertEquals(bujoJson.weeks(), loadedBujoJson.weeks());
  }
}