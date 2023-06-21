package cs3500.pa05.controller;

import cs3500.pa05.enums.MenuBarAction;
import cs3500.pa05.model.ScheduleManager;
import cs3500.pa05.model.ScheduleManagerImpl;
import cs3500.pa05.model.Settings;
import cs3500.pa05.model.filemanager.FileManager;
import cs3500.pa05.model.filemanager.FileManagerImpl;
import cs3500.pa05.model.filemanager.json.BujoJson;
import cs3500.pa05.view.PasswordView;
import cs3500.pa05.view.RenameWeekView;
import java.io.File;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
  private MenuController menuController;
  private WeekController weekController;
  private AtomicInteger modificationCount;
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
  @FXML
  private VBox weeklyOverview;
  private Stage stage;

  /**
   * Constructor for a JournalController.
   *
   * @param stage   JavaFX stage for the window.
   * @param manager ScheduleManager to retrieve Week data from.
   */
  public JournalControllerImpl(Stage stage, ScheduleManager manager) {
    this.manager = manager;
    this.stage = stage;
    this.itemCreator = new ItemCreationController();
    this.settingsController = new SettingsController();
    this.modificationCount = new AtomicInteger(0);
    this.menuController = new MenuController(this::handleMenuAction);
  }

  /**
   * A Blank Constructor for JournalController.
   */
  public JournalControllerImpl() {

  }

  /**
   * Runs the Journal Controller.
   */
  @FXML
  public void run() {
    this.weekController =
        new WeekController(manager, itemCreator, modificationCount, weekView, weekTitle,
            weekTitleField, nextWeek, prevWeek,
            newWeek, sideBar, weeklyOverview, warningLabel);

    this.menuController.attachMenuHandlers(menuBarContainer);

    this.weekController.attachWeekHandlers();
    this.manager.createNewWeek();
    this.weekController.updateWeekTitle();
    this.weekController.registerWeekTitleHandlers();
    createCloseHandler();
  }

  /**
   * Handles Menu Bar Actions.
   *
   * @param e      ActionEvent
   * @param action MenuBarAction
   */
  private void handleMenuAction(ActionEvent e, MenuBarAction action) {
    switch (action) {
      case OPEN -> loadFile();
      case SAVE -> saveFile(false);
      case SAVE_AS -> saveFile(true);
      case NEW_WEEK -> this.weekController.createNewWeek();
      case NEW_TASK -> createNewTask();
      case NEW_EVENT -> createNewEvent();
      case OPEN_SETTINGS -> openSettings();
      case OPEN_TEMPLATE -> openTemplate();
      case NEW_JOURNAL -> createNewJournal();
      default -> throw new IllegalArgumentException("Unknown action provided!");
    }
  }

  /**
   * Creates a way of closing the window.
   */
  private void createCloseHandler() {
    this.stage.setOnCloseRequest(event -> {
      if (modificationCount.get() > 0) {
        boolean result = showSaveRequest();

        if (!result) {
          event.consume();
        }
      }
    });
  }

  /**
   * Displays a confirmation alert for saving updates to the file.
   *
   * @return true if the user wants to save, false otherwise.
   */
  private boolean showSaveRequest() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Unsaved changes");
    alert.setHeaderText("You have unsaved changes");
    alert.setContentText("Choose your option.");

    ButtonType buttonSave = new ButtonType("Save");
    ButtonType buttonCancel = new ButtonType("Cancel");
    ButtonType buttonDontSave = new ButtonType("Don't save");

    alert.getButtonTypes().setAll(buttonSave, buttonCancel, buttonDontSave);

    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent()) {
      if (result.get() == buttonSave) {
        if (saveFile(false)) {
          return true;
        } else {
          return false;
        }
      } else if (result.get() == buttonDontSave) {
        return true;
      }
    }
    return false;
  }

  /**
   * Creates a new journal.
   */
  private void createNewJournal() {
    if (modificationCount.get() > 0) {
      boolean saveResult = showSaveRequest();

      if (!saveResult) {
        return;
      }
    }

    this.manager = new ScheduleManagerImpl();
    this.weekController.setManager(this.manager);
    this.manager.createNewWeek();
    this.weekController.renderWeek();

    modificationCount.set(0);
  }

  /**
   * displays an information alert.
   *
   * @param title   title of the alert
   * @param message message of the alert
   */
  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Opens the settings of the journal.
   */
  private void openSettings() {
    this.settingsController.openSettingsModal(this.manager.getSettings());
    this.weekController.renderWeek();
  }

  /**
   * Gets a password from the user.
   *
   * @param newPassword true if the password is new, false otherwise.
   * @return the password entered by the user.
   */
  private String getUserPassword(boolean newPassword) {
    PasswordView passwordView = new PasswordView();

    return passwordView.requestPassword(newPassword);
  }

  /**
   * Provides functionality to save a bujo file.
   *
   * @param saveAs true if the file is being saved as a new file, false otherwise.
   * @return boolean true if the file was saved, false otherwise.
   */
  private boolean saveFile(boolean saveAs) {
    if (!this.manager.hasFileManager() || saveAs) {
      String password = getUserPassword(true);

      if (password.isEmpty()) {
        showAlert("No password entered!", "Operation cancelled");
        return false;
      }

      String filePath = saveBujoFile();
      if (filePath == null) {
        System.out.println("User did not choose save file. Operation cancelled!");
        return false;
      }
      this.fileManager = new FileManagerImpl(filePath, password);
      this.manager.setFileManager(this.fileManager);
    }

    this.manager.saveData();
    modificationCount.set(0);
    return true;
  }

  /**
   * Creates a new task.
   */
  private void createNewTask() {
    itemCreator.createTask(task -> {
      this.manager.getCurrentWeek().addTask(task);
      modificationCount.incrementAndGet();
      this.weekController.renderWeek();
    });
  }

  /**
   * Creates a new event.
   */
  private void createNewEvent() {
    itemCreator.createEvent(event -> {
      this.manager.getCurrentWeek().addEvent(event);
      modificationCount.incrementAndGet();
      this.weekController.renderWeek();
    });
  }

  /**
   * Opens a file chooser to choose a bujo file.
   *
   * @return a FileManager of the file chosen.
   */
  private FileManager openFile() {
    String filePath = chooseBujoFile();
    if (filePath == null) {
      System.out.println("User did not choose bujo file. Operation cancelled!");
      return null;
    }

    String password = getUserPassword(false);

    if (password.isEmpty()) {
      showAlert("Unable to open file.", "Could not open file because no password was entered.");
      return null;
    }

    return new FileManagerImpl(filePath, password);
  }

  /**
   * Opens a template which is present if the user has not saved the file.
   */
  private void openTemplate() {
    if (modificationCount.get() > 0) {
      boolean saveResult = showSaveRequest();

      if (!saveResult) {
        return;
      }
    }

    FileManager fileManager = openFile();

    if (fileManager == null) {
      return;
    }

    BujoJson bujoJson = fileManager.loadFromFile();

    if (bujoJson == null) {
      showAlert("Unable to open file.",
          "Could not open file. Either the password is incorrect or the bujo file is corrupted.");
      return;
    }

    this.manager.loadTemplate(bujoJson);
    this.manager.setFileManager(null);
    modificationCount.set(1);

    RenameWeekView renameWeekView = new RenameWeekView();

    Optional<String> newWeekName = renameWeekView.showAndWait();

    newWeekName.ifPresent(s -> this.manager.getCurrentWeek().setWeekName(s));

    this.weekController.renderWeek();
  }

  /**
   * Loads a bujo file.
   */
  private void loadFile() {
    if (modificationCount.get() > 0) {
      boolean saveResult = showSaveRequest();

      if (!saveResult) {
        return;
      }
    }

    FileManager fileManager = openFile();

    if (fileManager == null) {
      return;
    }

    BujoJson bujoJson = fileManager.loadFromFile();
    if (bujoJson == null) {
      showAlert("Unable to open file.",
          "Could not open file. Either the password is incorrect or the bujo file is corrupted.");
      return;
    }

    this.fileManager = fileManager;
    this.manager.setFileManager(this.fileManager);
    this.manager.loadData(bujoJson);
    modificationCount.set(0);
    this.weekController.renderWeek();
  }

  /**
   * Opens up a bujo file chooser. Can only take in bujo files.
   *
   * @return a FileChooser that only takes in bujo files.
   */
  private FileChooser getBujoChooser() {
    FileChooser fileChooser = new FileChooser();

    fileChooser.setTitle("Open Bujo File");

    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Bujo Files", "*.bujo"));

    return fileChooser;
  }

  /**
   * Opens up a file chooser dialog for the user to choose a bujo file.
   *
   * @return the absolute path of the file chosen.
   */
  private String chooseBujoFile() {
    FileChooser fileChooser = getBujoChooser();

    File file = fileChooser.showOpenDialog(stage);

    if (file == null) {
      return null;
    }

    return file.getAbsolutePath();
  }

  /**
   * Saves a bujo file.
   *
   * @return the absolute path of the file saved.
   */
  private String saveBujoFile() {
    FileChooser fileChooser = getBujoChooser();

    File file = fileChooser.showSaveDialog(stage);

    if (file == null) {
      return null;
    }

    return file.getAbsolutePath();
  }
}
