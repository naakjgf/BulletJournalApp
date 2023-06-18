package cs3500.pa05;

import cs3500.pa05.controller.JournalControllerImpl;
import cs3500.pa05.model.ScheduleManagerImpl;
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
      Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gui.fxml")));
      primaryStage.setTitle("Bullet Journal");
      primaryStage.setScene(new Scene(root, 800, 800));
      primaryStage.show();
    } catch (Exception e) {
      System.out.println("Error loading GUI; Please reference the stack trace below:");
      e.printStackTrace();
    }

    //having it take a ScheduleManager as a parameter may not be the best way to go about this?
    //especially considering it isn't used yet in the impl it isn't too late to adjust this,
    //achieves the same thing making an instance in the constructor instead of the driver, up to you
    // though as you have been the one designing it, dealers' choice.
    FileManager fileManager = new FileManagerImpl(filepath);
    ScheduleManagerImpl scheduleManager = new ScheduleManagerImpl(fileManager);
    JournalControllerImpl controller = new JournalControllerImpl(scheduleManager);

    GuiView view = new GuiViewImpl(controller);

  }
}
