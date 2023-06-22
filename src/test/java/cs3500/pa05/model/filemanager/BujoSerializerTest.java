package cs3500.pa05.model.filemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.Settings;
import cs3500.pa05.model.Week;
import cs3500.pa05.model.filemanager.json.BujoJson;
import cs3500.pa05.model.filemanager.json.CryptoJson;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the BujoSerializer class.
 */
public class BujoSerializerTest {

  private BujoSerializer bujoSerializer;
  private BujoJson bujoJson;
  private String validJson;

  /**
   * Sets up the test. Creates a valid BujoJson object and encrypts it.
   *
   * @throws GeneralSecurityException if there is an error with the encryption.
   */
  @BeforeEach
  public void setUp() throws GeneralSecurityException {
    bujoSerializer = new BujoSerializer();
    List<Week> weeks = new ArrayList<>();
    weeks.add(new Week(0));
    bujoJson = new BujoJson(weeks, new Settings(0, 0, 0));

    String password = "Password";
    String salt = CryptoManager.generateSalt(16);
    ObjectMapper objectMapper = new ObjectMapper();
    String jsonRepresentation;
    try {
      jsonRepresentation = objectMapper.writeValueAsString(bujoJson);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    String encryptedData = CryptoManager.encrypt(jsonRepresentation, password, salt);
    CryptoJson cryptoJson = new CryptoJson(encryptedData, salt);

    try {
      validJson = objectMapper.writeValueAsString(cryptoJson);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Tests that the serializeRecord method checks if the method converts properly to
   * a non-null JsonNode object.
   */
  @Test
  public void testSerializeRecord() {
    JsonNode resultNode = bujoSerializer.serializeRecord(bujoJson);
    assertNotNull(resultNode);
    assertEquals(0, resultNode.get("settings").get("currentWeek").asInt());
    assertEquals(0, resultNode.get("settings").get("maximumEvents").asInt());
  }

  /**
   * Tests that the bujoToJson method, when given a valid BujoJson object
   * returns a valid String Json
   */
  @Test
  public void testBujoToJson() {

    String jsonResult;
    try {
      jsonResult = bujoSerializer.bujoToJson(bujoJson, "Password");
    } catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    }

    ObjectMapper objectMapper = new ObjectMapper();
    CryptoJson validCryptoJson;
    CryptoJson actualCryptoJson;
    try {
      validCryptoJson = objectMapper.readValue(validJson, CryptoJson.class);
      actualCryptoJson = objectMapper.readValue(jsonResult, CryptoJson.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    String password = "Password";
    String validPlainText;
    String actualPlainText;
    try {
      validPlainText =
          CryptoManager.decrypt(validCryptoJson.encryptedData(), password, validCryptoJson.salt());
      actualPlainText = CryptoManager.decrypt(actualCryptoJson.encryptedData(), password,
          actualCryptoJson.salt());
    } catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    }

    assertEquals(validPlainText, actualPlainText);
  }
}

