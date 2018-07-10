import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CostCalculator {

  /** Contains the expenditure per each day */
  private static HashMap<LocalDate, Integer> dailyRevenue = new HashMap<>();

  /** Updates the expenditure and revenue in the entire system */
  public static void updateSystemRevenue(int fee) {
    LocalDate date = TransitTime.getCurrentDate();
    YearMonth month = YearMonth.of(date.getYear(), date.getMonth());

    if (dailyRevenue.containsKey(date)) {
      dailyRevenue.put(date, dailyRevenue.get(date) + fee);
    } else {
      dailyRevenue.put(date, fee);
    }
  }

  /** @return A HashMap containing the expenditure per each day */
  public static HashMap<LocalDate, Integer> getDailyRevenue() {
    return dailyRevenue;
  }

  public static void generateReport()
      throws IOException {
    // Writes to dailyReports.txt
    Writer writer =
        new BufferedWriter(
            new OutputStreamWriter(new FileOutputStream("dailyReports.txt"), "utf-8"));
    writer.write("Date       Revenue");
    writer.write(System.lineSeparator());

    // Loop through all days and write each day's revenue
// The revenue collected for each day
    HashMap<LocalDate, Integer> dailyTotals = CostCalculator.getDailyRevenue();
    // List of all the dates collected
    List<LocalDate> dates = new ArrayList<LocalDate>(dailyTotals.keySet());
    dates.sort(Collections.reverseOrder());

    for (LocalDate day : dates) {
      writer.write(System.lineSeparator());
      String date = day.toString();
      double total = dailyRevenue.get(day) / 100.0;
      String revenue = String.format("%.2f", total);
      writer.write(String.format("%s", date + " $" + revenue));
    }
    writer.close();
  }
}
