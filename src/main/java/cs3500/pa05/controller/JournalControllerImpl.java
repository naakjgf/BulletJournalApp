package cs3500.pa05.controller;

import cs3500.pa05.enums.DayOfWeek;
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
import java.net.URL;
import java.util.Comparator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Implementation of a Journal Controller.
 */
public class JournalControllerImpl implements JournalController {
  private ScheduleManager manager;
  private FileManager fileManager;
  private Settings settings;
  private Stage stage;
  private Scene originalScene;
  private String originalStylesheet;


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

  public void setOriginalScene(Scene scene) {
    this.originalScene = scene;
  }

  @Override
  public void updateCurrentWeek() {

  }

  @FXML
  public void run() {

  }


  @FXML
  public void switchToScene2(ActionEvent buttonClicked) {
    if (this.originalScene == null) {
      System.out.println("Original scene is null");
    }
    this.originalScene = this.stage.getScene();
    if (this.originalScene == null) {
      System.out.println("Original scene is null after setting it");
    }
    if (!this.originalScene.getStylesheets().isEmpty()) {
      this.originalStylesheet = this.originalScene.getStylesheets().get(0);
    }

    FXMLLoader loader =
        new FXMLLoader(getClass().getClassLoader().getResource("newItemDayChoice.fxml"));
    try {
      loader.setController(JournalControllerImpl.this);
      Scene scene2 = loader.load();
      URL stylesheet = getClass().getClassLoader().getResource("styles.css");
      if (stylesheet != null) {
        scene2.getStylesheets().add(stylesheet.toExternalForm());
      }
      this.stage.setScene(scene2);
    } catch (IOException exc) {
      exc.printStackTrace();
      throw new IllegalStateException("Unable to load layout.", exc);
    }
  }

  @FXML
  public void switchToScene1() {
    if (this.originalScene != null) {
      if (this.originalStylesheet != null) {
        this.originalScene.getStylesheets().add(this.originalStylesheet);
      }
      this.stage.setScene(this.originalScene);
    } else {
      throw new IllegalStateException("Original scene is not set.");
    }
  }

  public void newEvent(DayOfWeek day) {
    //will need to replace this with this.manager. create new event implementation
    System.out.println("New Event on " + day + " created.");
  }

  public void newTask() {
    //will need to replace this with this.manager. create new task implementation
    System.out.println("New Task Created");
  }

  @FXML
  public void newMondayEvent() {
    newEvent(DayOfWeek.MONDAY);
    switchToScene1();
  }

  @FXML
  public void newTuesdayEvent() {
    newEvent(DayOfWeek.TUESDAY);
    switchToScene1();
  }

  @FXML
  public void newWednesdayEvent() {
    newEvent(DayOfWeek.WEDNESDAY);
    switchToScene1();
  }

  @FXML
  public void newThursdayEvent() {
    newEvent(DayOfWeek.THURSDAY);
    switchToScene1();
  }

  @FXML
  public void newFridayEvent() {
    newEvent(DayOfWeek.FRIDAY);
    switchToScene1();
  }

  @FXML
  public void newSaturdayEvent() {
    newEvent(DayOfWeek.SATURDAY);
    switchToScene1();
  }

  @FXML
  public void newSundayEvent() {
    newEvent(DayOfWeek.SUNDAY);
    switchToScene1();
  }
  /*
    public void newMondayTask() {

    }
    public void newTuesdayTask() {

    }
    public void newWednesdayTask() {

    }
    public void newThursdayTask() {

    }
    public void newFridayTask() {

    }
    public void newSaturdayTask() {

    }
    public void newSundayTask() {

    }*/


  public void renderWeek() {
    for (int i = 0; i < manager.getNumWeeks(); i++) {
      for (Task t : manager.getWeek(i).getTasks()) {
        TaskView tView = new TaskView(t);
        //TODO:
        //Add tView to GUI
        //Add checkbox somewhere
      }
      manager.getWeek(i).getEvents().sort(Comparator.comparingLong(Event::getStartTime));
      for (Event e : manager.getWeek(i).getEvents()) {
        EventView eView = new EventView(e);
        //TODO:
        //add eView to GUI
      }
    }
  }

  public void saveFile() {
    if (!this.manager.hasFileManager()) {
      String filePath = saveBujoFile();
      if (filePath == null) {
        System.out.println("User did not choose save file. Operation cancelled!");
        return;
      }
      this.fileManager = new FileManagerImpl(filePath);
      this.manager.setFileManager(this.fileManager);
    }

    this.manager.saveWeeks();
  }

  public void loadFile() {
    String filePath = chooseBujoFile();
    if (filePath == null) {
      System.out.println("User did not choose save file. Operation cancelled!");
      return;
    }
    this.fileManager = new FileManagerImpl(filePath);
    this.manager.setFileManager(this.fileManager);
    this.manager.loadWeeks();
    this.settings = this.fileManager.loadSettingsFromFile();
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
