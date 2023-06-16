package cs3500.pa05.model;

/**
 * Interface for a Task object which extends the ScheduleItem interface
 */
public interface TaskInterface extends ScheduleItem {
  /**
   * Indicates if task has been marked as complete or not.
   *
   * @return boolean representing the completion of the task.
   */
  boolean isComplete();

  /**
   * Sets the completion status of the task.
   *
   * @param isCompleted Boolean representing if the task has been completed or not.
   */
  void setCompletion(boolean isCompleted);
}
