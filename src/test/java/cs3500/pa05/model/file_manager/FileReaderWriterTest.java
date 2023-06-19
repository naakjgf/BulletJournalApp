package cs3500.pa05.model.file_manager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;


public class FileReaderWriterTest {

  @Test
  public void testReadFileContents() {
    String content = "test content";
    String tempFile = "temp.txt";
    try {
      Files.write(Paths.get(tempFile), content.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals(content, FileReaderWriter.readFileContents(tempFile));
  }

  @Test
  public void testWriteFileContents() {
    String content = "test content";
    String tempFile = "temp.txt";
    FileReaderWriter.writeFileContents(tempFile, content);

    String readContent = "";
    try {
      readContent = new String(Files.readAllBytes(Paths.get(tempFile)));
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(content, readContent);
  }

  @Test
  public void testReadFileContentsThrowsIOException() {
    // An invalid file path
    String filePath = "/invalid/file/path.txt";
    FileReaderWriter.readFileContents(filePath);
  }

  @Test
  public void testWriteFileContentsThrowsIOException() {
    // An invalid file path
    String filePath = "/invalid/file/path.txt";
    FileReaderWriter.writeFileContents(filePath, "Some content");
  }
}
