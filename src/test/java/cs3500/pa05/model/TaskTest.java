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

public class TaskTest {
  Task task;
  ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    task = new Task("Coding", "Finish the code", DayOfWeek.MONDAY);
    objectMapper = new ObjectMapper();
  }

  @Test
  public void getName() {
    assertEquals("Coding", task.getName());
  }

  @Test
  public void getDescription() {
    assertEquals("Finish the code", task.getDescription());
  }

  @Test
  public void setDescription() {
    task.setDescription("Fix the code");
    assertEquals("Fix the code", task.getDescription());
  }

  @Test
  public void getDayOfWeek() {
    assertEquals(DayOfWeek.MONDAY, task.getDayOfWeek());
  }

  @Test
  public void setDayOfWeek() {
    task.setDayOfWeek(DayOfWeek.TUESDAY);
    assertEquals(DayOfWeek.TUESDAY, task.getDayOfWeek());
  }

  @Test
  public void getId() {
    assertNotNull(task.getId());
  }

  @Test
  public void isComplete() {
    assertFalse(task.isComplete());
  }

  @Test
  public void setCompletion() {
    task.setCompletion(true);
    assertTrue(task.isComplete());
  }

  @Test
  public void jsonCreatorTest() {
    String json = "{ \"name\" : \"Coding\", \"description\" : \"Finish the code\"," +
        " \"id\" : \"123\", \"day\" : \"MONDAY\", \"complete\" : false }";
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

  @Test
  void setName() {
    task.setName("New Name");
    assertEquals("New Name", task.getName());
  }
}
