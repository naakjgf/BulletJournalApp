package cs3500.pa05.model.filemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.GeneralSecurityException;
import java.util.Base64;
import org.junit.jupiter.api.Test;

/**
 * Tests for CryptoManager.
 */
class CryptoManagerTest {
  @Test
  void encryptDecryptTest() {
    String password = "testPassword";
    String salt = CryptoManager.generateSalt(16);
    String originalMessage = "This is a test message.";

    String encryptedMessage = null;
    try {
      encryptedMessage = CryptoManager.encrypt(originalMessage, password, salt);
    } catch (GeneralSecurityException e) {
      fail("Encryption failed with GeneralSecurityException.");
    }

    assertNotEquals(originalMessage, encryptedMessage);

    String decryptedMessage = null;
    try {
      decryptedMessage = CryptoManager.decrypt(encryptedMessage, password, salt);
    } catch (GeneralSecurityException e) {
      fail("Decryption failed with GeneralSecurityException.");
    }

    assertEquals(originalMessage, decryptedMessage);
  }

  @Test
  void generateSaltLength() {
    String salt = CryptoManager.generateSalt(16);

    assertNotNull(salt);
    assertTrue(salt.length() > 0);

    assertEquals(16, Base64.getDecoder().decode(salt).length);
  }
}
