package cs3500.pa05.model;

import cs3500.pa05.model.file_manager.FileManager;
import java.util.List;

/**
 * Implementation of the ScheduleManager.
 */
public class ScheduleManagerImpl implements ScheduleManager {
  private List<Week> weeks;
  private Week currentWeek;
  private int currentWeekNumber;
  private FileManager fileManager;

  /**
   * Constructor for a ScheduleManager.
   *
   * @param fileManager FileManager instance to use for file management.
   */
  public ScheduleManagerImpl(FileManager fileManager) {
    this.fileManager = fileManager;
  }

  /**
   * Constructor for a ScheduleManager.
   */
  public ScheduleManagerImpl() {

  }

  /**
   * Sets FileManager object.
   *
   * @param fileManager File Manager object to set to.
   */
  public void setFileManager(FileManager fileManager) {
    this.fileManager = fileManager;
  }

  @Override
  public Week getWeek(int week) {
    return weeks.get(week);
  }

  @Override
  public void loadWeeks(List<Week> weeks) {
    this.weeks = weeks;
  }

  @Override
  public void saveWeeks() {
    this.fileManager.saveWeeksToFile(weeks, currentWeekNumber);
  }

  @Override
  public void setCurrentWeek(int week) {
    this.currentWeekNumber = week;
    this.currentWeek = weeks.stream()
        .filter(w -> w.getWeekNumber() == currentWeekNumber)
        .findAny()
        .orElse(null);
  }

  @Override
  public Week getCurrentWeek() {
    return currentWeek;
  }

  @Override
  public int createNewWeek() {
    return 0;
  }
}
