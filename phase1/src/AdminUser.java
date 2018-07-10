import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
/** Represents an AdminUser in a transit system. */
public class AdminUser extends CardHolder {

  private static List<Trip> allTrips;
  private static HashMap<String, AdminUser> allAdminUsers = new HashMap<>();

  /**
   * Construct a new instance of AdminUser
   *
   * @param name the name of this AdminUser
   * @param email the email of this AdminUser
   */
  public AdminUser(String name, String email) throws EmailInUseException{
    super(name, email);
    allAdminUsers.put(email, this);
  }

  public static AdminUser findAdminUser(String email) throws UserNotFoundException{
    if (allAdminUsers.containsKey(email)) {
      return allAdminUsers.get(email);
    }
    else{
      throw new UserNotFoundException();
    }
  }

  public void dailyReports() {
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
        writer.write(System.lineSeparator());
        String date = day.toString();
        double total = dailyTotals.get(day) / 100.0;
        String revenue = String.format("%.2f", total);
        writer.write(String.format("%s", date + " $" + revenue));
      }
      writer.close();

    } catch (IOException e) {
      System.out.println("File could not be constructed. Daily reports will not be generated.");
    }
  }
}
