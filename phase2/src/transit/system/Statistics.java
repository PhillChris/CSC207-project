package transit.system;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;

public abstract class Statistics<T> {
  /** Log of the daily values stored by this statistic */
  HashMap<LocalDate, T> dailyLogs;
  /** Log of the monthly values stored by this statistic */
  HashMap<YearMonth, T> monthlyLogs;

  /**
   * Generates statistics for each day in the past week (the last 7 days)
   *
   * @return a HashMap of the statistics on each day of the past week
   */
  HashMap<LocalDate, T> generateWeeklyValues() {
    return dailyLogs;
  }

  /**
   * Generates statistics for each month in the past year (the last 7 days)
   *
   * @return a HashMap of the statistics on each day of the past week
   */
  HashMap<YearMonth, T> generateMonthlyValues() {
    return monthlyLogs;
  }

  /** Throws out old statistics */
  void clearLogs() {}

  /** Updates the information stored by this statistic */
  abstract void update(T data);
}
