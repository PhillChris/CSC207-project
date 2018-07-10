import java.util.List;

/** Parses all methods pertaining to users in the transit system */
public class UserParser implements ObjectParser {
  /**
   * Generates a user's monthly expenditure profile
   *
   * @param userInfo Information given for the user from TransitReadWrite.read
   */
  public void monthlyExpenditure(List<String> userInfo) {
    try {
      User user = User.findUser(userInfo.get(0));
      TransitReadWrite.write(user.getAvgMonthly());
    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }

  /**
   * Processes an add user request
   *
   * @param userInfo Information given for the user from TransitReadWrite.read
   */
  public void add(List<String> userInfo) {
    try {
      if (userInfo.get(0).equals("yes")) {
        AdminUser admin = new AdminUser(userInfo.get(1), userInfo.get(2));
        TransitReadWrite.write("Added admin user " + admin);
      } else {
        User user = new User(userInfo.get(1), userInfo.get(2));
        TransitReadWrite.write("Added user " + user);
      }
    } catch (TransitException a) {
      a.getMessage();
    }
  }

  /**
   * Processes a remove user request
   *
   * @param userInfo Information given for the user from TransitReadWrite.read
   */
  public void remove(List<String> userInfo) {
    try {
      try {
        // if the given user is an AdminUser
        AdminUser admin = AdminUser.findAdminUser(userInfo.get(0));
        admin.removeAdminUser();
        TransitReadWrite.write("Removed admin user " + admin);
      } catch (UserNotFoundException a) {
        // if the given user is not an AdminUser but is a User
        User user = User.findUser(userInfo.get(0));
        user.removeUser();
        TransitReadWrite.write("Removed user " + user);
      }
    } catch (TransitException a) {
      a.getMessage();
    }
  }

  /**
   * Processes a change name request for a given user
   *
   * @param userInfo Information given for the user from TransitReadWrite.read
   */
  public void changeName(List<String> userInfo) {
    try {
      User user = User.findUser(userInfo.get(0));
      String newName = userInfo.get(1);
      user.changeName(newName);
    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }

  /**
   * Processes a daily report request
   *
   * @param userInfo Information given for the admin user from TransitReadWrite.read
   */
  public void dailyReports(List<String> userInfo) {
    try {
      AdminUser user = AdminUser.findAdminUser(userInfo.get(0));
      user.dailyReports();
      TransitReadWrite.write("Published daily reports to dailyReports.txt");
    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }

  /**
   * Generates a report containing all of a given user's cards and balances
   *
   * @param userInfo Information given for the user from TransitReadWrite.read
   */
  public void report(List<String> userInfo) {
    try {
      User user = User.findUser(userInfo.get(0));
      String message = "Username: " + user + System.lineSeparator();
      for (int i = 0; i < user.getCards().size(); i++) {
        message += user.getCards().get(i) + System.lineSeparator();
        if (i < user.getCards().size() - 1) {
          message += System.lineSeparator();
        }
      }
      TransitReadWrite.write(message);
    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }

  /**
   * Processes a request for the last three trips traveled by a given user
   *
   * @param userInfo Information given for the user from TransitReadWrite.read
   */
  public void getLastThree(List<String> userInfo) {
    try {
      User user = User.findUser(userInfo.get(0));
      String message =
          "These are the last three trips for user " + user + ": " + System.lineSeparator();
      for (int i = 0; i < user.getLastThree().size(); i++) {
        message += user.getLastThree().get(i).toString();
        if (i < user.getLastThree().size() - 1) {
          message += System.lineSeparator();
        }
      }
      TransitReadWrite.write(message);
    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }
}
