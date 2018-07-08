import java.io.*;
import java.time.LocalDateTime;
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
    HashMap<LocalDateTime, Integer> dailyTotals = CostCalculator.allUsersDailyTotals(CardHolder.getAllUsers());
    List<LocalDateTime> dates = new ArrayList<LocalDateTime>(dailyTotals.keySet());
    Collections.sort(dates);

    try {
      Writer writer =
        new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("dailyReports.txt"), "utf-8"));
      writer.write("Date       Revenue");
      writer.write(System.lineSeparator());
      for (LocalDateTime day : dates) {
        String date = day.toString();
        double total = dailyTotals.get(day) / 100;
        String revenue = String.format("&.2f", total);
        writer.write(String.format("%s %s", date, revenue));
      }
    } catch (IOException e) {
      System.out.println("File could not be constructed. Daily reports will not be generated.");
    }
  }
}
