package cs3500.pa05.model.file_manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import cs3500.pa05.model.file_manager.json.BujoJson;
import cs3500.pa05.model.Settings;
import cs3500.pa05.model.Week;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileManagerImplTest {

  private FileManagerImpl fileManager;

  @BeforeEach
  public void setUp() {
    String tempFile = "temp.txt";
    fileManager = new FileManagerImpl(tempFile);
  }

  @Test
  public void testLoadFromFile() {
    String validJsonContent = "{ \"data\": [], \"settings\": { \"currentWeek\": 0," +
        " \"maximumEvents\": 0, \"maximumTasks\": 0 } }";
    FileReaderWriter.writeFileContents("temp.txt", validJsonContent);

    BujoJson loadedBujoJson = fileManager.loadFromFile();

    assertEquals(new ArrayList<Week>(), loadedBujoJson.weeks());
    assertEquals(0, loadedBujoJson.settings().getCurrentWeek());

    fileManager = new FileManagerImpl("temp2.txt");

    assertThrows(RuntimeException.class, () -> fileManager.loadFromFile(), "");
  }

  @Test
  public void testSaveToFile() {
    List<Week> weeks = new ArrayList<>();
    weeks.add(new Week(0));
    BujoJson bujoJson = new BujoJson(weeks, new Settings(0, 0, 0));

    fileManager.saveToFile(bujoJson);

    String savedContent = FileReaderWriter.readFileContents("temp.txt");

    String expectedContent = "{\"data\":[{\"weekNumber\":0,\"events\":[],\"tasks\":[],\"weekName\":null}]," +
        "\"settings\":{\"maximumTasks\":0,\"maximumEvents\":0,\"currentWeek\":0}}";
    assertEquals(expectedContent, savedContent.trim());
  }
}


