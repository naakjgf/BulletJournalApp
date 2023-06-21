package cs3500.pa05.model.file_manager.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Settings;
import cs3500.pa05.model.Week;
import java.util.List;

/**
 * Json Record for an encrypted Bujo file
 *
 * @param encryptedData Encrypted data.
 * @param salt Salt for SHA256 password hash calculation.
 */
public record CryptoJson(@JsonProperty("encryptedData") String encryptedData, @JsonProperty("salt") String salt) {

}

