package cs3500.pa05.controller;

import cs3500.pa05.enums.DayOfWeek;
import cs3500.pa05.enums.MenuBarAction;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.ScheduleManager;
import cs3500.pa05.model.Settings;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.file_manager.FileManager;
import cs3500.pa05.model.file_manager.FileManagerImpl;
import cs3500.pa05.view.EventView;
import cs3500.pa05.view.TaskView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Implementation of a Journal Controller.
 */
public class JournalControllerImpl implements JournalController {
  private ScheduleManager manager;
  private FileManager fileManager;
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
  private Button prevWeek;
  @FXML
  private Button nextWeek;
  @FXML
  private Button newWeek;


  private Stage stage;

  /**
   * Constructor for a JournalController.
   *
   * @param manager ScheduleManager to retrieve Week data from.
   */
  public JournalControllerImpl(Stage stage, ScheduleManager manager) {
    this.manager = manager;
    this.stage = stage;
  }

  /**
   * Constructor for JournalController.
   */
  public JournalControllerImpl() {

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
  }

  private void attachWeekHandlers() {
    nextWeek.setOnAction((e) -> {
      if (this.manager.getCurrentWeek().getWeekNumber() >= this.manager.getNumWeeks() - 1) {
        return;
      }

      this.manager.setCurrentWeek(this.manager.getCurrentWeek().getWeekNumber() + 1);
      renderWeeks();
    });

    prevWeek.setOnAction((e) -> {
      if (this.manager.getCurrentWeek().getWeekNumber() <= 0) {
        return;
      }

      this.manager.setCurrentWeek(this.manager.getCurrentWeek().getWeekNumber() + 1);
      renderWeeks();
    });

    newWeek.setOnAction(e -> createNewWeek());
  }


  /**
   * Updates the week title to the current active week.
   */
  public void updateWeekTitle() {
    weekTitle.setText("Week " + (this.manager.getCurrentWeek().getWeekNumber() + 1));
  }


  private void handleMenuAction(ActionEvent e, MenuBarAction action) {
    switch (action) {
      case OPEN -> {
        loadFile();
        updateWeekTitle();
      }
      case SAVE -> saveFile(false);
      case SAVE_AS -> saveFile(true);
      case NEW_WEEK -> createNewWeek();
      case NEW_TASK -> createNewTask();
    }

  }

  private void createNewWeek() {
    this.manager.createNewWeek();
    renderWeeks();
    updateWeekTitle();
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
    MenuItem itemSave = createMenuItem(MenuBarAction.SAVE, "Save", createKeybinds);
    MenuItem itemSaveAs = createMenuItem(MenuBarAction.SAVE_AS, "Save As", createKeybinds);
    MenuItem itemOpen = createMenuItem(MenuBarAction.OPEN, "Open", createKeybinds);

    menuFile.getItems().addAll(itemSave, itemSaveAs, itemOpen);

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

  public void renderWeeks() {
    for (int i = 0; i < manager.getNumWeeks(); i++) {
      for (Task t : manager.getWeek(i).getTasks()) {
        TaskView tView = new TaskView(t);
        //Add tView to GUI
        VBox myVBox = (VBox) weekView.getChildren().get(t.getDayOfWeek().getNumVal());
        myVBox.getChildren().add(tView);
        //Add checkbox to sidebar
        sideBar.getChildren().add(new CheckBox(t.getName()));
      }
      manager.getWeek(i).getEvents().sort(Comparator.comparingLong(Event::getStartTime));
      for (Event e : manager.getWeek(i).getEvents()) {
        EventView eView = new EventView(e);
        VBox myVBox = (VBox) weekView.getChildren().get(e.getDayOfWeek().getNumVal());
        myVBox.getChildren().add(eView);
      }


    }
  }

  public void saveFile(boolean saveAs) {
    if (!this.manager.hasFileManager() || saveAs) {
      String filePath = saveBujoFile();
      if (filePath == null) {
        System.out.println("User did not choose save file. Operation cancelled!");
        return;
      }
      this.fileManager = new FileManagerImpl(filePath);
      this.manager.setFileManager(this.fileManager);
    }

    this.manager.saveData();
  }

  private void createNewTask() {
    Dialog<Task> dialog = new Dialog<>();
    dialog.setTitle("Create a new Task");

    DialogPane dialogPane = new DialogPane();
    dialog.setDialogPane(dialogPane);

    TextField nameField = new TextField();
    nameField.setPromptText("Name");

    TextField descriptionField = new TextField();
    descriptionField.setPromptText("Description");

    ComboBox<DayOfWeek> dayOfWeekComboBox = new ComboBox<>();
    dayOfWeekComboBox.getItems().addAll(DayOfWeek.values());

    ButtonType createBtnType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
    dialogPane.getButtonTypes().addAll(createBtnType, ButtonType.CANCEL);

    dialogPane.lookupButton(createBtnType).addEventFilter(javafx.event.ActionEvent.ACTION, ev -> {
      String name = nameField.getText();
      String description = descriptionField.getText();
      DayOfWeek day = dayOfWeekComboBox.getValue();

      if (name.isEmpty() || description.isEmpty() || day == null) {
        ev.consume();  // stop the dialog from closing
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Please fill all fields!");
        alert.showAndWait();
      }
    });

    dialog.setResultConverter(buttonType -> {
      if (buttonType == createBtnType) {
        String name = nameField.getText();
        String description = descriptionField.getText();
        DayOfWeek day = dayOfWeekComboBox.getValue();

        return new Task(name, description, day);
      }
      return null;
    });

    VBox dialogVbox = new VBox(10);
    dialogVbox.setPadding(new Insets(20, 20, 20, 20));
    dialogVbox.getChildren().addAll(nameField, descriptionField, dayOfWeekComboBox);

    dialogPane.setContent(dialogVbox);

    Optional<Task> result = dialog.showAndWait();

    result.ifPresent(task -> {
      // handle task object here
      this.manager.getCurrentWeek().addTask(task);
      renderWeeks();
    });
  }

  public void loadFile() {
    String filePath = chooseBujoFile();
    if (filePath == null) {
      System.out.println("User did not choose save file. Operation cancelled!");
      return;
    }
    this.fileManager = new FileManagerImpl(filePath);
    this.manager.setFileManager(this.fileManager);
    this.manager.loadData();
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
