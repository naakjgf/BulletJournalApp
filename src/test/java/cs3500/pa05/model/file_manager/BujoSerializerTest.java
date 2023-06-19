package cs3500.pa05.model.file_manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
  }

  @Test
  public void testSerializeRecord() {
    JsonNode resultNode = bujoSerializer.serializeRecord(bujoJson);
    assertNotNull(resultNode);
    assertEquals(0, resultNode.get("settings").get("currentWeek").asInt());
    assertEquals(0, resultNode.get("settings").get("maximumEvents").asInt());
  }

  //Not able to force a throw of an exception without modifying the existing code or creating
  //a pointless class that extends Record to force it to fail. May end up attempting to figure out
  //a way later but likely not, cannot mock do to it being static as well.
  /*@Test
  public void testSerializeRecordWithInvalidRecord() {
    try {
      bujoSerializer.serializeRecord(invalidRecord);
    } catch (IllegalArgumentException e) {
      assertEquals("Given record cannot be serialized", e.getMessage());
      throw e;
    }
    BujoSerializer mockSerializer = mock(BujoSerializer.class);
    ObjectMapper mockMapper = mock(ObjectMapper.class);
    when(mockMapper.convertValue(invalidRecord, JsonNode.class))
        .thenThrow(new IllegalArgumentException("Given record cannot be serialized"));
    assertThrows(IllegalArgumentException.class, () -> bujoSerializer.serializeRecord(invalidRecord));
  }*/

  @Test
  public void testBujoToJson() {
    String jsonResult = bujoSerializer.bujoToJson(bujoJson);
    assertEquals("{\"data\":[{\"weekNumber\":0,\"events\":[],\"tasks\":[]," +
        "\"weekName\":null}],\"settings\":{\"maximumTasks\":0,\"maximumEvents\":0," +
        "\"currentWeek\":0}}", jsonResult);
  }
}
