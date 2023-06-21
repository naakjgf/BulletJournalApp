package cs3500.pa05.enums;

/**
 * Represents an action on a menu bar.
 */
public enum MenuBarAction {
  /**
   * Saving a bujo file.
   */
  SAVE("Shortcut+S"),

  /**
   * Saving a bujo file to a new location.
   */
  SAVE_AS("Shortcut+A"),

  /**
   * Opening a Bujo file.
   */
  OPEN("Shortcut+O"),

  /**
   * Creating a new event.
   */
  NEW_EVENT("Shortcut+E"),

  /**
   * Creating a new task.
   */
  NEW_TASK("Shortcut+T"),

  /**
   * Creating a new week.
   */
  NEW_WEEK("Shortcut+N"),

  /**
   * Opening the bujo settings.
   */
  OPEN_SETTINGS("Shortcut+,"),

  /**
   * Open's a bujo file as a template
   */
  OPEN_TEMPLATE("Shortcut+P"),

  /**
   * Creating a new journal.
   */
  NEW_JOURNAL("Shortcut+W");

  private final String keyCombination;

  /**
   * Constructor for MenuBarAction.
   *
   * @param keyCombination Key combination for the action.
   */
  MenuBarAction(String keyCombination) {
    this.keyCombination = keyCombination;
  }

  /**
   * getter for the key combination.
   *
   * @return key combination.
   */
  public String getKeyCombination() {
    return keyCombination;
  }
}
