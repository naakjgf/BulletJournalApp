package cs3500.pa05.model.file_manager.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Settings;
import cs3500.pa05.model.Week;
import java.util.List;

/**
 * Json Record for a Bujo file
 * @param weeks Weeks data in bujo file
 */
public record BujoJson(@JsonProperty("data") List<Week> weeks, @JsonProperty Settings settings) {

}
