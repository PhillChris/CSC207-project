package transit.system;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;

public interface Statistics<T> {

  /**
   * Generates statistics for each day in the past week (the last 7 days)
   *
   * @return a HashMap of the statistics on each day of the past week
   */
  HashMap<LocalDate, T> generateWeeklyValues();

  /**
   * Generates statistics for each month in the past year (the last 7 days)
   *
   * @return a HashMap of the statistics on each day of the past week
   */
  HashMap<YearMonth, T> generateMonthlyValues();

  /**
   * Create default values for the HashMap returned by the generateWeeklyMessage method. This makes
   * the interface work easily when a week has not yet passed in the application.
   *
   * @return a HashMap containing default (dummy) values.
   */
  HashMap<LocalDate, T> defaultWeeklyValues();

  /**
   * Create default values for the HashMap returned by the generateMonthlyMessage method. This makes
   * the interface work easily when a year has not yet passed in the application.
   *
   * @return a HashMap containing default (dummy) values.
   */
  HashMap<YearMonth, T> defaultMonthlyValues();
}
