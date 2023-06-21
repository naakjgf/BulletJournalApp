package cs3500.pa05.model.file_manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.file_manager.json.BujoJson;
import cs3500.pa05.model.Settings;
import cs3500.pa05.model.Week;
import cs3500.pa05.model.file_manager.json.CryptoJson;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.BadPaddingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class FileManagerImplTest {

  private FileManagerImpl fileManager;
  private ObjectMapper objectMapper;
  private String password;
  private String tempFile;

  @BeforeEach
  public void setUp() {
    password = "testPassword";
    tempFile = "temp.txt";
    fileManager = new FileManagerImpl(tempFile, password);
    objectMapper = new ObjectMapper();
  }

  @Test
  public void testLoadFromFileValid() throws Exception {
    String validJsonContent = "{ \"data\": [], \"settings\": { \"currentWeek\": 0," +
        " \"maximumEvents\": 0, \"maximumTasks\": 0 } }";
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


  @Test
  public void testLoadFromFileIncorrectPassword() throws Exception {
    String validJsonContent = "{ \"data\": [], \"settings\": { \"currentWeek\": 0," +
        " \"maximumEvents\": 0, \"maximumTasks\": 0 } } }";
    String salt = CryptoManager.generateSalt(16);
    String encryptedContent = CryptoManager.encrypt(validJsonContent, password, salt);
    CryptoJson cryptoJson = new CryptoJson(encryptedContent, salt);
    ObjectMapper mapper = new ObjectMapper();
    FileReaderWriter.writeFileContents(tempFile, mapper.writeValueAsString(cryptoJson));

    FileManagerImpl wrongPasswordFileManager = new FileManagerImpl(tempFile, "WrongPassword");
    BujoJson loadedBujoJson = wrongPasswordFileManager.loadFromFile();

    assertNull(loadedBujoJson);
  }

  /*@Test
  public void testLoadFromFileBadContent() throws Exception {
    String malformedJsonContent = "this is not valid JSON";
    String salt = CryptoManager.generateSalt(16);
    String encryptedContent = CryptoManager.encrypt(malformedJsonContent, password, salt);
    CryptoJson cryptoJson = new CryptoJson(encryptedContent, salt);
    ObjectMapper mapper = new ObjectMapper();
    FileReaderWriter.writeFileContents(tempFile, mapper.writeValueAsString(cryptoJson));

    assertThrows(RuntimeException.class, () -> fileManager.loadFromFile());
  }

  @Test
  public void testLoadFromFileThrowsSecurityException()
      throws GeneralSecurityException, JsonProcessingException {
    String invalidPermissionsFile = "invalidPermissions.txt";
    FileReaderWriter.writeFileContents(invalidPermissionsFile, "test");
    // Create a FileManager with the invalidPermissionsFile
    FileManagerImpl invalidPermissionsFileManager =
        new FileManagerImpl(invalidPermissionsFile, password);
    BujoDeserializer bujoDeserializer = mock(BujoDeserializer.class);
    when(bujoDeserializer.jsonToBujo("test", password)).thenThrow(GeneralSecurityException.class);

    assertThrows(RuntimeException.class, invalidPermissionsFileManager::loadFromFile);
  }*/


  @Test
  public void testLoadFromFile() {
    List<Week> weeks = new ArrayList<>();
    BujoJson bujoJson = new BujoJson(weeks, new Settings(0, 0, 0));
    String salt = CryptoManager.generateSalt(16);
    String validJsonContent = "";
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

    assertEquals(bujoJson.settings().getMaximumTasks(), loadedBujoJson.settings().getMaximumTasks());
    assertEquals(bujoJson.settings().getMaximumEvents(),
        loadedBujoJson.settings().getMaximumEvents());
  }

}