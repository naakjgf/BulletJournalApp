package cs3500.pa05.model.filemanager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.filemanager.json.BujoJson;
import cs3500.pa05.model.filemanager.json.CryptoJson;
import java.security.GeneralSecurityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the BujoDeserializer class.
 */
public class BujoDeserializerTest {
  private BujoDeserializer bujoDeserializer;
  private String cryptoJson;

  /**
   * Sets up the test. Creates a valid BujoJson object and encrypts it.
   *
   * @throws GeneralSecurityException if there is an error with the encryption.
   */
  @BeforeEach
  public void setUp() throws GeneralSecurityException {
    bujoDeserializer = new BujoDeserializer();
    String validJson = "{ \"data\": [], \"settings\": { \"currentWeek\": 0,"
        + " \"maximumEvents\": 0, \"maximumTasks\": 0 } }";
    String password = "Password";
    String salt = CryptoManager.generateSalt(16);
    String encryptedData = CryptoManager.encrypt(validJson, password, salt);
    CryptoJson cryptoJsonObj = new CryptoJson(encryptedData, salt);
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      cryptoJson = objectMapper.writeValueAsString(cryptoJsonObj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Tests that the jsonToBujo method checks if the method makes a non-null BujoJson object.
   */
  @Test
  public void testJsonToBujo() {
    BujoJson bujoJson;
    try {
      bujoJson = bujoDeserializer.jsonToBujo(cryptoJson, "Password");
    } catch (GeneralSecurityException | JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    assertNotNull(bujoJson);
  }
}


