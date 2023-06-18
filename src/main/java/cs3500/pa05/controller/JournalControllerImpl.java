package cs3500.pa05.controller;

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
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
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
  private VBox sidebar;

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

  }

  public void renderWeeks() {
    for (int i = 0; i < manager.getNumWeeks(); i++) {
      for(Task t : manager.getWeek(i).getTasks())
      {
        TaskView tView = new TaskView(t);
        //Add tView to GUI
        VBox myVBox = (VBox) weekView.getChildren().get(t.getDayOfWeek().getNumVal());
        myVBox.getChildren().add(tView);
        //Add checkbox to sidebar
        sidebar.getChildren().add(new CheckBox(t.getName()));
      }
      manager.getWeek(i).getEvents().sort(Comparator.comparingLong(Event::getStartTime));
      for (Event e: manager.getWeek(i).getEvents())
      {
        EventView eView = new EventView(e);
        //TODO:
        VBox myVBox = (VBox) weekView.getChildren().get(e.getDayOfWeek().getNumVal());
        myVBox.getChildren().add(eView);
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
