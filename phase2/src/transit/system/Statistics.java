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
   * Throws out old statistics
   */
  void clearLogs();
}
