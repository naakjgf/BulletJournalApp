package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class SettingsTest {
  Settings settings;
  ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    settings = new Settings(5, 10, 1);
    objectMapper = new ObjectMapper();
  }

  @Test
  public void getCurrentWeek() {
    assertEquals(1, settings.getCurrentWeek());
  }

  @Test
  public void setCurrentWeek() {
    settings.setCurrentWeek(2);
    assertEquals(2, settings.getCurrentWeek());
  }

  @Test
  public void getMaximumEvents() {
    assertEquals(10, settings.getMaximumEvents());
  }

  @Test
  public void getMaximumTasks() {
    assertEquals(5, settings.getMaximumTasks());
  }

  @Test
  public void setMaximumTasks() {
    settings.setMaximumTasks(15);
    assertEquals(15, settings.getMaximumTasks());
  }

  @Test
  public void setMaximumEvents() {
    settings.setMaximumEvents(20);
    assertEquals(20, settings.getMaximumEvents());
  }

  @Test
  public void jsonCreatorTest() {
    String json = "{ \"maximumTasks\" : 5, \"maximumEvents\" : 10, \"currentWeek\" : 1 }";
    try {
      Settings parsedSettings = objectMapper.readValue(json, Settings.class);

      assertEquals(5, parsedSettings.getMaximumTasks());
      assertEquals(10, parsedSettings.getMaximumEvents());
      assertEquals(1, parsedSettings.getCurrentWeek());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
