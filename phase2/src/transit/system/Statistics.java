package transit.system;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Statistics<T> {
  /** Log of the daily values stored by this statistic */
  HashMap<LocalDate, ArrayList<T>> dailyLogs;

  /**
   * Generates a string representation of statistics for each day in the past week (the last 7 days)
   *
   * @return a HashMap of the statistics on each day of the past week
   */
  abstract String generateWeeklyValues();

  /**
   * Generates a string representation of statistics for each month in the past year (the last 7 days)
   *
   * @return a HashMap of the statistics on each day of the past week
   */
  abstract String generateMonthlyValues();

  /** Throws out old statistics */
  void clearLogs() {}

  /** Updates the information stored by this statistic */
  void update(T data) {
    LocalDate date = TransitTime.getCurrentDate();
    // Update user's personal monthly expenditure History
    if (!dailyLogs.containsKey(date)) {
      dailyLogs.put(date, new ArrayList<T>());
      dailyLogs.get(date).add(data);
    }
    dailyLogs.get(date).add(data);
  }
}
