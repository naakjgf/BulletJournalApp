package cs3500.pa05.controller;


import cs3500.pa05.view.WelcomeScreenView;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class WelcomeScreenController {
  private final WelcomeScreenView view;

  private File currentFile;
  private final Stage stage;

  public WelcomeScreenController(WelcomeScreenView view, Stage stage) {
    this.view = view;
    this.stage = stage;

    setupEventHandlers();
  }

  public String getReadFileContent()
  {
    try {
      return new String(Files.readAllBytes(Paths.get(currentFile.toURI())));
    } catch (Exception ex) {
      return "Error reading file: " + ex.getMessage();
    }

  }
  private void setupEventHandlers() {
    FileChooser fileChooser = new FileChooser();

    view.getOpenButton().setOnAction(e -> {
      File file = fileChooser.showOpenDialog(stage);
      if (file != null) {
        currentFile = file;
        view.setFilePathText("File selected: " + file.getAbsolutePath());
      }
    });
  }
}
