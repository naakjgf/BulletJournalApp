package cs3500.pa05.view;

import cs3500.pa05.controller.JournalControllerImpl;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * Implementation of GuiView.
 */
public class GuiViewImpl implements GuiView {
  private final FXMLLoader loader;

  /**
   * Constructor for GuiViewImpl.
   *
   * @param controller Controller for journal.
   */
  public GuiViewImpl(JournalControllerImpl controller) {
    this.loader = new FXMLLoader();
    this.loader.setLocation(getClass().getClassLoader().getResource("gui.fxml"));
    this.loader.setController(controller);
  }

  /**
   * Loads a scene from the Schedule GUI layout.
   *
   * @return the layout
   */
  @Override
  public Scene load() throws IllegalStateException {
    try {
      return this.loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }
}
