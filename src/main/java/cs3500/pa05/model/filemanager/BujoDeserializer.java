package cs3500.pa05.model.filemanager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.filemanager.json.BujoJson;
import cs3500.pa05.model.filemanager.json.CryptoJson;
import java.security.GeneralSecurityException;

/**
 * A class for deserializing bujo files into objects.
 */
public class BujoDeserializer {
  private final ObjectMapper mapper;

  /**
   * Constructor for BujoDeserializer.
   */
  public BujoDeserializer() {
    this.mapper = new ObjectMapper();
  }

  /**
   * Processes the Json contents of a Bujo file into a BujoJson object.
   *
   * @param json     Bujo file contents
   * @param password Password to decrypt bujo file with.
   * @return BujoJson object which represents the contents of the file.
   * @throws JsonProcessingException  If error processing input JSON.
   * @throws GeneralSecurityException If error decrypting bujo file.
   */
  public BujoJson jsonToBujo(String json, String password) throws
      JsonProcessingException, GeneralSecurityException {
    CryptoJson cryptoJson = this.mapper.readValue(json, CryptoJson.class);

    String decryptedJson =
        CryptoManager.decrypt(cryptoJson.encryptedData(), password, cryptoJson.salt());

    return this.mapper.readValue(decryptedJson, BujoJson.class);
  }
}
