package cs3500.pa05;

import cs3500.pa05.controller.JournalControllerImpl;
import cs3500.pa05.model.ScheduleManagerImpl;
import cs3500.pa05.model.file_manager.FileManager;
import cs3500.pa05.model.file_manager.FileManagerImpl;
import cs3500.pa05.view.GuiView;
import cs3500.pa05.view.GuiViewImpl;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Driver for the application.
 */
public class Driver extends Application {
  @Override
  public void start(Stage stage) {
    ScheduleManagerImpl manager = new ScheduleManagerImpl();

    JournalControllerImpl controller = new JournalControllerImpl(manager);

    GuiView view = new GuiViewImpl(controller);
  }

  /**
   * Main method.
   *
   * @param args input args.
   */
  public static void main(String[] args) {
    launch();
  }
}
