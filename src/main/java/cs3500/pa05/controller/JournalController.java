package cs3500.pa05.controller;

/**
 * Represents a controller for a Bullet Journal.
 */
public interface JournalController {
  /**
   * Updates and re-renders the current week view.
   */
  void updateCurrentWeek();

  /**
   * Initializes a Journal view.
   *
   * @throws IllegalStateException if error during journal rendering
   */
  void run() throws IllegalStateException;
}
