package cs3500.pa05.controller;

import cs3500.pa05.enums.DayOfWeek;
import cs3500.pa05.enums.ItemAction;
import cs3500.pa05.enums.MenuBarAction;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.ScheduleItem;
import cs3500.pa05.model.ScheduleManager;
import cs3500.pa05.model.ScheduleManagerImpl;
import cs3500.pa05.model.Settings;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Week;
import cs3500.pa05.model.file_manager.FileManager;
import cs3500.pa05.model.file_manager.FileManagerImpl;
import cs3500.pa05.view.EventView;
import cs3500.pa05.view.TaskView;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Implementation of a Journal Controller.
 */
public class JournalControllerImpl implements JournalController {
  private ScheduleManager manager;
  private FileManager fileManager;
  private ItemCreationController itemCreator;
  private SettingsController settingsController;

  private int modificationCount;

  private Settings settings;
  @FXML
  private HBox weekView;
  @FXML
  private VBox sideBar;

  @FXML
  private VBox menuBarContainer;

  @FXML
  private Label weekTitle;

  @FXML
  private TextField weekTitleField;

  @FXML
  private StackPane weekTitleContainer;

  @FXML
  private Button prevWeek;
  @FXML
  private Button nextWeek;
  @FXML
  private Button newWeek;

  @FXML
  private Label warningLabel;


  private Stage stage;

  /**
   * Constructor for a JournalController.
   *
   * @param manager ScheduleManager to retrieve Week data from.
   */
  public JournalControllerImpl(Stage stage, ScheduleManager manager) {
    this.manager = manager;
    this.stage = stage;
    this.itemCreator = new ItemCreationController();
    this.settingsController = new SettingsController();
    this.modificationCount = 0;
  }

  /**
   * Constructor for JournalController.
   */
  public JournalControllerImpl() {

  }

  public static Map.Entry<DayOfWeek, Integer> findMax(Map<DayOfWeek, Integer> days) {
    if (days == null || days.isEmpty()) {
      return null;  // or throw an exception, depending on your requirements
    }

    return Collections.max(days.entrySet(), Map.Entry.comparingByValue());
  }

  @Override
  public void updateCurrentWeek() {

  }

  @FXML
  public void run() {
    attachMenuHandlers();
    attachWeekHandlers();
    this.manager.createNewWeek();
    updateWeekTitle();
    registerWeekTitleHandlers();
    createCloseHandler();
  }

  private void attachWeekHandlers() {
    nextWeek.setOnAction((e) -> {
      if (this.manager.getCurrentWeek().getWeekNumber() >= this.manager.getNumWeeks() - 1) {
        return;
      }

      this.manager.setCurrentWeek(this.manager.getCurrentWeekNum() + 1);
      renderWeek();
    });

    prevWeek.setOnAction((e) -> {
      if (this.manager.getCurrentWeek().getWeekNumber() <= 0) {
        return;
      }

      this.manager.setCurrentWeek(this.manager.getCurrentWeekNum() - 1);
      renderWeek();
    });

    newWeek.setOnAction(e -> createNewWeek());
  }

  /**
   * Updates the week title to the current active week.
   */
  public void updateWeekTitle() {
    finishEditWeekTitle();
    Week currentWeek = this.manager.getCurrentWeek();

    if (currentWeek.getWeekName() != null) {
      weekTitle.setText(currentWeek.getWeekName());
    } else {
      weekTitle.setText("Week " + (this.manager.getCurrentWeekNum() + 1));
    }
  }

  private void handleMenuAction(ActionEvent e, MenuBarAction action) {
    switch (action) {
      case OPEN -> loadFile();
      case SAVE -> saveFile(false);
      case SAVE_AS -> saveFile(true);
      case NEW_WEEK -> createNewWeek();
      case NEW_TASK -> createNewTask();
      case NEW_EVENT -> createNewEvent();
      case OPEN_SETTINGS -> openSettings();
      case NEW_JOURNAL -> createNewJournal();
    }
  }

