package transit.system;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Statistics<T> {
  /** Log of the daily values stored by this statistic */
  HashMap<LocalDate, ArrayList<T>> dailyLogs;

  /**
   * Generates statistics for each day in the past week (the last 7 days)
   *
   * @return a HashMap of the statistics on each day of the past week
   */
  HashMap<LocalDate, T> generateWeeklyValues() {
    return new HashMap<>();
  }

  /**
   * Generates statistics for each month in the past year (the last 7 days)
   *
   * @return a HashMap of the statistics on each day of the past week
   */
  HashMap<YearMonth, T> generateMonthlyValues() {
    return new HashMap<>();
  }

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
