package cs3500.pa05.controller;

import cs3500.pa05.model.ScheduleManager;
import javafx.fxml.FXML;

/**
 * Implementation of a Journal Controller.
 */
public class JournalControllerImpl implements JournalController {
  private ScheduleManager manager;

  /**
   * Constructor for a JournalController.
   *
   * @param manager ScheduleManager to retrieve Week data from.
   */
  public JournalControllerImpl(ScheduleManager manager) {
    this.manager = manager;
  }

  /**
   * Constructor for JournalController.
   */
  public JournalControllerImpl() {

  }

  public void setManager(ScheduleManager manager) {
    this.manager = manager;
  }

  @Override
  public void updateCurrentWeek() {

  }

  @FXML
  public void run() {

  }
}
