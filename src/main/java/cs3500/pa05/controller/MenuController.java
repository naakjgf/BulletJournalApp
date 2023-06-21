package cs3500.pa05.controller;

import cs3500.pa05.enums.MenuBarAction;
import java.util.function.BiConsumer;
import java.util.function.Function;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;

/**
 * Controller for a MenuBar.
 */
public class MenuController {
  private final BiConsumer<ActionEvent, MenuBarAction> handleMenuAction;

  /**
   * Constructor for a MenuBar controller.
   *
   * @param handleMenuAction MenuBarAction
   */
  public MenuController(BiConsumer<ActionEvent, MenuBarAction> handleMenuAction) {
    this.handleMenuAction = handleMenuAction;
  }

  private MenuItem createMenuItem(MenuBarAction action, String name, boolean createKeybind) {
    MenuItem menuItem = new MenuItem(name);
    menuItem.setOnAction(e -> this.handleMenuAction.accept(e, action));

    if (createKeybind) {
      menuItem.setAccelerator(KeyCombination.keyCombination(action.getKeyCombination()));
    }

    return menuItem;
  }

  private MenuBar createMenuBar(boolean createKeybinds) {
    Menu menuFile = new Menu("File");
    MenuItem itemNewBujo = createMenuItem(MenuBarAction.NEW_JOURNAL, "New Bujo", createKeybinds);
    MenuItem itemSave = createMenuItem(MenuBarAction.SAVE, "Save", createKeybinds);
    MenuItem itemSaveAs = createMenuItem(MenuBarAction.SAVE_AS, "Save As", createKeybinds);
    MenuItem itemOpen = createMenuItem(MenuBarAction.OPEN, "Open", createKeybinds);
    MenuItem itemSettings = createMenuItem(MenuBarAction.OPEN_SETTINGS, "Settings", createKeybinds);

    menuFile.getItems().addAll(itemNewBujo, itemSave, itemSaveAs, itemOpen, itemSettings);

    Menu menuInsert = new Menu("Insert");
    MenuItem itemEvent = createMenuItem(MenuBarAction.NEW_EVENT, "New Event", createKeybinds);
    MenuItem itemTask = createMenuItem(MenuBarAction.NEW_TASK, "New Task", createKeybinds);
    MenuItem itemWeek = createMenuItem(MenuBarAction.NEW_WEEK, "New Week", createKeybinds);

    menuInsert.getItems().addAll(itemEvent, itemTask, itemWeek);

    MenuBar menubar = new MenuBar();
    menubar.getMenus().addAll(menuFile, menuInsert);

    return menubar;
  }

  /**
   * Attach handlers for the menu bar actions.
   *
   * @param menuBarContainer Container VBox for menu bar on application screen.
   */
  public void attachMenuHandlers(VBox menuBarContainer) {
    MenuBar menuBarVisible = createMenuBar(true);
    MenuBar menuBarHidden = createMenuBar(false);

    menuBarHidden.useSystemMenuBarProperty().set(true);

    menuBarContainer.getChildren().addAll(menuBarVisible, menuBarHidden);
  }
}
