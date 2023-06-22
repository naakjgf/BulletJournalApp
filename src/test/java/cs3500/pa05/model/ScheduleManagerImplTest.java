package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cs3500.pa05.model.filemanager.FileManager;
import cs3500.pa05.model.filemanager.json.BujoJson;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Tests for ScheduleManagerImpl.
 */
public class ScheduleManagerImplTest {

  private ScheduleManagerImpl scheduleManager;
  private FileManager fileManager;

  /**
   * Sets up the test with a ScheduleManagerImpl and a mock FileManager.
   */
  @BeforeEach
  public void setUp() {
    scheduleManager = new ScheduleManagerImpl();
    fileManager = Mockito.mock(FileManager.class);
  }

  /**
   * Tests that setFileManager sets the fileManager field properly.
   */
  @Test
  public void setFileManager() {
    assertFalse(scheduleManager.hasFileManager());
    scheduleManager.setFileManager(fileManager);
    assertTrue(scheduleManager.hasFileManager());
  }

  /**
   * Tests that the loadData method loads the data from a bujoJson properly.
   */
  @Test
  public void loadData() {
    BujoJson bujoJson = new BujoJson(new ArrayList<>(), new Settings(3, 4, 1));
    when(fileManager.loadFromFile()).thenReturn(bujoJson);

    scheduleManager.setFileManager(fileManager);
    scheduleManager.loadData(bujoJson);

    assertEquals(1, scheduleManager.getCurrentWeekNum());
  }

  /**
   * Tests that the loadData method loads the data from a bujoJson properly.
   */
  @Test
  public void saveData() {
    scheduleManager.setFileManager(fileManager);
    scheduleManager.saveData();
    verify(fileManager, times(1)).saveToFile(any());

    fileManager = null;
    scheduleManager.setFileManager(null);
    scheduleManager.saveData();
    assertEquals(0,
        scheduleManager.getCurrentWeekNum());
  }

  /**
   * Tests that setCurrentWeek sets the current week properly.
   */
  @Test
  public void setCurrentWeek() {
    Week week1 = new Week(0);
    Week week2 = new Week(1);

    List<Week> weeks = new ArrayList<>();
    weeks.add(week1);
    weeks.add(week2);

    BujoJson bujoJson = new BujoJson(weeks, new Settings(3, 4, 1));
    when(fileManager.loadFromFile()).thenReturn(bujoJson);

    scheduleManager.setFileManager(fileManager);
    scheduleManager.loadData(bujoJson);

    assertEquals(1, scheduleManager.getCurrentWeekNum());

    scheduleManager.setCurrentWeek(0);
    assertEquals(0, scheduleManager.getCurrentWeekNum());
  }

  /**
   * Tests that getCurrentWeek returns the current week number properly.
   */
  @Test
  public void getCurrentWeek() {
    Week week1 = new Week(0);

    List<Week> weeks = new ArrayList<>();
    weeks.add(week1);

    BujoJson bujoJson = new BujoJson(weeks, new Settings(3, 4, 0));
    when(fileManager.loadFromFile()).thenReturn(bujoJson);

    scheduleManager.setFileManager(fileManager);
    scheduleManager.loadData(bujoJson);

    assertEquals(week1, scheduleManager.getCurrentWeek());
  }

  /**
   * Tests that createNewWeek creates a new week properly and increments the number of weeks.
   */
  @Test
  public void createNewWeek() {
    int newWeekNumber = scheduleManager.createNewWeek();

    assertEquals(newWeekNumber, scheduleManager.getCurrentWeekNum());
    assertEquals(newWeekNumber + 1, scheduleManager.getNumWeeks());
  }

  /**
   * Tests getSettings validating it returning the settings properly.
   */
  @Test
  void getSettings() {
    Settings settings = new Settings(3, 4, 0);

    List<Week> weeks = new ArrayList<>();

    BujoJson bujoJson = new BujoJson(weeks, settings);
    when(fileManager.loadFromFile()).thenReturn(bujoJson);

    scheduleManager.setFileManager(fileManager);
    scheduleManager.loadData(bujoJson);

    assertEquals(settings, scheduleManager.getSettings());
  }

  /**
   * Tests setMaximumTasks validating it setting the maximum tasks properly.
   */
  @Test
  void setMaximumTasks() {
    scheduleManager.setMaximumTasks(5);
    assertEquals(5, scheduleManager.getSettings().getMaximumTasks());
  }

  /**
   * Tests setMaximumEvents validating it setting the maximum events properly.
   */
  @Test
  void setMaximumEvents() {
    scheduleManager.setMaximumEvents(7);
    assertEquals(7, scheduleManager.getSettings().getMaximumEvents());
  }

  /**
   * Tests the loadTemplate method validating it loads the proper template of bujoJson when called.
   */
  @Test
  void loadTemplate() {
    Settings templateSettings = new Settings(3, 4, 0);

    BujoJson templateBujoJson = new BujoJson(new ArrayList<>(), templateSettings);
    scheduleManager.loadTemplate(templateBujoJson);

    assertEquals(templateSettings, scheduleManager.getSettings());
    assertEquals(1, scheduleManager.getNumWeeks());
    assertEquals(0, scheduleManager.getCurrentWeekNum());
    assertNotNull(scheduleManager.getCurrentWeek());
  }
}
