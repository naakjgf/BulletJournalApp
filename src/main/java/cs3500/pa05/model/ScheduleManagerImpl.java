package cs3500.pa05.model;

import cs3500.pa05.model.file_manager.FileManager;
import java.util.List;

/**
 * Implementation of the ScheduleManager.
 */
public class ScheduleManagerImpl implements ScheduleManager {
  private List<Week> weeks;
  private Week currentWeek;
  private FileManager fileManager;

  public ScheduleManagerImpl(FileManager fileManager) {
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

  }

  @Override
  public void setCurrentWeek(int week) {

  }

  @Override
  public Week getCurrentWeek() {
    return null;
  }

  @Override
  public int createNewWeek() {
    return 0;
  }
}
