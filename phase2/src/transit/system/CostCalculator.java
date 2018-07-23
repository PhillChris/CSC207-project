package transit.system;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/** A class for making money calculations and generating daily reports. */
public class CostCalculator {

  private static HashMap<YearMonth, Integer> monthlyRevenue = new HashMap<>();
  /** Contains the expenditure per each day */
  private static HashMap<LocalDate, Integer> dailyRevenue = new HashMap<>();
  /** Contains the number of stations travelled per day by users */
  private static HashMap<LocalDate, Integer> dailyLog = new HashMap<>();

  /** Updates the expenditure and revenue in the entire system */
  void updateSystemRevenue(int fee, int tripLength) {
    LocalDate date = TransitTime.getCurrentDate();
    YearMonth month = YearMonth.of(date.getYear(), date.getMonth());

    if (dailyRevenue.containsKey(date)) {
      dailyRevenue.put(date, dailyRevenue.get(date) + fee);
      dailyLog.put(date, dailyLog.get(date) + tripLength);

    } else {
      dailyRevenue.put(date, fee);
      dailyLog.put(date, tripLength);
    }

    if (monthlyRevenue.containsKey(month)) {
      monthlyRevenue.put(month, monthlyRevenue.get(month) + fee);
    } else {
      monthlyRevenue.put(month, fee);
    }
  }

  /** @return The daily report table for all days passed during this simulation. */
  String generateReportMessage() {
    // Loop through all days and write each day's revenue
    // The revenue collected for each day
    HashMap<LocalDate, Integer> dailyTotals = dailyRevenue;
    // List of all the dates collected
    List<LocalDate> dates = new ArrayList<LocalDate>(dailyTotals.keySet());
    dates.sort(Collections.reverseOrder());
    String message = System.lineSeparator(); // The message to be outputted to Daily Reports
    for (LocalDate day : dates) {
      String date = day.toString();
      double revenue = dailyRevenue.get(day) / 100.0;
      int travelled = dailyLog.get(day);
      message +=
          String.format("%s   $%.2f     %s%s", date, revenue, travelled, System.lineSeparator());
    }
    return message;
  }

  public static HashMap<YearMonth, Integer> getMonthlyRevenue() {
    return monthlyRevenue;
  }
}
