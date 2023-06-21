package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.enums.DayOfWeek;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Week class.
 */
public class WeekTest {
  Week week;
  ObjectMapper objectMapper;
  Task task;
  Event event;
  ScheduleItem scheduleItem;

  /**
   * Sets up the Week and ObjectMapper for testing.
   */
  @BeforeEach
  public void setUp() {
    week = new Week(1);
    task = new Task("Test Task", "Test Description", DayOfWeek.MONDAY);
    event = new Event("Test Event", "Test Description", DayOfWeek.MONDAY, 1609459200000L, 3600000L);
    objectMapper = new ObjectMapper();
  }

  @Test
  public void getWeekNumber() {
    assertEquals(1, week.getWeekNumber());
  }

  @Test
  public void getEvents() {
    week.addEvent(event);
    List<Event> events = week.getEvents();
    assertTrue(events.contains(event));
  }

  @Test
  public void getTasks() {
    week.addTask(task);
    List<Task> tasks = week.getTasks();
    assertTrue(tasks.contains(task));
  }

  @Test
  public void addTask() {
    week.addTask(task);
    assertTrue(week.getTasks().contains(task));
  }

  @Test
  public void addEvent() {
    week.addEvent(event);
    assertTrue(week.getEvents().contains(event));
  }

  @Test
  public void deleteItem() {
    week.addTask(task);
    week.addEvent(event);
    assertTrue(week.getTasks().contains(task));
    assertTrue(week.getEvents().contains(event));

    week.deleteItem(task);
    assertFalse(week.getTasks().contains(task));

    week.deleteItem(event);
    assertFalse(week.getEvents().contains(event));

    week.addEvent((Event) scheduleItem);
    week.deleteItem(scheduleItem);
  }

  @Test
  public void jsonCreatorTest() {
    String json = "{ \"weekNumber\" : 1, \"events\" : [], \"tasks\" : [] }";
    try {
      Week parsedWeek = objectMapper.readValue(json, Week.class);
      assertEquals(1, parsedWeek.getWeekNumber());
      assertTrue(parsedWeek.getTasks().isEmpty());
      assertTrue(parsedWeek.getEvents().isEmpty());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getAndSetWeekName() {
    week.setWeekName("Test Week");
    assertEquals("Test Week", week.getWeekName());
  }
}
