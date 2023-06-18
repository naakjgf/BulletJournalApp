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
  NEW_WEEK("Shortcut+N");

  private final String keyCombination;

  MenuBarAction(String keyCombination) {
    this.keyCombination = keyCombination;
  }

  public String getKeyCombination() {
    return keyCombination;
  }
}
