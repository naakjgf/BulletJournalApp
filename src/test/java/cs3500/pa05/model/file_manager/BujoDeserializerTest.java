package cs3500.pa05.model.file_manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.file_manager.json.BujoJson;
import cs3500.pa05.model.file_manager.json.CryptoJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BujoDeserializerTest {
  private BujoDeserializer bujoDeserializer;
  private String validJson;
  private String cryptoJson;

  @BeforeEach
  public void setUp() throws GeneralSecurityException {
    bujoDeserializer = new BujoDeserializer();
    validJson = "{ \"data\": [], \"settings\": { \"currentWeek\": 0," +
        " \"maximumEvents\": 0, \"maximumTasks\": 0 } }";
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

  @Test
  public void testJsonToBujo() {
    BujoJson bujoJson = null;
    try {
      bujoJson = bujoDeserializer.jsonToBujo(cryptoJson, "Password");
    } catch (GeneralSecurityException | JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    assertNotNull(bujoJson);
  }
}