  private void createCloseHandler() {
    this.stage.setOnCloseRequest(event -> {
      if (modificationCount > 0) {
        event.consume();

        // create a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Unsaved changes");
        alert.setHeaderText("You have unsaved changes");
        alert.setContentText("Choose your option.");

        ButtonType buttonSave = new ButtonType("Save");
        ButtonType buttonCancel = new ButtonType("Cancel");
        ButtonType buttonDontSave = new ButtonType("Don't save");

        alert.getButtonTypes().setAll(buttonSave, buttonCancel, buttonDontSave);

        alert.showAndWait().ifPresent(type -> {
          if (type == buttonSave) {
            if (saveFile(false)) {
              this.stage.close();
            } else {
              showAlert("Unable to save file", "User cancelled operation!");
            }
          } else if (type == buttonDontSave) {
            this.stage.close();
          }
        });
      }
    });
  }

  private void createNewJournal() {
    if (modificationCount > 0 && !saveFile(false)) {
      showAlert("Error saving current bujo!",
          "Please save the current working bujo before creating a new one.");
      return;
    }

    this.manager = new ScheduleManagerImpl();
    this.manager.createNewWeek();
    renderWeek();

    modificationCount = 0;
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  private void openSettings() {
    this.settingsController.openSettingsModal(this.manager.getSettings());
    renderWeek();
  }

  private void createNewWeek() {
    this.manager.createNewWeek();
    renderWeek();
    modificationCount++;
  }

  private MenuItem createMenuItem(MenuBarAction action, String name, boolean createKeybind) {
    MenuItem menuItem = new MenuItem(name);
    menuItem.setOnAction(e -> handleMenuAction(e, action));

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

  private void attachMenuHandlers() {
    MenuBar menuBarVisible = createMenuBar(true);
    MenuBar menuBarHidden = createMenuBar(false);

    menuBarHidden.useSystemMenuBarProperty().set(true);

    menuBarContainer.getChildren().addAll(menuBarVisible, menuBarHidden);
  }

  private void handleItemAction(ItemAction action, Week w, ScheduleItem item) {
    switch (action) {
      case DELETE -> w.deleteItem(item);
      case EDIT -> itemCreator.editItem(item);
    }

    renderWeek();
    modificationCount++;
  }

  public void renderWeek() {
    Map<DayOfWeek, Integer> taskCountMap = new HashMap<>();
    Map<DayOfWeek, Integer> eventCountMap = new HashMap<>();

    for (Node node : weekView.getChildren()) {
      // Check if this child is a VBox
      if (node instanceof ScrollPane scrollPane) {
        // Now, we can remove specific children from the VBox
        VBox vbox = (VBox) scrollPane.getContent();

        ((VBox) vbox).getChildren().removeIf(
            child -> child instanceof TaskView || child instanceof EventView);
      }
    }

    sideBar.getChildren().removeIf(child -> !(child instanceof Label));

    Week currentWeek = manager.getCurrentWeek();

    for (Task t : currentWeek.getTasks()) {
      TaskView tView = new TaskView(t,
          (ItemAction action) -> handleItemAction(action, currentWeek, t));

      if (taskCountMap.containsKey(t.getDayOfWeek())) {
        taskCountMap.put(t.getDayOfWeek(), taskCountMap.get(t.getDayOfWeek()) + 1);
      } else {
        taskCountMap.put(t.getDayOfWeek(), 1);
      }

      //Add tView to GUI
      VBox vbox = (VBox) ((ScrollPane) weekView.getChildren()
          .get(t.getDayOfWeek().getNumVal())).getContent();

      vbox.getChildren().add(tView);

      //Add completion status to sidebar
      VBox sidebarTask = new VBox(5);
      sidebarTask.getStyleClass().add("sidebarTask");
      Label nameLabel = new Label(t.getName());
      nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
      Label completeLabel = new Label(t.isComplete() ? "Completed" : "Incomplete");
      completeLabel.setFont(Font.font("Arial", 12));


      sidebarTask.getChildren().addAll(nameLabel, completeLabel);

      sideBar.getChildren().add(sidebarTask);
    }

    currentWeek.getEvents().sort(Comparator.comparingLong(Event::getStartTime));
    for (Event e : currentWeek.getEvents()) {
      if (eventCountMap.containsKey(e.getDayOfWeek())) {
        eventCountMap.put(e.getDayOfWeek(), eventCountMap.get(e.getDayOfWeek()) + 1);
      } else {
        eventCountMap.put(e.getDayOfWeek(), 1);
      }

      EventView eView = new EventView(e,
          (ItemAction action) -> handleItemAction(action, currentWeek, e));

      VBox vbox = (VBox) ((ScrollPane) weekView.getChildren()
          .get(e.getDayOfWeek().getNumVal())).getContent();

      vbox.getChildren().add(eView);
    }

    alertMaximumItems(taskCountMap, eventCountMap);

    updateWeekTitle();
  }

  private void registerWeekTitleHandlers() {
    weekTitle.addEventFilter(MouseEvent.MOUSE_CLICKED, (mouseEvent) -> {
      if (mouseEvent.getClickCount() == 2) {
        editWeekTitle();
      }
    });

    weekTitleField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        this.manager.getCurrentWeek().setWeekName(weekTitleField.getText());
        updateWeekTitle();
      }
    });

