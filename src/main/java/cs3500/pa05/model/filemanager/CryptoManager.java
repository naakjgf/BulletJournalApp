package cs3500.pa05.model.filemanager;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Manages Cryptography for encrpytion and decryption of Bujo files.
 */
public class CryptoManager {
  private static final int ITERATION_COUNT = 65536;
  private static final int KEY_LENGTH = 256;


  /**
   * Generates a secure salt to be used by the Secret Key generator.
   *
   * @param length Length of salt to generate.
   * @return String representing the password hashing salt.
   */
  public static String generateSalt(int length) {
    SecureRandom secureRandom = new SecureRandom();
    byte[] salt = new byte[length];
    secureRandom.nextBytes(salt);
    return Base64.getEncoder().encodeToString(salt);
  }

  /**
   * Calculates a hashed secret key with SHA256 for AES encryption using a password and salt.
   *
   * @param password Password to generate key with.
   * @param salt     Salt to use for hashing.
   * @return SecretKey object for AES encryption/decryption.
   * @throws NoSuchAlgorithmException If algorithm does not exist.
   * @throws InvalidKeySpecException  If key spec is invalid.
   */
  private static SecretKey getSecretKey(String password, String salt)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    KeySpec
        spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), ITERATION_COUNT, KEY_LENGTH);
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    byte[] secretKey = factory.generateSecret(spec).getEncoded();
    return new SecretKeySpec(secretKey, "AES");
  }

  /**
   * Encrypts plaintext using a password and salt.
   *
   * @param plainText Text to encrypt.
   * @param password  Password to encrypt with.
   * @param salt      Generated salt to use for encryption
   * @return Encrypted cipher.
   * @throws GeneralSecurityException If exception caught during encryption or key calculation.
   */
  public static String encrypt(String plainText, String password, String salt)
      throws GeneralSecurityException {
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    SecretKey secretKey = getSecretKey(password, salt);

    SecureRandom secureRandom = new SecureRandom();
    byte[] iv = new byte[16];
    secureRandom.nextBytes(iv);
    IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

    cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
    byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

    byte[] cipherTextWithIv = new byte[cipherText.length + iv.length];
    System.arraycopy(iv, 0, cipherTextWithIv, 0, iv.length);
    System.arraycopy(cipherText, 0, cipherTextWithIv, iv.length, cipherText.length);

    return Base64.getEncoder().encodeToString(cipherTextWithIv);
  }


  /**
   * Decrypts plaintext using a password and salt.
   *
   * @param cipherText Cipher to decrypt.
   * @param password   Password to decrypt with.
   * @param salt       Stored salt to use for decryption hash
   * @return Decrypted plaintext.
   * @throws GeneralSecurityException If exception caught during decryption or key calculation.
   */
  public static String decrypt(String cipherText, String password, String salt)
      throws GeneralSecurityException {
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    SecretKey secretKey = getSecretKey(password, salt);

    byte[] cipherTextWithIv = Base64.getDecoder().decode(cipherText);
    byte[] iv = new byte[16];
    System.arraycopy(cipherTextWithIv, 0, iv, 0, iv.length);
    IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

    byte[] actualCipherText = new byte[cipherTextWithIv.length - iv.length];
    System.arraycopy(cipherTextWithIv, iv.length, actualCipherText, 0, actualCipherText.length);

    cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
    byte[] plainText = cipher.doFinal(actualCipherText);

    return new String(plainText, StandardCharsets.UTF_8);
  }
}
