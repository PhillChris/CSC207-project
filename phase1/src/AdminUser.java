import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
/** Represents an AdminUser in a transit system. */
public class AdminUser extends CardHolder {

  private static List<Trip> allTrip;

  /**
   * Construct a new instance of AdminUser
   *
   * @param name the name of this AdminUser
   * @param email the email of this AdminUser
   */
  public AdminUser(String name, String email) {
    super(name, email);
  }


  public static void dailyReports() throws IOException {
    HashMap<LocalDate, Integer> dailyTotals = CostCalculator.allUsersDailyTotals(getAllUsers());
    List<LocalDate> dates = new ArrayList<LocalDate>(dailyTotals.keySet());
    Collections.sort(dates);

    try (Writer writer =
        new BufferedWriter(
            new OutputStreamWriter(new FileOutputStream("dailyReports.txt"), "utf-8"))) {
      writer.write("Date       Revenue");
      writer.write(System.lineSeparator());
      for (LocalDate day : dates) {
        String date = day.toString();
        double total = dailyTotals.get(day) / 100;
        String revenue = String.format("&.2f", total);
        writer.write(String.format("%s %s", date, revenue));
      }
      ;
    }
  }
}
