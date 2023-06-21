package cs3500.pa05.model;

import cs3500.pa05.model.filemanager.FileManager;
import cs3500.pa05.model.filemanager.json.BujoJson;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the ScheduleManager.
 */
public class ScheduleManagerImpl implements ScheduleManager {
  private List<Week> weeks;
  private Week currentWeek;
  private FileManager fileManager;

  private Settings settings;


  /**
   * Constructor for a ScheduleManager
   */
  public ScheduleManagerImpl() {
    this.weeks = new ArrayList<>();
    this.settings = new Settings(0, 0, 0);
  }


  @Override
  public void setFileManager(FileManager fileManager) {
    this.fileManager = fileManager;
  }

  @Override
  public boolean hasFileManager() {
    return this.fileManager != null;
  }

  @Override
  public void loadData(BujoJson bujoJson) {
    this.weeks = bujoJson.weeks();
    this.settings = bujoJson.settings();

    this.setCurrentWeek(this.settings.getCurrentWeek());
  }

  @Override
  public void loadTemplate(BujoJson bujoJson) {
    Settings settingsObject = bujoJson.settings();
    this.weeks = new ArrayList<>();
    this.settings = settingsObject;

    int week = createNewWeek();
    settingsObject.setCurrentWeek(week);
  }

  @Override
  public void saveData() {
    if (this.fileManager != null) {
      BujoJson bujo = new BujoJson(weeks, settings);

      this.fileManager.saveToFile(bujo);
    }
  }

  @Override
  public Settings getSettings() {
    return settings;
  }

  /**
   * Sets the maximum number of tasks setting on the Settings object.
   *
   * @param maximumTasks Maximum number of tasks.
   */
  public void setMaximumTasks(int maximumTasks) {
    settings.setMaximumTasks(maximumTasks);
  }

  /**
   * Sets the maximum number of events setting on the Settings object.
   *
   * @param maximumEvents Maximum number of events.
   */
  public void setMaximumEvents(int maximumEvents) {
    settings.setMaximumEvents(maximumEvents);
  }

  @Override
  public void setCurrentWeek(int week) {
    this.settings.setCurrentWeek(week);
    this.currentWeek = weeks.stream()
        .filter(w -> w.getWeekNumber() == settings.getCurrentWeek())
        .findAny()
        .orElse(null);
  }

  @Override
  public Week getCurrentWeek() {
    return currentWeek;
  }

  @Override
  public int getCurrentWeekNum() {
    return settings.getCurrentWeek();
  }

  @Override
  public int createNewWeek() {
    int newWeekNumber = weeks.size();
    Week newWeek = new Week(newWeekNumber);
    weeks.add(newWeek);
    setCurrentWeek(newWeekNumber);

    return newWeekNumber;
  }

  @Override
  public int getNumWeeks() {
    return weeks.size();
  }
}
