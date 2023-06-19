package cs3500.pa05.enums;

/**
 * Represents an action on a menu bar.
 */
public enum MenuBarAction {
  SAVE("Shortcut+S"),
  SAVE_AS("Shortcut+A"),
  OPEN("Shortcut+O"),
  NEW_EVENT("Shortcut+E"),
  NEW_TASK("Shortcut+T"),
  NEW_WEEK("Shortcut+W"),
  OPEN_SETTINGS("Shortcut+,"),
  NEW_JOURNAL("Shortcut+N");

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
