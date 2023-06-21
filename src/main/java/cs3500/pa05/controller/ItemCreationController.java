package cs3500.pa05.controller;

import cs3500.pa05.model.Event;
import cs3500.pa05.model.ScheduleItem;
import cs3500.pa05.model.Task;
import cs3500.pa05.view.manager.EventCreationView;
import cs3500.pa05.view.manager.EventEditingView;
import cs3500.pa05.view.manager.TaskCreationView;
import cs3500.pa05.view.manager.TaskEditingView;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Controller for managing item creation and editing.
 */
public class ItemCreationController {

  /**
   * Edits a task using a TaskEditingView dialog.
   *
   * @param task Task to edit.
   */
  public void editTask(Task task) {
    TaskEditingView dialog = new TaskEditingView(task);
    Optional<ScheduleItem> result = dialog.showAndWait();

    result.ifPresent(scheduleItem -> {
      Task taskResult = (Task) scheduleItem;

      task.setName(taskResult.getName());
      task.setDescription(taskResult.getDescription());
      task.setDayOfWeek(taskResult.getDayOfWeek());
    });
  }

  /**
   * Edits an event using the EventEditingView dialog.
   *
   * @param event Event to edit.
   */
  public void editEvent(Event event) {
    EventEditingView dialog = new EventEditingView(event);

    Optional<ScheduleItem> result = dialog.showAndWait();

    result.ifPresent((scheduleItem) -> {
      Event eventResult = (Event) scheduleItem;

      event.setName(eventResult.getName());
      event.setDescription(eventResult.getDescription());
      event.setDayOfWeek(eventResult.getDayOfWeek());
      event.setDuration(eventResult.getDuration());
      event.setStartTime(eventResult.getStartTime());
    });
  }

  /**
   * Constructs a dialogue for task creation and passes the task back using a Consumer.
   *
   * @param taskConsumer Consumer to callback with created task.
   */
  public void createTask(Consumer<Task> taskConsumer) {
    TaskCreationView dialog = new TaskCreationView();

    Optional<ScheduleItem> result = dialog.showAndWait();

    result.ifPresent(scheduleItem -> {
      Task taskResult = (Task) scheduleItem;

      taskConsumer.accept(taskResult);
    });
  }

  /**
   * Constructs a dialogue for event creation and passes the event back using a Consumer.
   *
   * @param eventConsumer Consumer to callback with created event.
   */
  public void createEvent(Consumer<Event> eventConsumer) {
    EventCreationView dialog = new EventCreationView();

    Optional<ScheduleItem> result = dialog.showAndWait();

    result.ifPresent(scheduleItem -> {
      Event eventResult = (Event) scheduleItem;

      eventConsumer.accept(eventResult);
    });
  }
}

