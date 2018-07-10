import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
/** Represents an AdminUser in a transit system. */
public class AdminUser extends User {

  /**
   * Construct a new instance of AdminUser
   *
   * @param name the name of this AdminUser
   * @param email the email of this AdminUser
   */
  public AdminUser(String name, String email) throws EmailInUseException {
    super(name, email);
  }

  /**
   * @param email The email belong to a particular AdminUser
   * @return the AdminUser with the given email
   * @throws UserNotFoundException Thrown if no AdminUser has given email
   */
  public static AdminUser findAdminUser(String email) throws UserNotFoundException {
    User user = findUser(email);
    if (user instanceof AdminUser) {
      return (AdminUser) user;
    } else {
      throw new UserNotFoundException();
    }
  }

  /** Requests the production of a daily report to dailyReports.txt */
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