    weekTitleField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
      if (wasFocused && !isNowFocused) {
        this.manager.getCurrentWeek().setWeekName(weekTitleField.getText());
        updateWeekTitle();
      }
    });
  }

  private void finishEditWeekTitle() {
    weekTitle.setVisible(true);  // Set the label's text to the text field's text
    weekTitleField.setVisible(false);
    weekTitleField.setDisable(true);
  }

  private void editWeekTitle() {
    weekTitleField.setText(weekTitle.getText());
    weekTitle.setVisible(false);
    weekTitleField.setVisible(true);
    weekTitleField.setDisable(false);
    weekTitleField.requestFocus();
    modificationCount++;

    // Add an event filter for pressing Enter in the text field
  }

  private void alertMaximumItems(Map<DayOfWeek, Integer> taskCountMap,
                                 Map<DayOfWeek, Integer> eventCountMap) {
    Settings settings = this.manager.getSettings();

    warningLabel.setText("");

    Map.Entry<DayOfWeek, Integer> taskMax = findMax(taskCountMap);
    Map.Entry<DayOfWeek, Integer> eventMax = findMax(eventCountMap);

    if (taskMax != null && settings.getMaximumTasks() != 0) {
      if (taskMax.getValue() > settings.getMaximumTasks()) {
        warningLabel.setText("Maximum number of tasks exceeded for " + taskMax.getKey() + "    ");
      }
    }

    if (eventMax != null && settings.getMaximumEvents() != 0) {
      if (eventMax.getValue() > settings.getMaximumEvents()) {
        warningLabel.setText(
            warningLabel.getText() + "Maximum number of events exceeded for " + eventMax.getKey());
      }
    }


  }

  public boolean saveFile(boolean saveAs) {
    if (!this.manager.hasFileManager() || saveAs) {
      String filePath = saveBujoFile();
      if (filePath == null) {
        System.out.println("User did not choose save file. Operation cancelled!");
        return false;
      }
      this.fileManager = new FileManagerImpl(filePath);
      this.manager.setFileManager(this.fileManager);
    }

    this.manager.saveData();
    return true;
  }

  private void createNewTask() {
    itemCreator.createNewTask(task -> {
      this.manager.getCurrentWeek().addTask(task);
      modificationCount++;
      renderWeek();
    });
  }

  private void createNewEvent() {
    itemCreator.createNewEvent(event -> {
      this.manager.getCurrentWeek().addEvent(event);
      modificationCount++;
      renderWeek();
    });
  }

  private void loadFile() {
    if (modificationCount > 0 && !saveFile(false)) {
      showAlert("Error saving current bujo!",
          "Please save the current working bujo before loading a different one.");
      return;
    }

    String filePath = chooseBujoFile();
    if (filePath == null) {
      System.out.println("User did not choose save file. Operation cancelled!");
      return;
    }
    this.fileManager = new FileManagerImpl(filePath);
    this.manager.setFileManager(this.fileManager);
    this.manager.loadData();
    modificationCount = 0;
    renderWeek();
  }


  private FileChooser getBujoChooser() {
    FileChooser fileChooser = new FileChooser();

    fileChooser.setTitle("Open Bujo File");

    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Bujo Files", "*.bujo"));

    return fileChooser;
  }

  private String chooseBujoFile() {
    FileChooser fileChooser = getBujoChooser();

    File file = fileChooser.showOpenDialog(stage);

    if (file == null) {
      return null;
    }

    return file.getAbsolutePath();
  }

  private String saveBujoFile() {
    FileChooser fileChooser = getBujoChooser();

    File file = fileChooser.showSaveDialog(stage);

    if (file == null) {
      return null;
    }

    return file.getAbsolutePath();
  }
}
