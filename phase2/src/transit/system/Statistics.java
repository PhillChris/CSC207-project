package transit.system;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;

/** A class for recording analytical information associated to another object */
public class Statistics {
  /** The maximum number of days for information to be stored in the system */
  public static final int STORAGELIMIT = 365;
  /** Log of the daily values stored by this statistic */
  HashMap<LocalDate, Double> dailyLogs;

  /** Creates a new instance of Statistics */
  public Statistics() {
    dailyLogs = new HashMap<>();
  }

  /**
   * Generates a string representation of statistics for each day in the past week (the last 7 days)
   *
   * @return a HashMap of the statistics on each day of the past week
   */
  public HashMap<LocalDate, Double> generateWeeklyValues() {
    HashMap<LocalDate, Double> expenditures = new HashMap<>();
    LocalDate date = TransitTime.getCurrentDate();
    // Loop through the last seven days
    while (!date.equals(date.minusDays(7))) {
      expenditures.put(date, dailyLogs.get(date));
      date = date.minusDays(1);
    }
    return expenditures;
  }

  /**
   * Generates a string representation of statistics for each month in the past year (the last 7
   * days)
   *
   * @return a HashMap of the statistics on each day of the past week
   */
  public HashMap<YearMonth, Double> generateMonthlyValues() {
    HashMap<YearMonth, Double> expenditures = new HashMap<>();
    YearMonth month = TransitTime.getCurrentMonth();
    // Loop through the past year
    while (!month.equals(month.minusMonths(12))) {
      double cost = calculateMonthlyCost(month);
      expenditures.put(month, cost);
      month = month.minusMonths(1);
    }
    return expenditures;
  }

  /** Updates the information stored by this statistic */
  void update(Double data) {
    LocalDate date = TransitTime.getCurrentDate();
    // Update user's personal monthly expenditure History
    if (!dailyLogs.containsKey(date)) {
      dailyLogs.put(date, data);
    } else {
      dailyLogs.put(date, dailyLogs.get(date) + data);
    }
    refreshLogs();
  }

  private double calculateMonthlyCost(YearMonth month) {
    double sum = 0.0;
    LocalDate date = month.atEndOfMonth();
    while (date != month.minusMonths(1).atEndOfMonth()) {
      Double value = dailyLogs.get(date);
      date = date.minusDays(1);
    }
    return sum;
  }

  /** Throws out data stored beyond the total capacity of days stored */
  private void refreshLogs() {
    if (dailyLogs.keySet().size() > STORAGELIMIT) {
      LocalDate date = TransitTime.getCurrentDate().minusDays(STORAGELIMIT);
      while (dailyLogs.keySet().size() > STORAGELIMIT) {
        dailyLogs.remove(date);
        date = date.minusDays(1);
      }
    }
  }
}
