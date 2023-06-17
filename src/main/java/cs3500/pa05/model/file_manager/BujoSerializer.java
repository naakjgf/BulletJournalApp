package cs3500.pa05.model.file_manager;

import com.fasterxml.jackson.core.JsonProcessingException;
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
   * Serialize a list of weeks into JSON
   *
   * @param
   * @return List of Week objects represented by JSON.
   * @throws JsonProcessingException If error processing input JSON.
   */
  public String weeksToJson(String json) throws JsonProcessingException {
    BujoJson bujoJson = this.mapper.readValue(json, BujoJson.class);
    return bujoJson.weeks();
  }

  /**
   * Processes contents of a Bujo file into a Settings object.
   *
   * @param json Bujo file contents
   * @return Settings object
   * @throws JsonProcessingException If error processing input JSON.
   */
  public Settings settingsToJson(String json) throws JsonProcessingException {
    BujoJson bujoJson = this.mapper.readValue(json, BujoJson.class);

    return bujoJson.settings();
  }
}
