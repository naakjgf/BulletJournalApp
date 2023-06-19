package cs3500.pa05.model.file_manager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import cs3500.pa05.model.Settings;
import cs3500.pa05.model.Week;
import cs3500.pa05.model.file_manager.json.BujoJson;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BujoDeserializerTest {
  private BujoDeserializer bujoDeserializer;
  private String validJson;
  private String invalidJson;

  @BeforeEach
  public void setUp() {
    bujoDeserializer = new BujoDeserializer();
    validJson = "{ \"data\": [], \"settings\": { \"currentWeek\": 0," +
        " \"maximumEvents\": 0, \"maximumTasks\": 0 } }";
  }

  @Test
  public void testJsonToWeeks() throws JsonProcessingException {
    List<Week> weeks = bujoDeserializer.jsonToWeeks(validJson);
    assertNotNull(weeks);


    // Here you can make further assertions about the contents of the weeks list
    // For example, if you know that the validJson should result in a list with one week, you could assert:
    // assertEquals(1, weeks.size());
  }

  @Test
  public void testJsonToSettings() throws JsonProcessingException {
    Settings settings = bujoDeserializer.jsonToSettings(validJson);
    assertNotNull(settings);

    // Here you can make further assertions about the contents of the settings object
  }

  @Test
  public void testJsonToBujo() throws JsonProcessingException {
    BujoJson bujoJson = bujoDeserializer.jsonToBujo(validJson);
    assertNotNull(bujoJson);

    // Here you can make further assertions about the contents of the bujoJson object
  }
}
