import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;

public class CostCalculator {

  /** Contains the expenditure for each method */
  private static HashMap<YearMonth, Integer> MonthlyRevenue = new HashMap<>();

  /** Contains the expenditure per each day */
  private static HashMap<LocalDate, Integer> DailyRevenue = new HashMap<>();

  /** Updates the expenditure and revenue in the entire system */
  public static void updateSystemRevenue(int fee) {
    updateDaily(fee);
    updateMonthly(fee);
  }

  /** Updates the expenditure and revenue in the entire system */
  private static void updateDaily(int fee) {
    LocalDate date = TransitTime.getCurrentDate();
    YearMonth month = YearMonth.of(date.getYear(), date.getMonth());

    if (DailyRevenue.containsKey(date)) {
      DailyRevenue.put(date, DailyRevenue.get(date) + fee);
    } else {
      DailyRevenue.put(date, fee);
    }
  }

  private static void updateMonthly(int fee) {
    LocalDate date = TransitTime.getCurrentDate();
    YearMonth month = YearMonth.of(date.getYear(), date.getMonth());

    if (MonthlyRevenue.containsKey(month)) {
      MonthlyRevenue.put(month, MonthlyRevenue.get(month) + fee);
    } else {
      MonthlyRevenue.put(month, fee);
    }
  }

  /** @return A HashMap containing the expenditure per each day */
  public static HashMap<LocalDate, Integer> getDailyRevenue() { return DailyRevenue; }

  public static void generateReport(List<LocalDate> dates, HashMap<LocalDate, Integer> dailyTotals) throws IOException {
    // Writes to dailyReports.txt
    Writer writer =
        new BufferedWriter(
            new OutputStreamWriter(new FileOutputStream("dailyReports.txt"), "utf-8"));
    writer.write("Date       Revenue");
    writer.write(System.lineSeparator());

    // Loop through all days and write each day's revenue
    for (LocalDate day : dates) {
      writer.write(System.lineSeparator());
      String date = day.toString();
      double total = dailyTotals.get(day) / 100.0;
      String revenue = String.format("%.2f", total);
      writer.write(String.format("%s", date + " $" + revenue));
    }
    writer.close();
  }
}
