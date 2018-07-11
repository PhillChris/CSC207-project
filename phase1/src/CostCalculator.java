import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CostCalculator {

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
  }

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
      message += String.format("%.2f", revenue) + "    " + travelled + System.lineSeparator();
    }
    return message;
  }
}
