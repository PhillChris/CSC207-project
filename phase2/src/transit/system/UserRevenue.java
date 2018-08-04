package transit.system;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;

public class UserRevenue extends Statistics<Double> implements Serializable {

  /** Instantiates a new instance of UserRevenue */
  public UserRevenue() {
    dailyLogs = new HashMap<LocalDate, ArrayList<Double>>();
  }

  /** @return A String detailing average expenditure per month of this transit.system.User. */
  String avgMonthlyMessage() {
    String message = "Cost per month for user: ";
    return message.trim();
  }

  @Override
  public HashMap<LocalDate, Double> generateWeeklyValues() {
    HashMap<LocalDate, Double> expenditures = new HashMap<>();
    LocalDate date = TransitTime.getCurrentDate();
    // Loop through the last seven days
    while (!date.equals(date.minusDays(7))) {
      double cost = calculateDayCost(date);
      expenditures.put(date, cost);
      date = date.minusDays(1);
    }
    return expenditures;
  }

  @Override
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

  /**
   * A private helper to calculate the cost of the system for a given month
   *
   * @param month A given month in the calendar year
   * @return The total expenditure logged for the given month
   */
  private double calculateMonthlyCost(YearMonth month) {
    double sum = 0.0;
    LocalDate date = month.atEndOfMonth();
    while (date != month.minusMonths(1).atEndOfMonth()) {
      Double value = calculateDayCost(date);
      date = date.minusDays(1);
    }
    return sum;
  }

  private double calculateDayCost(LocalDate date) {
    double sum = 0.0;
    ArrayList<Double> values = dailyLogs.get(date);
    if (values != null) {
      for (double value : values) {
        sum += value;
      }
    }
    return sum;
  }
}
