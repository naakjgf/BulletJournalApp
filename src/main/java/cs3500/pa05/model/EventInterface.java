package cs3500.pa05.model;

/**
 * Interface for an Event object which extends the ScheduleItem interface.
 */
public interface EventInterface extends ScheduleItem {
  /**
   * Retrieves the start time for the given event object.
   *
   * @return Start time epoch.
   */
  long getStartTime();

  /**
   * Retrieves the duration for the given event object.
   *
   * @return Duration in milliseconds.
   */
  long getDuration();

  /**
   * Sets the start time for the given event object.
   *
   * @param startTimeEpoch Starting time to set event to in epoch ms.
   */
  void setStartTime(long startTimeEpoch);

  /**
   * Sets the duration for the given event object.
   *
   * @param durationMs the duration to set the event object to in milliseconds.
   */
  void setDuration(long durationMs);
}
