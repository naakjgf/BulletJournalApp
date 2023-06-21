package GuiTests;

import cs3500.pa05.Driver;
import cs3500.pa05.enums.DayOfWeek;
import java.time.LocalTime;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

@SuppressWarnings("unchecked")
public class EventCreationTest extends ApplicationTest {

  private String eventName;
  private String eventDescription;
  private DayOfWeek eventDay;
  private String eventDate;
  private LocalTime eventTime;

  @Override
  public void start(Stage stage) throws Exception {
    stage.show();
  }

  @BeforeEach
  public void setUp() throws Exception {
    eventName = "Test Event";
    eventDescription = "Test Description";
    eventDay = DayOfWeek.MONDAY;
    eventDate = "2023-06-14";
    eventTime = LocalTime.now();
  }

  @AfterEach
  public void tearDown() throws Exception {
    FxToolkit.hideStage();
    release(new KeyCode[]{});
    release(new MouseButton[]{});
  }

  @Test
  public void testInsertAndCreateNewEvent() throws Exception {
    ApplicationTest.launch(Driver.class);
    //mouse click on the screen to get past the splash screen
    clickOn();

    clickOn("Insert");
    clickOn("New Event");

    clickOn("Create");
    clickOn("OK");
    clickOn("OK");

    clickOn("Name");
    write(eventName);

    clickOn("Description");
    write(eventDescription);

    /*write(eventName);
    write(eventDescription);*/

    ComboBox<DayOfWeek> dayOfWeekComboBox = lookup("Day").queryComboBox();
    interact(() -> dayOfWeekComboBox.getSelectionModel().select(eventDay));

    /*DatePicker datePicker = lookup("Start Date").queryDatePicker();
    interact(() -> datePicker.setValue(eventDate));*/
    clickOn("Start Date");
    write(eventDate);

    Spinner<LocalTime> timeSpinner = lookup(".spinner").queryAs(Spinner.class);
    interact(() -> timeSpinner.getValueFactory().setValue(LocalTime.MIDNIGHT));

    /*Spinner<LocalTime> timeSpinner = lookup("#timeSpinner").querySpinner();
    interact(() -> timeSpinner.getValueFactory().setValue(eventTime));*/

    clickOn("Create");

    FxAssert.verifyThat("#monday", NodeMatchers.hasChild(eventName));

    clickOn(eventName);
    clickOn("Edit");
    interact(() -> dayOfWeekComboBox.getSelectionModel().select(DayOfWeek.TUESDAY));
    clickOn("Save");

    FxAssert.verifyThat("#tuesday", NodeMatchers.hasChild(eventName));

    clickOn(eventName);
    clickOn("Delete");

    //got rid of only child of that vbox, want to check if it is empty.
    FxAssert.verifyThat("#tuesday", NodeMatchers.hasChild(""));

    press(KeyCode.COMMAND, KeyCode.E);
    clickOn("Cancel");
  }

  /*@Test
  public void testCreateAndEditEvent() {
    // Go to Insert and create new event
    clickOn("Insert"); // replace with the correct selector if needed

    // On the pop up dialog -
    // Attempt to click Create without putting anything in
    clickOn("Create"); // replace with the correct selector if needed

    // Input name into name field TEXTFIELD
    clickOn("Name");
    write("Test Event");

    // Description into description field TEXTFIELD
    clickOn("Description");
    write("This is a test event.");

    // Click on combobox and select an element COMBOBOX
    clickOn("Day");
    clickOn("MONDAY"); // replace with the correct value if needed

    // See if it is possible to click forward 1 minute on the spinner SPINNER
    // TestFX doesn't have built-in support for Spinners, so we'll simulate typing the time directly
    clickOn("Start Time"); // replace with the correct prompt text if needed
    eraseText(8);
    write("12:34 PM");

    // Click on date-picker and put click on a random day DATEPICKER
    clickOn("Start Date"); // replace with the correct prompt text if needed
    press(KeyCode.DOWN); // navigate to a different date
    press(KeyCode.ENTER); // select the date

    // Attempt to submit one without a description but all other fields, then one with a description (optional?) click save.
    clickOn("Description");
    eraseText(21);
    clickOn("Create");
    // Verify that the event pops up under the vbox of the selected day
    FxAssert.verifyThat("#eventVbox", NodeMatchers.hasChild( // replace #eventVbox with the correct fx:id if needed
        LabeledMatchers.hasText("Test Event"))); // replace with a more specific matcher if needed

    // Click on the event, click on edit button, click on the COMBOBOX if it easy to do and select a different day, click save again.
    clickOn("Test Event");
    clickOn("Edit"); // replace with the correct selector if needed
    clickOn("Day");
    clickOn("TUESDAY"); // replace with the correct value if needed
    clickOn("Save"); // replace with the correct selector if needed

    // Verify it appeared on that other day
    // This depends on the structure of your VBox and how you're showing the days

    // Click on the event again, click Delete
    clickOn("Test Event");
    clickOn("Delete"); // replace with the correct selector if needed

    // If possible figure out how to input command+E
    // And quickly create an event in a similar way to above. Or just click cancel.
    press(KeyCode.COMMAND, KeyCode.E);
    // fill out the dialog as above
  }*/
}
