package cs3500.pa05.view;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class WeekView extends GridPane {
  public WeekView() {
    // Define an array to represent the days of the week
    String[] daysOfWeek = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};

    // Iterate over the days of the week and add them to the grid
    for (int i = 0; i < daysOfWeek.length; i++) {
      Image img = new Image("file:images/" + daysOfWeek[i] + ".png");
      ImageView view = new ImageView(img);
      Button button = new Button("", view); // Set the button text to empty string and the graphic to our ImageView
      this.add(button, i, 0);
    }
  }
}