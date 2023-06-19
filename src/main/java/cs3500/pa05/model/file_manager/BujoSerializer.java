package cs3500.pa05.model.file_manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.Settings;
import cs3500.pa05.model.Week;
import cs3500.pa05.model.file_manager.json.BujoJson;
import java.util.List;

/**
 * A class for serializing objects into Bujo files.
 */
public class BujoSerializer {
  private final ObjectMapper mapper;

  /**
   * Constructor for BujoSerializer.
   */
  public BujoSerializer() {
    this.mapper = new ObjectMapper();
  }

  /**
   * Converts a given record object to a JsonNode.
   *
   * @param record the record to convert
   * @return the JsonNode representation of the given record
   * @throws IllegalArgumentException if the record could not be converted correctly
   */
  public JsonNode serializeRecord(Record record) {
    try {
      return this.mapper.convertValue(record, JsonNode.class);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Given record cannot be serialized");
    }
  }

  /**
   * Serialize a BujoJson into JSON.
   *
   * @param bujoJson BujoJson object
   * @return List of Week objects represented by JSON.
   * @throws JsonProcessingException If error processing input JSON.
   */
  public String bujoToJson(BujoJson bujoJson) {
    return serializeRecord(bujoJson).toString();
  }
}
