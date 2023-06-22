package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.enums.DayOfWeek;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Event class.
 */
public class EventTest {
  Event event;
  ObjectMapper objectMapper;

  /**
   * Sets up the event and object mapper for testing.
   */
  @BeforeEach
  public void setUp() {
    event = new Event("Meeting", "Project meeting", DayOfWeek.MONDAY,
        1615972928L, 3600000L);
    event = new Event("Meeting", "Project meeting", DayOfWeek.MONDAY,
        1615972928L, 3600000L);
    objectMapper = new ObjectMapper();
  }

  /**
   * Tests that the jsonCreator method checks if the method converts a json string
   * to an event properly.
   */
  @Test
  public void jsonCreatorTest() {
    String json =
        "{ \"name\" : \"Meeting\", \"description\" : \"Project meeting\", \"day\" : \"MONDAY\","
            + " \"startTime\" : 1615972928, \"duration\" : 3600000, \"id\" : \"123\" }";
    try {
      Event parsedEvent = objectMapper.readValue(json, Event.class);

      assertEquals("Meeting", parsedEvent.getName());
      assertEquals("Project meeting", parsedEvent.getDescription());
      assertEquals(DayOfWeek.MONDAY, parsedEvent.getDayOfWeek());
      assertEquals(1615972928L, parsedEvent.getStartTime());
      assertEquals(3600000L, parsedEvent.getDuration());
      assertEquals("123", parsedEvent.getId());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Tests the getID method.
   */
  @Test
  public void getId() {
    assertNotNull(event.getId());
  }

  /**
   * Tests the getStartTime method.
   */
  @Test
  public void getStartTime() {
    assertEquals(1615972928L, event.getStartTime());
  }

  /**
   * Tests the setStartTime method.
   */
  @Test
  public void setStartTime() {
    event.setStartTime(1615000L);
    assertEquals(1615000L, event.getStartTime());
  }

  /**
   * Tests the getDuration method.
   */
  @Test
  public void getDuration() {
    assertEquals(3600000L, event.getDuration());
  }

  /**
   * Tests the setDuration method.
   */
  @Test
  public void setDuration() {
    event.setDuration(7200000L);
    assertEquals(7200000L, event.getDuration());
  }

  /**
   * Tests the getName method.
   */
  @Test
  public void getName() {
    assertEquals("Meeting", event.getName());
  }

  /**
   * Tests the getDescription method.
   */
  @Test
  public void getDescription() {
    assertEquals("Project meeting", event.getDescription());
  }

  /**
   * Tests the setDescription method.
   */
  @Test
  public void setDescription() {
    event.setDescription("Updated project meeting");
    assertEquals("Updated project meeting", event.getDescription());
  }

  /**
   * Tests the getDayOfWeek method.
   */
  @Test
  public void getDayOfWeek() {
    assertEquals(DayOfWeek.MONDAY, event.getDayOfWeek());
  }

  /**
   * Tests the setDayOfWeek method.
   */
  @Test
  public void setDayOfWeek() {
    event.setDayOfWeek(DayOfWeek.TUESDAY);
    assertEquals(DayOfWeek.TUESDAY, event.getDayOfWeek());
  }

  /**
   * Tests the setName method.
   */
  @Test
  void setName() {
    event.setName("Updated Meeting");
    assertEquals("Updated Meeting", event.getName());
  }
}