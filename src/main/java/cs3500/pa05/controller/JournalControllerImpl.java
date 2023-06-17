package cs3500.pa05.controller;

import cs3500.pa05.model.ScheduleManager;

public class JournalControllerImpl implements JournalController {
  private ScheduleManager manager;

  public JournalControllerImpl(ScheduleManager manager) {
    this.manager = manager;
  }

  @Override
  public void updateCurrentWeek() {

  }
}
