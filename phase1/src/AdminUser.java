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
    try {
      // Writes to dailyReports.txt
      Writer writer =
          new BufferedWriter(
              new FileWriter("dailyReports.txt", true));
      writer.write("Date       Revenue");
      writer.write(System.lineSeparator());

      // Append the new day's revenue

      LocalDate date = TransitTime.currentTime.toLocalDate();
      double total = CostCalculator.DailyRevenue.get(date) / 100;
      String revenue = String.format("%.2f", total);
      writer.write(String.format("%s", date + " $" + revenue));

    } catch (IOException e) {
      System.out.println("File could not be constructed. Daily reports will not be generated.");
    }
  }
}
