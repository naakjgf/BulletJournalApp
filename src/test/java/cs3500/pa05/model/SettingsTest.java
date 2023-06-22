package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Settings class.
 */
public class SettingsTest {
  Settings settings;
  ObjectMapper objectMapper;

  /**
   * Sets up the test, populating a new Settings object and creating an ObjectMapper.
   */
  @BeforeEach
  public void setUp() {
    settings = new Settings(5, 10, 1);
    objectMapper = new ObjectMapper();
  }

  /**
   * Tests that getCurrentWeek returns the correct value.
   */
  @Test
  public void getCurrentWeek() {
    assertEquals(1, settings.getCurrentWeek());
  }

  /**
   * Tests that setCurrentWeek sets the current week to the correct value.
   */
  @Test
  public void setCurrentWeek() {
    settings.setCurrentWeek(2);
    assertEquals(2, settings.getCurrentWeek());
  }

  /**
   * Tests that getMaximumEvents returns the correct value.
   */
  @Test
  public void getMaximumEvents() {
    assertEquals(10, settings.getMaximumEvents());
  }

  /**
   * Tests that getMaximumTasks sets the maximum tasks to the correct value.
   */
  @Test
  public void getMaximumTasks() {
    assertEquals(5, settings.getMaximumTasks());
  }

  /**
   * Tests that setMaximumTasks sets the maximum tasks to the correct value.
   */
  @Test
  public void setMaximumTasks() {
    settings.setMaximumTasks(15);
    assertEquals(15, settings.getMaximumTasks());
  }

  /**
   * Tests that setMaximumEvents sets the maximum events to the correct value.
   */
  @Test
  public void setMaximumEvents() {
    settings.setMaximumEvents(20);
    assertEquals(20, settings.getMaximumEvents());
  }

  /**
   * Tests jsonCreator by creating a Settings object from a JSON string.
   */
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
