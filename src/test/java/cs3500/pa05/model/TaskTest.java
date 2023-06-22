package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.enums.DayOfWeek;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Task class.
 */
public class TaskTest {
  Task task;
  ObjectMapper objectMapper;

  /**
   * Sets up the task and object mapper for testing.
   */
  @BeforeEach
  public void setUp() {
    task = new Task("Coding", "Finish the code", DayOfWeek.MONDAY);
    objectMapper = new ObjectMapper();
  }

  /**
   * Checks that getName returns the correct name.
   */
  @Test
  public void getName() {
    assertEquals("Coding", task.getName());
  }

  /**
   * Checks that getDescription returns the correct description.
   */
  @Test
  public void getDescription() {
    assertEquals("Finish the code", task.getDescription());
  }

  /**
   * Tests that the setDescription method sets the description of the task properly.
   */
  @Test
  public void setDescription() {
    task.setDescription("Fix the code");
    assertEquals("Fix the code", task.getDescription());
  }

  /**
   * Tests that the getDayOfWeek method returns the correct day of the week.
   */
  @Test
  public void getDayOfWeek() {
    assertEquals(DayOfWeek.MONDAY, task.getDayOfWeek());
  }

  /**
   * Tests that the setDayOfWeek method sets the day of the week of the task.
   */
  @Test
  public void setDayOfWeek() {
    task.setDayOfWeek(DayOfWeek.TUESDAY);
    assertEquals(DayOfWeek.TUESDAY, task.getDayOfWeek());
  }

  /**
   * Tests that the getId method returns a non-null value.
   */
  @Test
  public void getId() {
    assertNotNull(task.getId());
  }

  /**
   * Verifies isComplete returns the correct boolean value.
   */
  @Test
  public void isComplete() {
    assertFalse(task.isComplete());
  }

  /**
   * Tests that the setCompletion method sets the completion of the task.
   */
  @Test
  public void setCompletion() {
    task.setCompletion(true);
    assertTrue(task.isComplete());
  }

  /**
   * Tests that the jsonCreator method checks if the method converts a json string
   * into a task properly.
   */
  @Test
  public void jsonCreatorTest() {
    String json = "{ \"name\" : \"Coding\", \"description\" : \"Finish the code\","
        + " \"id\" : \"123\", \"day\" : \"MONDAY\", \"complete\" : false }";
    try {
      Task parsedTask = objectMapper.readValue(json, Task.class);

      assertEquals("Coding", parsedTask.getName());
      assertEquals("Finish the code", parsedTask.getDescription());
      assertEquals(DayOfWeek.MONDAY, parsedTask.getDayOfWeek());
      System.out.println(parsedTask.isComplete());
      System.out.println(json);
      assertFalse(parsedTask.isComplete());
      assertEquals("123", parsedTask.getId());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Tests the setName method, validating it sets the name of the task properly.
   */
  @Test
  void setName() {
    task.setName("New Name");
    assertEquals("New Name", task.getName());
  }
}
