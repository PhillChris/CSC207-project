package transit.system;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserRevenue extends Statistics<Double> implements Serializable {

  @Override
  public HashMap<LocalDate, Double> generateWeeklyValues() {
    return dailyLogs;
  }

  @Override
  public HashMap<YearMonth, Double> generateMonthlyValues() {
    return monthlyLogs;
  }

  @Override
  public void clearLogs() {}

  @Override
  public void update(Double data) {
    LocalDate date = TransitTime.getCurrentDate();
    YearMonth month = YearMonth.of(date.getYear(), date.getMonth());
    // Update user's personal monthly expenditure History
    if (monthlyLogs.containsKey(month)) {
      monthlyLogs.put(month, monthlyLogs.get(month) + data);
    } else {
      monthlyLogs.put(month, data);
    }
  }

  /** @return A String detailing average expenditure per month of this transit.system.User. */
  String avgMonthlyMessage() {
    String message = "Cost per month for user: ";
    List<YearMonth> months = new ArrayList<>(this.monthlyLogs.keySet());
    for (YearMonth month : months) {
      message +=
          month.toString()
              + " : "
              + "$"
              + String.format(
                  "%.2f", monthlyLogs.get(month) / (month.lengthOfMonth() * 100.0));
      message += System.lineSeparator();
    }
    return message.trim();
  }
}
