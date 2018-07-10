import java.time.LocalDateTime;
import java.util.List;

public class UserParser {
  /**
   * Gets a user's average monthly expenditures for the Transit System
   *
   * @param userInfo Information given from the user
   */
  static void monthlyExpenditure(List<String> userInfo) {
    try {
      CardHolder user = CardHolder.findUser(userInfo.get(0));
      String averageMonthly = String.format("&.2f", user.getAvgMonthly());
      TransitReadWrite.write("User " + user.getEmail() +"'s average monthly expenditures: " + averageMonthly);
    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }

  /**
   * Adds a new user to the Transit System
   *
   * @param userInfo Info given from the user
   */
  static void addUser(List<String> userInfo) {
    try {
      LocalDateTime time = TransitTime.getTime(userInfo.get(0));
    } catch (TransitException a) {
      a.getMessage();
    }

    if (userInfo.get(1).equals("yes")) {
      AdminUser admin = new AdminUser(userInfo.get(2), userInfo.get(3));
    } else {
      CardHolder user = new CardHolder(userInfo.get(2), userInfo.get(3));
    }
  }

  static void changeName(List<String> userInfo) {
    try {
      CardHolder user = CardHolder.findUser(userInfo.get(0));
      String newName = userInfo.get(1);
      user.changeName(newName);
    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }

  static void dailyReports(List<String> userInfo) {
    try {
      AdminUser user = AdminUser.findAdminUser(userInfo.get(0));
      user.dailyReports();
      TransitReadWrite.write("Published daily reports to dailyReports.txt");
    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }
}
