package cs3500.pa05.view;


import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class WelcomeScreenView extends VBox {

  private TextField filePathInput = new TextField();
  private Button openButton = new Button("Open File");
  private Text filePathText = new Text();

  public WelcomeScreenView() {
    this.setSpacing(10);
    this.getChildren().addAll(filePathInput, openButton, filePathText);
  }

  public Button getOpenButton() {
    return openButton;
  }

  public TextField getFilePathInput() {
    return filePathInput;
  }

  public void setFilePathText(String text) {
    this.filePathText.setText(text);
  }
}
