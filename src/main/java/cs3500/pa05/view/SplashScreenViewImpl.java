package cs3500.pa05.view;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * Implementation of GuiView.
 */
public class SplashScreenViewImpl implements GuiView {
  private final FXMLLoader loader;

  /**
   * Constructor for SplashScreenViewImpl.
   */
  public SplashScreenViewImpl() {
    this.loader = new FXMLLoader();
    this.loader.setLocation(getClass().getClassLoader().getResource("splashScreen.fxml"));
  }

  /**
   * Loads a scene from the splash screen layout.
   *
   * @return the layout
   */
  @Override
  public Scene load() throws IllegalStateException {
    try {
      return this.loader.load();
    } catch (IOException exc) {
      exc.printStackTrace();
      throw new IllegalStateException("Unable to load layout.");
    }
  }
}
