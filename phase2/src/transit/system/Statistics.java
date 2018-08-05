package transit.system;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;

/** A class for recording analytical information associated to another object */
public class Statistics implements Serializable {
  public static final int STORAGELIMIT = 365;
  /** Records revenue for the entire System */
  private static Statistics SystemRevenue = setSystemRevenue();
  /** Records the total number of stations travelled by users for the System */
  private static Statistics SystemTripLength = setSystemTripLength();

  /** Log of the daily values stored by this statistic */
  HashMap<LocalDate, Integer> dailyLogs;

  /** Creates a new instance of Statistics */
  public Statistics() {
    dailyLogs = new HashMap<>();
    dailyLogs.put(TransitTime.getClock().getCurrentDate(), 0);
  }

  /** @return The statistics associated with system revenue */
  public static Statistics getSystemRevenue() {
    return SystemRevenue;
  }

  /** @return The statistics associated with stations travelled accross the system */
  public static Statistics getSystemTripLength() {
    return SystemTripLength;
  }

  private static Statistics setSystemRevenue() {
    Statistics revenue = (Statistics) Database.readObject(Database.REVENUE_LOCATION);
    if (revenue != null) {
      return revenue;
    }
    return new Statistics();
  }

  private static Statistics setSystemTripLength() {
    Statistics tripLength = (Statistics) Database.readObject(Database.TRIPLOG_LOCATION);
    if (tripLength != null) {
      return tripLength;
    }
    return new Statistics();
  }

  /**
   * Generates a string representation of statistics for each day in the past week (the last 7 days)
   *
   * @return a HashMap of the statistics on each day of the past week
   */
  public HashMap<LocalDate, Integer> generateWeeklyValues() {
    HashMap<LocalDate, Integer> expenditures = new HashMap<>();
    LocalDate date = TransitTime.getClock().getCurrentDate();
    // Loop through the last seven days
    LocalDate endDate = date.minusDays(7);
    while (!date.equals(endDate)) {
      if (dailyLogs.get(date) == null) {
        expenditures.put(date, 0);
      } else {
        expenditures.put(date, dailyLogs.get(date));
      }
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
  public HashMap<YearMonth, Integer> generateMonthlyValues() {
    HashMap<YearMonth, Integer> expenditures = new HashMap<>();
    YearMonth month = TransitTime.getClock().getCurrentMonth();
    // Loop through the past year
    YearMonth endMonth = month.minusMonths(12);
    while (!month.equals(endMonth)) {
      int cost = calculateMonthlyCost(month);
      expenditures.put(month, cost);
      month = month.minusMonths(1);
    }
    return expenditures;
  }

  /** Updates the information stored by this statistic */
  public void update(Integer data) {
    LocalDate date = TransitTime.getClock().getCurrentDate();
    // Update user's personal monthly expenditure History
    if (!dailyLogs.containsKey(date)) {
      dailyLogs.put(date, data);
    } else {
      dailyLogs.put(date, dailyLogs.get(date) + data);
    }
    refreshLogs();
  }

  private int calculateMonthlyCost(YearMonth month) {
    int sum = 0;
    LocalDate date = month.atEndOfMonth();
    while (!date.equals(month.minusMonths(1).atEndOfMonth())) {
      Integer value;
      if (dailyLogs.get(date) == null) {
        value = 0;
      } else {
        value = dailyLogs.get(date);
      }
      sum += value;
      date = date.minusDays(1);
    }
    return sum;
  }

  /** Throws out data stored beyond the total capacity of days stored */
  private void refreshLogs() {
    for (LocalDate date : dailyLogs.keySet()) {
      if (date.isBefore(TransitTime.getClock().getCurrentDate().minusDays(STORAGELIMIT))) {
        dailyLogs.remove(date);
      }
    }
  }
}
