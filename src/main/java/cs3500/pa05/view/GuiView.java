package cs3500.pa05.view;
import javafx.scene.Scene;

/**
 * Represents a GUI view for a Bullet Journal Schedule
 */
public interface GuiView {
  /**
   * Loads a scene from a Schedule GUI layout.
   *
   * @return the layout
   */
  Scene load() throws IllegalStateException;
}
