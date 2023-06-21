package cs3500.pa05.model.filemanager.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Settings;
import cs3500.pa05.model.Week;
import java.util.List;

/**
 * Json Record for a Bujo file
 *
 * @param weeks List of Week data in a bujo file
 * @param settings Settings object containing settings for Bujo file.
 */
public record BujoJson(@JsonProperty("data") List<Week> weeks, @JsonProperty Settings settings) {

}
