package transit.system;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserRevenue implements Statistics<Double>, Serializable {

  private HashMap<YearMonth, Double> expenditureMonthly = new HashMap<>();
  /** Calculates and sends the daily revenue recieved from this user to the system */
  private HashMap<LocalDate, Double> expenditureDaily = new HashMap<>();
  /** Calculates and sends the daily revenue recieved from this user to the system */
  @Override
  public HashMap<LocalDate, Double> generateWeeklyValues() {
    return expenditureDaily;
  }

  @Override
  public HashMap<YearMonth, Double> generateMonthlyValues() {
    return expenditureMonthly;
  }

  @Override
  public void clearLogs() {}

  @Override
  public void update(Double data) {
    LocalDate date = TransitTime.getCurrentDate();
    YearMonth month = YearMonth.of(date.getYear(), date.getMonth());
    // Update user's personal monthly expenditure History
    if (expenditureMonthly.containsKey(month)) {
      expenditureMonthly.put(month, expenditureMonthly.get(month) + data);
    } else {
      expenditureMonthly.put(month, data);
    }
  }

  /** @return A String detailing average expenditure per month of this transit.system.User. */
  String avgMonthlyMessage() {
    String message = "Cost per month for user: ";
    List<YearMonth> months = new ArrayList<>(this.expenditureMonthly.keySet());
    for (YearMonth month : months) {
      message +=
          month.toString()
              + " : "
              + "$"
              + String.format(
                  "%.2f", expenditureMonthly.get(month) / (month.lengthOfMonth() * 100.0));
      message += System.lineSeparator();
    }
    return message.trim();
  }
}
