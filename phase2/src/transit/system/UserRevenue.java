package transit.system;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
  public String generateWeeklyValues() {
    StringBuilder message = new StringBuilder();
    LocalDate date = TransitTime.getCurrentDate();
    // Loop through the last seven days
    while (!date.equals(date.minusDays(7))) {
      message.append("Expenditure for " + date.toString() + ": ");
      double expenditure = caldulateDayCost(date);
      message.append(expenditure + System.lineSeparator());
    }
    return new String(message);
  }

  @Override
  String generateMonthlyValues() {
      StringBuilder message = new StringBuilder();
      LocalDate date = TransitTime.getCurrentDate();
      YearMonth month = TransitTime.getCurrentMonth();
      // Loop through the past year
      while (!month.equals(month.minusMonths(12))){

      }
    return null;
  }

    /**
     * A private helper to calculate the cost of the system for a given month
     * @param month
     * @return
     */
  private double calculateMonthlyCost(YearMonth month){
      double sum = 0.0;
      LocalDate date = month.atEndOfMonth();
      while (date!= month.minusMonths(1).atEndOfMonth()){
          Double value = caldulateDayCost(date);
          date = date.minusDays(1);
      }
      return sum;

  }

  private double caldulateDayCost(LocalDate date){
      double sum = 0.0;
      ArrayList<Double> values = dailyLogs.get(date);
      if (values!=null){
          for (double value: values){
              sum += value;
          }
      }
      return sum;
  }
}
