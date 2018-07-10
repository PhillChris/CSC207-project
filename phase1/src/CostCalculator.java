import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;

public class CostCalculator {

  /** Contains the expenditure per each day */
  private static HashMap<LocalDate, Integer> dailyRevenue = new HashMap<>();
  /** Contains the number of stations travelled per day by users */
  private static HashMap<LocalDate, Integer> dailyLog = new HashMap<>();

  /** Updates the expenditure and revenue in the entire system */
  public static void updateSystemRevenue(int fee, int tripLength) {
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

  /** @return A HashMap containing the expenditure per each day */
  public static HashMap<LocalDate, Integer> getDailyRevenue() {
    return dailyRevenue;
  }

  public static void generateReport(List<LocalDate> dates)
      throws IOException {
    // Writes to dailyReports.txt
    Writer writer =
        new BufferedWriter(
            new OutputStreamWriter(new FileOutputStream("dailyReports.txt"), "utf-8"));
    writer.write("Date       Revenue   Stations Travelled");
    writer.write(System.lineSeparator());

    // Loop through all days and write each day's revenue
    for (LocalDate day : dates) {
      writer.write(System.lineSeparator());
      String date = day.toString();
      double  revenue = dailyRevenue.get(day) / 100.0;
      int travelled=dailyLog.get(day);
      String message = String.format("%.2f", revenue) + "    " + travelled;
      writer.write(String.format("%s", date + " $" + message));
    }
    writer.close();
  }
}
