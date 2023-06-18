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

  @BeforeEach
  public void setUp() {
    event = new Event("Meeting", "Project meeting", DayOfWeek.MONDAY,
        1615972928L, 3600000L);
    event = new Event("Meeting", "Project meeting", DayOfWeek.MONDAY,
        1615972928L, 3600000L);
    objectMapper = new ObjectMapper();
  }

  @Test
  public void jsonCreatorTest() {
    String json =
        "{ \"name\" : \"Meeting\", \"description\" : \"Project meeting\", \"day\" : \"MONDAY\"," +
            " \"startTime\" : 1615972928, \"duration\" : 3600000, \"id\" : \"1\" }";
    try {
      Event parsedEvent = objectMapper.readValue(json, Event.class);

      assertEquals("Meeting", parsedEvent.getName());
      assertEquals("Project meeting", parsedEvent.getDescription());
      assertEquals(DayOfWeek.MONDAY, parsedEvent.getDayOfWeek());
      assertEquals(1615972928L, parsedEvent.getStartTime());
      assertEquals(3600000L, parsedEvent.getDuration());
      assertEquals("1", parsedEvent.getId());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void getId() {
    assertNotNull(event.getId());
  }

  @Test
  public void getStartTime() {
    assertEquals(1615972928L, event.getStartTime());
  }

  @Test
  public void setStartTime() {
    event.setStartTime(1615973000L);
    assertEquals(1615973000L, event.getStartTime());
  }

  @Test
  public void getDuration() {
    assertEquals(3600000L, event.getDuration());
  }

  @Test
  public void setDuration() {
    event.setDuration(7200000L);
    assertEquals(7200000L, event.getDuration());
  }

  @Test
  public void getName() {
    assertEquals("Meeting", event.getName());
  }

  @Test
  public void getDescription() {
    assertEquals("Project meeting", event.getDescription());
  }

  @Test
  public void setDescription() {
    event.setDescription("Updated project meeting");
    assertEquals("Updated project meeting", event.getDescription());
  }

  @Test
  public void getDayOfWeek() {
    assertEquals(DayOfWeek.MONDAY, event.getDayOfWeek());
  }

  @Test
  public void setDayOfWeek() {
    event.setDayOfWeek(DayOfWeek.TUESDAY);
    assertEquals(DayOfWeek.TUESDAY, event.getDayOfWeek());
  }
}