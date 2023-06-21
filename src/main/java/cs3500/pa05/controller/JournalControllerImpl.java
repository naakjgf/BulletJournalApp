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
import cs3500.pa05.model.file_manager.json.BujoJson;
import cs3500.pa05.view.EventView;
import cs3500.pa05.view.PasswordView;
import cs3500.pa05.view.TaskView;
import java.io.File;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
   * Constructor for JournalController.
   */
  public JournalControllerImpl() {

  }

  @FXML
  public void run() {
    this.weekController = new WeekController(manager, itemCreator, modificationCount, weekView, weekTitle, weekTitleField, nextWeek, prevWeek,
        newWeek, sideBar, weeklyOverview, warningLabel);

    this.menuController.attachMenuHandlers(menuBarContainer);

    this.weekController.attachWeekHandlers();
    this.manager.createNewWeek();
    this.weekController.updateWeekTitle();
    this.weekController.registerWeekTitleHandlers();
    createCloseHandler();
  }



  private void handleMenuAction(ActionEvent e, MenuBarAction action) {
    switch (action) {
      case OPEN -> loadFile();
      case SAVE -> saveFile(false);
      case SAVE_AS -> saveFile(true);
      case NEW_WEEK -> this.weekController.createNewWeek();
      case NEW_TASK -> createNewTask();
      case NEW_EVENT -> createNewEvent();
      case OPEN_SETTINGS -> openSettings();
      case NEW_JOURNAL -> createNewJournal();
    }
  }

  private void createCloseHandler() {
    this.stage.setOnCloseRequest(event -> {
      if (modificationCount.get() > 0) {
        boolean result = showSaveRequest();

        if (result) {
          this.stage.close();
        }
      }
    });
  }

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
          showAlert("Unable to save file", "User cancelled operation!");
          return false;
        }
      } else if (result.get() == buttonDontSave) {
        return true;
      }
    }

    return false;
  }

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

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  private void openSettings() {
    this.settingsController.openSettingsModal(this.manager.getSettings());
    this.weekController.renderWeek();
  }

  private String getUserPassword(boolean newPassword) {
    PasswordView passwordView = new PasswordView();
    String password = passwordView.requestPassword(newPassword);

    return password;
  }

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

  private void createNewTask() {
    itemCreator.createNewTask(task -> {
      this.manager.getCurrentWeek().addTask(task);
      modificationCount.incrementAndGet();
      this.weekController.renderWeek();
    });
  }

  private void createNewEvent() {
    itemCreator.createNewEvent(event -> {
      this.manager.getCurrentWeek().addEvent(event);
      modificationCount.incrementAndGet();
      this.weekController.renderWeek();
    });
  }



  private void loadFile() {
    if (modificationCount.get() > 0) {
      boolean saveResult = showSaveRequest();

      if (!saveResult) {
        return;
      }
    }

    String filePath = chooseBujoFile();
    if (filePath == null) {
      System.out.println("User did not choose save file. Operation cancelled!");
      return;
    }

    String password = getUserPassword(false);

    if (password.isEmpty()) {
      showAlert("Unable to open file.", "Could not open file because no password was entered.");
      return;
    }

    this.fileManager = new FileManagerImpl(filePath, password);

    BujoJson bujoJson = this.fileManager.loadFromFile();
    if (bujoJson == null) {
      showAlert("Unable to open file.", "Could not open file. Either the password is incorrect or the bujo file is corrupted.");
      return;
    }

    this.manager.setFileManager(this.fileManager);
    this.manager.loadData(bujoJson);
    modificationCount.set(0);
    this.weekController.renderWeek();
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
