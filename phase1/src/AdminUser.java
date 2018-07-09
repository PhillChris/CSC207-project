import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
/** Represents an AdminUser in a transit system. */
public class AdminUser extends CardHolder {

  private static List<Trip> allTrips;

  /**
   * Construct a new instance of AdminUser
   *
   * @param name the name of this AdminUser
   * @param email the email of this AdminUser
   */
  public AdminUser(String name, String email) {
    super(name, email);
  }

  public static void dailyReports() {
    // The revenue collected for each day
    HashMap<LocalDate, Integer> dailyTotals = CostCalculator.getDailyRevenue();
    // List of all the dates collected
    List<LocalDate> dates = new ArrayList<LocalDate>(dailyTotals.keySet());
    Collections.sort(dates);

    try {
      // Writes to dailyReports.txt
      Writer writer =
          new BufferedWriter(
              new OutputStreamWriter(new FileOutputStream("dailyReports.txt"), "utf-8"));
      writer.write("Date       Revenue");
      writer.write(System.lineSeparator());

      // Loop through all days and write each day's revenue
      for (LocalDate day : dates) {
        String date = day.toString();
        double total = dailyTotals.get(day) / 100;
        String revenue = String.format("%.2f", total);
        writer.write(String.format("%s", date + " $" + revenue));
      }

      writer.close();

    } catch (IOException e) {
      System.out.println("File could not be constructed. Daily reports will not be generated.");
    }
  }
}
