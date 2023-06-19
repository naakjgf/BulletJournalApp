package cs3500.pa05.model.file_manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.Settings;
import cs3500.pa05.model.Week;
import cs3500.pa05.model.file_manager.json.BujoJson;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BujoSerializerTest {

  private BujoSerializer bujoSerializer;
  private BujoJson bujoJson;
  private Record invalidRecord;

  @BeforeEach
  public void setUp() {
    bujoSerializer = new BujoSerializer();
    List<Week> weeks = new ArrayList<>();
    weeks.add(new Week(0));
    bujoJson = new BujoJson(weeks, new Settings(0, 0, 0));

    invalidRecord = mock(Record.class);

    ObjectMapper mapper = new ObjectMapper();
    when(mapper.convertValue(invalidRecord, JsonNode.class))
        .thenThrow(new IllegalArgumentException());
  }

  @Test
  public void testSerializeRecord() {
    JsonNode resultNode = bujoSerializer.serializeRecord(bujoJson);
    assertNotNull(resultNode);
    assertEquals(0, resultNode.get("settings").get("currentWeek").asInt());
    assertEquals(0, resultNode.get("settings").get("maximumEvents").asInt());
  }

  @Test
  public void testSerializeRecordWithInvalidRecord() {
    bujoSerializer.serializeRecord(invalidRecord);
  }

  @Test
  public void testBujoToJson() {
    String jsonResult = bujoSerializer.bujoToJson(bujoJson);
    assertEquals("{\"data\":[{\"weekNumber\":0,\"events\":[],\"tasks\":[]," +
        "\"weekName\":null}],\"settings\":{\"maximumTasks\":0,\"maximumEvents\":0," +
        "\"currentWeek\":0}}", jsonResult);
  }
}
