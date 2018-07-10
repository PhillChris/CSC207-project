import java.time.LocalDateTime;
import java.util.List;

public class UserParser {
  /**
   * Gets a user's average monthly expenditures for the Transit System
   *
   * @param userInfo Information given from the user
   */
  void monthlyExpenditure(List<String> userInfo) {
    try {
      User user = User.findUser(userInfo.get(0));
      TransitReadWrite.write(user.getAvgMonthly());
    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }

  /**
   * Adds a new user to the Transit System
   *
   * @param userInfo Info given from the user
   */
  void addUser(List<String> userInfo) {
    try {
      LocalDateTime time = TransitTime.getTime(userInfo.get(0));
      if (userInfo.get(1).equals("yes")) {
        AdminUser admin = new AdminUser(userInfo.get(2), userInfo.get(3));
      } else {
        User user = new User(userInfo.get(2), userInfo.get(3));
      }
    } catch (TransitException a) {
      a.getMessage();
    }
  }

  void changeName(List<String> userInfo) {
    try {
      User user = User.findUser(userInfo.get(0));
      String newName = userInfo.get(1);
      user.changeName(newName);
    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }

  void dailyReports(List<String> userInfo) {
    try {
      AdminUser user = AdminUser.findAdminUser(userInfo.get(0));
      user.dailyReports();
      TransitReadWrite.write("Published daily reports to dailyReports.txt");
    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }
}
