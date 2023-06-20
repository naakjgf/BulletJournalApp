package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cs3500.pa05.model.file_manager.FileManager;
import cs3500.pa05.model.file_manager.json.BujoJson;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ScheduleManagerImplTest {

  private ScheduleManagerImpl scheduleManager;
  private FileManager fileManager;

  @BeforeEach
  public void setUp() {
    scheduleManager = new ScheduleManagerImpl();
    fileManager = Mockito.mock(FileManager.class);
  }

  @Test
  public void setFileManager() {
    assertFalse(scheduleManager.hasFileManager());
    scheduleManager.setFileManager(fileManager);
    assertTrue(scheduleManager.hasFileManager());
  }

  @Test
  public void loadData() {
    BujoJson bujoJson = new BujoJson(new ArrayList<>(), new Settings(3, 4, 1));
    when(fileManager.loadFromFile()).thenReturn(bujoJson);

    scheduleManager.setFileManager(fileManager);
    scheduleManager.loadData();

    assertEquals(1, scheduleManager.getCurrentWeekNum());
  }

  @Test
  public void saveData() {
    scheduleManager.setFileManager(fileManager);
    scheduleManager.saveData();
    verify(fileManager, times(1)).saveToFile(any());
    //just to get the other branch of the if statement
    fileManager = null;
    scheduleManager.setFileManager(null);
    scheduleManager.saveData();
    assertEquals(0, scheduleManager.getCurrentWeekNum());
  }

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
    scheduleManager.loadData();

    assertEquals(1, scheduleManager.getCurrentWeekNum());

    scheduleManager.setCurrentWeek(0);
    assertEquals(0, scheduleManager.getCurrentWeekNum());
  }

  @Test
  public void getCurrentWeek() {
    Week week1 = new Week(0);

    List<Week> weeks = new ArrayList<>();
    weeks.add(week1);

    BujoJson bujoJson = new BujoJson(weeks, new Settings(3, 4, 0));
    when(fileManager.loadFromFile()).thenReturn(bujoJson);

    scheduleManager.setFileManager(fileManager);
    scheduleManager.loadData();

    assertEquals(week1, scheduleManager.getCurrentWeek());
  }

  @Test
  public void createNewWeek() {
    int newWeekNumber = scheduleManager.createNewWeek();

    assertEquals(newWeekNumber, scheduleManager.getCurrentWeekNum());
    assertEquals(newWeekNumber + 1, scheduleManager.getNumWeeks());
  }

  @Test
  void getWeek() {
    Week week1 = new Week(0);
    Week week2 = new Week(1);

    List<Week> weeks = new ArrayList<>();
    weeks.add(week1);
    weeks.add(week2);

    BujoJson bujoJson = new BujoJson(weeks, new Settings(3, 4, 0));
    when(fileManager.loadFromFile()).thenReturn(bujoJson);

    scheduleManager.setFileManager(fileManager);
    scheduleManager.loadData();

    assertEquals(week1, scheduleManager.getWeek(0));
    assertEquals(week2, scheduleManager.getWeek(1));
  }

  @Test
  void getSettings() {
    Settings settings = new Settings(3, 4, 0);

    List<Week> weeks = new ArrayList<>();

    BujoJson bujoJson = new BujoJson(weeks, settings);
    when(fileManager.loadFromFile()).thenReturn(bujoJson);

    scheduleManager.setFileManager(fileManager);
    scheduleManager.loadData();

    assertEquals(settings, scheduleManager.getSettings());
  }

  @Test
  void setMaximumTasks() {
    scheduleManager.setMaximumTasks(5);
    assertEquals(5, scheduleManager.getSettings().getMaximumTasks());
  }

  @Test
  void setMaximumEvents() {
    scheduleManager.setMaximumEvents(7);
    assertEquals(7, scheduleManager.getSettings().getMaximumEvents());
  }
}
