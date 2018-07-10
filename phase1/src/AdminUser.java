import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
/** Represents an AdminUser in a transit system. */
public class AdminUser extends User {

  private static HashMap<String, AdminUser> allAdminUsers = new HashMap<>();

  /**
   * Construct a new instance of AdminUser
   *
   * @param name the name of this AdminUser
   * @param email the email of this AdminUser
   */
  public AdminUser(String name, String email) throws EmailInUseException {
    super(name, email);
    allAdminUsers.put(email, this);
  }

  /** Removes this admin user from the system*/
  public void removeAdminUser() {
    allAdminUsers.remove(this.getEmail());
    this.removeUser();
  }

  /**
   * @param email The email belong to a particular AdminUser
   * @return the AdminUser with the given email
   * @throws UserNotFoundException Thrown if no AdminUser has given email
   */
  public static AdminUser findAdminUser(String email) throws UserNotFoundException {
    if (allAdminUsers.containsKey(email)) {
      return allAdminUsers.get(email);
    } else {
      throw new UserNotFoundException();
    }
  }

  /**
   * Writes the dailyReports to dailyReports.txt
   */
  public void dailyReports() {
    // The revenue collected for each day
    HashMap<LocalDate, Integer> dailyTotals = CostCalculator.getDailyRevenue();
    // List of all the dates collected
    List<LocalDate> dates = new ArrayList<LocalDate>(dailyTotals.keySet());
    Collections.sort(dates);

    try {
      CostCalculator.generateReport(dates, dailyTotals);
    } catch (IOException e) {
      System.out.println("File could not be constructed, daily reports will not be generated");
    }
  }
}
