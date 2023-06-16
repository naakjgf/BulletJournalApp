package cs3500.pa05.model;

import cs3500.pa05.enums.DayOfWeek;

/**
 * Represents an item on a weekly schedule.
 */
public interface ScheduleItem {
  /**
   * Returns name of Schedule Item.
   *
   * @return name of schedule item.
   */
  String getName();

  /**
   * Returns description of ScheduleItem.
   *
   * @return description String
   */
  String getDescription();

  /**
   * Returns the day of the week this ScheduleItem is assigned to.
   *
   * @return DayOfWeek
   */
  DayOfWeek getDayOfWeek();

  /**
   * Sets the description to a specified string.
   *
   * @param description String to set description to.
   */
  void setDescription(String description);

  /**
   * Sets the day of the week this ScheduleItem is assigned to.
   *
   * @param day DayOfWeek
   */
  void setDayOfWeek(DayOfWeek day);

}
