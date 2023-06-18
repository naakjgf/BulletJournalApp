package cs3500.pa05.view;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class WeekView extends GridPane {
  public WeekView() {

    String[] daysOfWeek = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};

    for (int i = 0; i < daysOfWeek.length; i++) {
      Image img = new Image("file:images/" + daysOfWeek[i] + ".png");
      ImageView view = new ImageView(img);
      Button button = new Button("", view);
      this.add(button, i, 0);
    }
  }
}