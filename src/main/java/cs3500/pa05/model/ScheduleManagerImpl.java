package cs3500.pa05.model;

import cs3500.pa05.model.file_manager.FileManager;
import cs3500.pa05.model.file_manager.json.BujoJson;
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
  public Week getWeek(int week) {
    return weeks.get(week);
  }

  @Override
  public void loadData() {
    BujoJson bujo = this.fileManager.loadFromFile();
    this.weeks = bujo.weeks();
    this.settings = bujo.settings();

    this.setCurrentWeek(this.settings.getCurrentWeek());
  }

  @Override
  public void saveData() {
    if (this.fileManager != null) {
      BujoJson bujo = new BujoJson(weeks, settings);

      this.fileManager.saveToFile(bujo);
    }
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
  public int createNewWeek() {
    int newWeekNumber = weeks.size();
    Week newWeek = new Week(newWeekNumber);
    weeks.add(newWeek);
    setCurrentWeek(newWeekNumber);

    return newWeekNumber;
  }
  @Override
  public int getNumWeeks()
  {
    return weeks.size();
  }
}
