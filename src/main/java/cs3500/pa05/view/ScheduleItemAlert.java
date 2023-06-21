package cs3500.pa05.view;

import cs3500.pa05.enums.ItemAction;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.ScheduleItem;
import cs3500.pa05.model.Task;
import java.util.function.Consumer;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Represents a popup for a schedule item.
 */
public class ScheduleItemAlert extends Dialog<Void> {
  /**
   * Constructor for ScheduleItemAlert.
   *
   * @param item ScheduleItem to view info for.
   * @param callback Callback with the ItemAction intended by the user.
   */
  public ScheduleItemAlert(ScheduleItem item, Consumer<ItemAction> callback) {
    super();
    setTitle("Manage " + (item instanceof Task ? "Task" : "Event") + ": " + item.getName());
    setHeaderText(item.getName());

    // RUN callback WITH ItemAction ENUM TYPE FOR EVENT
    createDialogue(callback);

    // Set Content

    Label descriptionLabel = new Label("Description");
    descriptionLabel.setFont(new Font(15));
    Label description = new Label(item.getDescription());
    description.setWrapText(true);

    VBox vbox = new VBox();
    addField(vbox, "Description", item.getDescription());
    addField(vbox, "Day", item.getDayOfWeek().toString());

    if (item instanceof Task) {
      addField(vbox, "Completed", String.valueOf(((Task) item).isComplete()));
    } else if (item instanceof Event) {
      addField(vbox, "Start Time", EventView.formatTime(((Event) item).getStartTime()));
      addField(vbox, "Duration", ((Event) item).getDuration() + " minutes");
    }


    getDialogPane().setContent(vbox);
  }

  /**
   * Creates the dialogue for the popup.
   *
   * @param callback Callback with the ItemAction intended by the user.
   */
  private void createDialogue(Consumer<ItemAction> callback) {
    ButtonType editButtonType = new ButtonType("Edit", ButtonBar.ButtonData.OTHER);
    ButtonType deleteButtonType = new ButtonType("Delete", ButtonBar.ButtonData.LEFT);
    ButtonType closeButtonType = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
    getDialogPane().getButtonTypes().addAll(editButtonType, deleteButtonType, closeButtonType);

    Button deleteButton = (Button) getDialogPane().lookupButton(deleteButtonType);
    deleteButton.setOnAction(e -> {
      close();
      callback.accept(ItemAction.DELETE);
    });

    Button editButton = (Button) getDialogPane().lookupButton(editButtonType);
    editButton.setOnAction(e -> {
      close();
      callback.accept(ItemAction.EDIT);
    });
  }

  /**
   * Adds a field to the popup.
   *
   * @param parent Parent pane to add field to.
   * @param title Title of the field.
   * @param value Value of the field.
   */
  private void addField(Pane parent, String title, String value) {
    Label label = new Label(title);
    label.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
    Label content = new Label(value);
    content.setWrapText(true);
    content.setStyle("-fx-padding: 0px 0px 12px 0px");
    parent.getChildren().addAll(label, content);
  }
}