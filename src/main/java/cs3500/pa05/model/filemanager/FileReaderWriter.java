package cs3500.pa05.model.filemanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class to read and write contents to file.
 */
public class FileReaderWriter {
  /**
   * Read file contents from a specified filename.
   *
   * @param fileName filename to laod contents from.
   * @return Contents of file.
   */
  public static String readFileContents(String fileName) {
    String content = "";

    try {
      content = new String(Files.readAllBytes(Paths.get(fileName)));
    } catch (IOException e) {
      System.out.println("An error occurred reading bujo file contents from: " + fileName);
      e.printStackTrace();
    }

    return content;
  }

  /**
   * Write contents to file with specified filename.
   *
   * @param fileName Filename to write contents to.
   * @param content Contents to write to file.
   */
  public static void writeFileContents(String fileName, String content) {
    try {
      Files.write(Paths.get(fileName), content.getBytes());
    } catch (IOException e) {
      System.out.println("An error occurred writing bujo file contents to " + fileName);
      e.printStackTrace();
    }
  }
}
