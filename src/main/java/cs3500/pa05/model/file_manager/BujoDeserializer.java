package cs3500.pa05.model.file_manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.Settings;
import cs3500.pa05.model.Week;
import cs3500.pa05.model.file_manager.json.BujoJson;
import java.util.List;

/**
 * A class for deserializing bujo files into objects.
 */
public class BujoDeserializer {
  private final ObjectMapper mapper;

  /**
   * Constructor for BujoDeserializer.
   */
  public BujoDeserializer() {
    this.mapper = new ObjectMapper();
  }

  /**
   * Processes contents of a Bujo file into a list of weeks.
   *
   * @param json Bujo file contents
   * @return List of Week objects represented by JSON.
   * @throws JsonProcessingException If error processing input JSON.
   */
  public List<Week> jsonToWeeks(String json) throws JsonProcessingException {
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
  public Settings jsonToSettings(String json) throws JsonProcessingException {
    BujoJson bujoJson = this.mapper.readValue(json, BujoJson.class);

    return bujoJson.settings();
  }


  public BujoJson jsonToBujo(String json) throws JsonProcessingException {
    return this.mapper.readValue(json, BujoJson.class);
  }

}
