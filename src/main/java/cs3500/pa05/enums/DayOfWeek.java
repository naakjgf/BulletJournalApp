package cs3500.pa05.enums;

/**
 * Represents a day of the week.
 */
public enum DayOfWeek {
  /**
   * Monday, first day of the week.
   */
  MONDAY(0),

  /**
   * Tuesday, second day of the week
   */
  TUESDAY(1),

  /**
   * Wednesday, third day of the week.
   */
  WEDNESDAY(2),

  /**
   * Thursday, fourth day of the week.
   */
  THURSDAY(3),

  /**
   * Friday, fifth day of the week.
   */
  FRIDAY(4),

  /**
   * Saturday, sixth day of the week.
   */
  SATURDAY(5),

  /**
   * Sunday, seventh and last day of the week.
   */
  SUNDAY(6);

  private int numVal;
  DayOfWeek(int numVal)
  {
    this.numVal = numVal;
  }

  public int getNumVal()
  {
    return this.numVal;
  }
}
