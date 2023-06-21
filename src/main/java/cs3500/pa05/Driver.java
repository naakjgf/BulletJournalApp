package cs3500.pa05;

import cs3500.pa05.controller.JournalControllerImpl;
import cs3500.pa05.model.ScheduleManagerImpl;
import cs3500.pa05.view.GuiView;
import cs3500.pa05.view.GuiViewImpl;
import cs3500.pa05.view.SplashScreenViewImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Driver for the Bullet Journal application.
 */
public class Driver extends Application {
  private Stage primaryStage;
  private Scene splashScreen;

  /**
   * Main method for the Bullet Journal application.
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    launch(args);
  }


  @Override
  public void init() {
    SplashScreenViewImpl splashScreenView = new SplashScreenViewImpl();
    splashScreen = splashScreenView.load();
  }


  @Override
  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    Stage splashStage = new Stage();
    splashStage.initStyle(StageStyle.UNDECORATED);
    splashStage.setScene(splashScreen);

    splashStage.show();

    splashScreen.setOnMouseClicked(e -> {
      splashStage.hide();
      loadMainApp();
    });

    splashScreen.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.A) {
        splashStage.hide();
        loadMainApp();
      }
    });
  }

  /**
   * Loads the main application.
   */
  private void loadMainApp() {
    ScheduleManagerImpl scheduleManager = new ScheduleManagerImpl();
    JournalControllerImpl journalController =
        new JournalControllerImpl(primaryStage, scheduleManager);
    GuiView journalView = new GuiViewImpl(journalController);

    try {
      primaryStage.setScene(journalView.load());
      primaryStage.setMinHeight(500.0);
      primaryStage.setMinWidth(1000.0);
      journalController.run();
      primaryStage.show();
    } catch (Exception e) {
      System.out.println("Error loading GUI; Please reference the stack trace below:");
      e.printStackTrace();
    }
  }
}
