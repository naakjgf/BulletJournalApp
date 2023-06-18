package cs3500.pa05;

import cs3500.pa05.controller.JournalControllerImpl;
import cs3500.pa05.model.ScheduleManagerImpl;
import cs3500.pa05.view.GuiView;
import cs3500.pa05.view.GuiViewImpl;
import javafx.application.Application;
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
    ScheduleManagerImpl scheduleManager = new ScheduleManagerImpl();
    JournalControllerImpl controller = new JournalControllerImpl(primaryStage, scheduleManager);

    GuiView view = new GuiViewImpl(controller);

    try {
//      FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gui.fxml"));
//      primaryStage.setTitle("Bullet Journal");

      Scene originalScene = view.load();
      controller.setOriginalScene(originalScene);
      primaryStage.setScene(originalScene);
      primaryStage.show();
    } catch (Exception e) {
      System.out.println("Error loading GUI; Please reference the stack trace below:");
      e.printStackTrace();
    }
  }
}
