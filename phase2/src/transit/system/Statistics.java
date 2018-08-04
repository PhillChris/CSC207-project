package transit.system;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Statistics {
  /** Log of the daily values stored by this statistic */
  HashMap<LocalDate, Double> dailyLogs;

  /**
   * Generates a string representation of statistics for each day in the past week (the last 7 days)
   *
   * @return a HashMap of the statistics on each day of the past week
   */
  HashMap<LocalDate, Double> generateWeeklyValues(){
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
   * Generates a string representation of statistics for each month in the past year (the last 7 days)
   *
   * @return a HashMap of the statistics on each day of the past week
   */
  HashMap<YearMonth, Double> generateMonthlyValues(){
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

  /** Throws out old statistics */
  void clearLogs() {}

  /** Updates the information stored by this statistic */
  void update(Double data) {
    LocalDate date = TransitTime.getCurrentDate();
    // Update user's personal monthly expenditure History
    if (!dailyLogs.containsKey(date)) {
      dailyLogs.put(date, data);
    }
    else{
      dailyLogs.put(date, dailyLogs.get(date) + data);
    }
  }

  protected double calculateMonthlyCost(YearMonth month) {
    double sum = 0.0;
    LocalDate date = month.atEndOfMonth();
    while (date != month.minusMonths(1).atEndOfMonth()) {
      Double value = dailyLogs.get(date);
      date = date.minusDays(1);
    }
    return sum;
  }
}
