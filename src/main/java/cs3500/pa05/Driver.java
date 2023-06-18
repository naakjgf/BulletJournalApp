package cs3500.pa05;

import cs3500.pa05.controller.JournalControllerImpl;
import cs3500.pa05.model.ScheduleManagerImpl;
import cs3500.pa05.model.file_manager.FileManager;
import cs3500.pa05.model.file_manager.FileManagerImpl;
import cs3500.pa05.view.GuiView;
import cs3500.pa05.view.GuiViewImpl;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Driver for the Bullet Journal application.
 */
public class Driver extends Application {
  /**
   * Main method for the Bullet Journal application.
   *
   * @param args Command line arguments.
    */
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gui.fxml"));
      primaryStage.setTitle("Bullet Journal");
      primaryStage.setScene(new Scene(Objects.requireNonNull(loader.load())));
      primaryStage.show();
    } catch (Exception e) {
      System.out.println("Error loading GUI; Please reference the stack trace below:");
      e.printStackTrace();
    }

    ScheduleManagerImpl scheduleManager = new ScheduleManagerImpl();
    JournalControllerImpl controller = new JournalControllerImpl(primaryStage, scheduleManager);

    GuiView view = new GuiViewImpl(controller);

  }
}
