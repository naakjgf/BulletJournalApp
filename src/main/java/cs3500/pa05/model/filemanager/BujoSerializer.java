package cs3500.pa05.model.filemanager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.filemanager.json.BujoJson;
import cs3500.pa05.model.filemanager.json.CryptoJson;
import java.security.GeneralSecurityException;

/**
 * A class for serializing objects into Bujo files.
 */
public class BujoSerializer {
  private final ObjectMapper mapper;

  /**
   * Constructor for BujoSerializer.
   */
  public BujoSerializer() {
    this.mapper = new ObjectMapper();
  }

  /**
   * Converts a given record object to a JsonNode.
   *
   * @param record the record to convert
   * @return the JsonNode representation of the given record
   * @throws IllegalArgumentException if the record could not be converted correctly
   */
  public JsonNode serializeRecord(Record record) {
    try {
      return this.mapper.convertValue(record, JsonNode.class);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Given record cannot be serialized");
    }
  }

  /**
   * Serialize a BujoJson into JSON.
   *
   * @param bujoJson BujoJson object
   * @param password Password to encrypt bujo file with.
   * @return List of Week objects represented by JSON.
   * @throws GeneralSecurityException If error encrypting input JSON.
   */
  public String bujoToJson(BujoJson bujoJson, String password) throws GeneralSecurityException {
    String plainText = serializeRecord(bujoJson).toString();
    String salt = CryptoManager.generateSalt(16);

    String cipher = CryptoManager.encrypt(plainText, password, salt);

    CryptoJson cryptoJson = new CryptoJson(cipher, salt);

    return serializeRecord(cryptoJson).toString();
  }
}
