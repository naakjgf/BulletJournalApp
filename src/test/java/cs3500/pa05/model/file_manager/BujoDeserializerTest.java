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
  public void testJsonToBujo() throws JsonProcessingException {
    BujoJson bujoJson = bujoDeserializer.jsonToBujo(validJson);
    assertNotNull(bujoJson);

    // Here you can make further assertions about the contents of the bujoJson object
  }
}
