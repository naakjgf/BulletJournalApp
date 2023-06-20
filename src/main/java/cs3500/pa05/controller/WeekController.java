package cs3500.pa05.controller;

import cs3500.pa05.enums.DayOfWeek;
import cs3500.pa05.enums.ItemAction;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.ScheduleItem;
import cs3500.pa05.model.ScheduleManager;
import cs3500.pa05.model.Settings;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Week;
import cs3500.pa05.view.EventView;
import cs3500.pa05.view.TaskView;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class WeekController {
  private static final String DEFAULT_WEEK_NAME_FORMAT = "Week %d";
  private static final String FONT_NAME = "Arial";
  private static final int WARNING_FONT_SIZE = 12;
  private static final int TITLE_FONT_SIZE = 14;
  private static final String TASKS_EXCEEDED_WARNING = "Maximum number of tasks exceeded for %s";
  private static final String EVENTS_EXCEEDED_WARNING = "Maximum number of events exceeded for %s";


  private final VBox sideBar;
  private final VBox weeklyOverview;
  private final Label warningLabel;
  private ScheduleManager manager;
  private final HBox weekView;
  private final Label weekTitle;
  private final TextField weekTitleField;
  private final ItemCreationController itemCreator;
  private final Button nextWeek;
  private final Button prevWeek;
  private final AtomicInteger modificationCount;
  private final Button newWeek;


  public WeekController(ScheduleManager manager, ItemCreationController itemCreator,
                        AtomicInteger modificationCount, HBox weekView,
                        Label weekTitle,
                        TextField weekTitleField, Button nextWeek, Button prevWeek, Button newWeek,
                        VBox sideBar, VBox weeklyOverview, Label warningLabel) {
    this.manager = manager;
    this.weekView = weekView;
    this.weekTitle = weekTitle;
    this.weekTitleField = weekTitleField;
    this.nextWeek = nextWeek;
    this.prevWeek = prevWeek;
    this.newWeek = newWeek;
    this.itemCreator = itemCreator;
    this.modificationCount = modificationCount;
    this.sideBar = sideBar;
    this.weeklyOverview = weeklyOverview;
    this.warningLabel = warningLabel;

  }

  public void setManager(ScheduleManager manager) {
    this.manager = manager;
  }

  public static Optional<Map.Entry<DayOfWeek, Integer>> findMax(Map<DayOfWeek, Integer> days) {
    if (days == null || days.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(Collections.max(days.entrySet(), Map.Entry.comparingByValue()));
  }

  public void attachWeekHandlers() {
    nextWeek.setOnAction(e -> updateWeekBy(1));
    prevWeek.setOnAction(e -> updateWeekBy(-1));
    newWeek.setOnAction(e -> createNewWeek());
  }

  private void updateWeekBy(int increment) {
    int currentWeekNumber = this.manager.getCurrentWeek().getWeekNumber();
    int newWeekNumber = currentWeekNumber + increment;

    if (newWeekNumber >= 0 && newWeekNumber < this.manager.getNumWeeks()) {
      this.manager.setCurrentWeek(newWeekNumber);
      renderWeek();
      this.modificationCount.incrementAndGet();
    }
  }

  public void createNewWeek() {
    this.manager.createNewWeek();
    renderWeek();
    this.modificationCount.incrementAndGet();
  }

  public void updateWeekTitle() {
    finishEditWeekTitle();
    Week currentWeek = this.manager.getCurrentWeek();

    if (currentWeek.getWeekName() != null) {
      weekTitle.setText(currentWeek.getWeekName());
    } else {
      int weekNumber = this.manager.getCurrentWeekNum() + 1;
      weekTitle.setText(String.format(DEFAULT_WEEK_NAME_FORMAT, weekNumber));
    }
  }

  public void registerWeekTitleHandlers() {
    weekTitle.addEventFilter(MouseEvent.MOUSE_CLICKED, (mouseEvent) -> {
      if (mouseEvent.getClickCount() == 2) {
        editWeekTitle();
      }
    });

    weekTitleField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        this.manager.getCurrentWeek().setWeekName(weekTitleField.getText());
        this.modificationCount.incrementAndGet();
        updateWeekTitle();
      }
    });

    weekTitleField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
      if (wasFocused && !isNowFocused) {
        this.manager.getCurrentWeek().setWeekName(weekTitleField.getText());
        this.modificationCount.incrementAndGet();
        updateWeekTitle();
      }
    });
  }

  private void finishEditWeekTitle() {
    weekTitle.setVisible(true);  // Set the label's text to the text field's text
    weekTitleField.setVisible(false);
    weekTitleField.setDisable(true);
  }

  private void editWeekTitle() {
    weekTitleField.setText(weekTitle.getText());
    weekTitle.setVisible(false);
    weekTitleField.setVisible(true);
    weekTitleField.setDisable(false);
    weekTitleField.requestFocus();
  }

  public void handleItemAction(ItemAction action, Week w, ScheduleItem item) {
    switch (action) {
      case DELETE -> w.deleteItem(item);
      case EDIT -> itemCreator.editItem(item);
    }

    renderWeek();
  }

  public void renderWeek() {
    Map<DayOfWeek, Integer> taskCountMap = new HashMap<>();
    Map<DayOfWeek, Integer> eventCountMap = new HashMap<>();

    clearExistingViews();
    Week currentWeek = manager.getCurrentWeek();

    renderTasks(currentWeek, taskCountMap);
    renderEvents(currentWeek, eventCountMap);

    renderWeeklyOverview(currentWeek.getTasks(), currentWeek.getEvents());

    alertMaximumItems(taskCountMap, eventCountMap);
    updateWeekTitle();
  }

  private void clearExistingViews() {
    for (Node node : weekView.getChildren()) {
      if (node instanceof ScrollPane scrollPane) {
        VBox vbox = (VBox) scrollPane.getContent();
        vbox.getChildren()
            .removeIf(child -> child instanceof TaskView || child instanceof EventView);
      }
    }
    sideBar.getChildren().removeIf(child -> !(child instanceof Label));
  }

  private void renderTasks(Week currentWeek, Map<DayOfWeek, Integer> taskCountMap) {
    for (Task t : currentWeek.getTasks()) {
      TaskView tView = new TaskView(t,
          (ItemAction action) -> handleItemAction(action, currentWeek, t));
      taskCountMap.merge(t.getDayOfWeek(), 1, Integer::sum);
      addTaskToGUI(t, tView);
      addCompletionStatusToSideBar(t);
    }
  }

  private void addTaskToGUI(Task task, TaskView taskView) {
    VBox vbox = (VBox) ((ScrollPane) weekView.getChildren()
        .get(task.getDayOfWeek().getNumVal())).getContent();
    vbox.getChildren().add(taskView);
  }

  private void addCompletionStatusToSideBar(Task task) {
    VBox sidebarTask = new VBox(5);
    sidebarTask.getStyleClass().add("sidebarTask");
    Label nameLabel = new Label(task.getName());
    nameLabel.setFont(Font.font(FONT_NAME, FontWeight.BOLD, TITLE_FONT_SIZE));
    Label completeLabel = new Label(task.isComplete() ? "Completed" : "Incomplete");
    completeLabel.setFont(Font.font(FONT_NAME, WARNING_FONT_SIZE));
    sidebarTask.getChildren().addAll(nameLabel, completeLabel);
    sideBar.getChildren().add(sidebarTask);
  }

  private void renderEvents(Week currentWeek, Map<DayOfWeek, Integer> eventCountMap) {
    currentWeek.getEvents().sort(Comparator.comparingLong(Event::getStartTime));
    for (Event e : currentWeek.getEvents()) {
      EventView eView =
          new EventView(e, (ItemAction action) -> handleItemAction(action, currentWeek, e));
      eventCountMap.merge(e.getDayOfWeek(), 1, Integer::sum);
      addEventToGUI(e, eView);
    }
  }

  private void addEventToGUI(Event event, EventView eventView) {
    VBox vbox = (VBox) ((ScrollPane) weekView.getChildren()
        .get(event.getDayOfWeek().getNumVal())).getContent();
    vbox.getChildren().add(eventView);
  }

  private VBox createWeeklyOverviewValue(String labelVal, String value) {
    VBox vbox = new VBox();
    Label label = new Label(labelVal);
    label.setFont(Font.font("Verdana", FontWeight.BOLD, 13));

    Label valLabel = new Label(value);
    valLabel.setFont(Font.font("Verdana", 13));
    vbox.getChildren().addAll(label, valLabel);

    return vbox;
  }

  private void renderWeeklyOverview(List<Task> tasks, List<Event> events) {
    // Clear weekly overview
    weeklyOverview.getChildren().removeIf(n -> n instanceof VBox);

    if (tasks.size() > 0) {
      int numOfTasks = tasks.size();
      NumberFormat percent = NumberFormat.getPercentInstance();

      long numTasksCompleted = tasks.stream().filter(Task::isComplete).count();
      double tasksCompletedFraction = (double) numTasksCompleted / (double) numOfTasks;

      String percentFormat = percent.format(tasksCompletedFraction);


      VBox numTasksVBox = createWeeklyOverviewValue("Total Tasks", String.valueOf(numOfTasks));
      VBox taskPercentVBox = createWeeklyOverviewValue("Tasks Completed", percentFormat);

      weeklyOverview.getChildren().addAll(numTasksVBox, taskPercentVBox);
    }

    if (events.size() > 0) {
      int numOfEvents = events.size();
      VBox numEventsVBox = createWeeklyOverviewValue("Total Events", String.valueOf(numOfEvents));

      weeklyOverview.getChildren().add(numEventsVBox);
    }
  }

  private void alertMaximumItems(Map<DayOfWeek, Integer> taskCountMap,
                                 Map<DayOfWeek, Integer> eventCountMap) {
    Settings settings = this.manager.getSettings();

    warningLabel.setText("");

    findMax(taskCountMap).ifPresent(entry -> {
      if (settings.getMaximumTasks() != 0 && entry.getValue() > settings.getMaximumTasks()) {
        warningLabel.setText(String.format(TASKS_EXCEEDED_WARNING, entry.getKey()));
      }
    });

    findMax(eventCountMap).ifPresent(entry -> {
      if (settings.getMaximumEvents() != 0 && entry.getValue() > settings.getMaximumEvents()) {
        warningLabel.setText(String.format(EVENTS_EXCEEDED_WARNING, entry.getKey()));
      }
    });
  }
}
