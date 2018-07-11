import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

/** Parses all methods pertaining to users in the transit system */
public class UserParser extends ObjectParser {
  public UserParser(BufferedWriter writer) {
    super(writer);
    buildUserHashMap();
  }
  /**
   * Generates a user's monthly expenditure profile
   *
   * @param userInfo Information given for the user from TransitReader.read
   */
  public void monthlyExpenditure(List<String> userInfo) {
    try {
      ObjectParser.checkInput(userInfo, 1);
      User user = User.findUser(userInfo.get(0));
      write(user.getAvgMonthly());
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  /**
   * Processes an add user request
   *
   * @param userInfo Information given for the user from TransitReader.read
   */
  public void add(List<String> userInfo) {
    try {
      ObjectParser.checkInput(userInfo, 3);
      if (userInfo.get(0).equals("yes")) {
        AdminUser admin = new AdminUser(userInfo.get(1), userInfo.get(2));
        write("Added admin user " + admin);
      } else {
        User user = new User(userInfo.get(1), userInfo.get(2));
        write("Added user " + user);
      }
    } catch (TransitException a) {
      a.getMessage();
    }
  }

  /**
   * Processes a remove user request
   *
   * @param userInfo Information given for the user from TransitReader.read
   */
  public void remove(List<String> userInfo) {
    try {
      ObjectParser.checkInput(userInfo, 1);
      // if the given user is not an AdminUser but is a User
      User user = User.findUser(userInfo.get(0));
      user.removeUser();
      write("Removed user " + user);

    } catch (TransitException a) {
      a.getMessage();
    }
  }

  /**
   * Processes a change name request for a given user
   *
   * @param userInfo Information given for the user from TransitReader.read
   */
  public void changeName(List<String> userInfo) {
    try {
      ObjectParser.checkInput(userInfo, 2);
      User user = User.findUser(userInfo.get(0));
      String newName = userInfo.get(1);
      user.changeName(newName);
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  /**
   * Processes a daily report request
   *
   * @param userInfo Information given for the admin user from TransitReader.read
   */
  public void dailyReports(List<String> userInfo) {
    try {
      ObjectParser.checkInput(userInfo, 1);
      AdminUser user = AdminUser.findAdminUser(userInfo.get(0));
      user.dailyReports();
      write("Published daily reports to dailyReports.txt");
    } catch (TransitException a) {
      write(a.getMessage());
    } catch (IOException b) {
      write("File not found: This file was not located");
    }
  }

  /**
   * Generates a report containing all of a given user's cards and balances
   *
   * @param userInfo Information given for the user from TransitReader.read
   */
  public void report(List<String> userInfo) {
    try {
      ObjectParser.checkInput(userInfo, 1);
      User user = User.findUser(userInfo.get(0));
      String message = "Username: " + user + System.lineSeparator();
      for (int i = 0; i < user.getCards().size(); i++) {
        message += user.getCards().get(i);
        if (i < user.getCards().size() - 1) {
          message += System.lineSeparator();
        }
      }
      write(message);
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  /**
   * Processes a request for the last three trips traveled by a given user
   *
   * @param userInfo Information given for the user from TransitReader.read
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
      write(message);
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  private void buildUserHashMap() {
    keyWords.put(
        "ADDUSER",
        (userInfo) -> {
          this.add(userInfo);
          return null;
        });
    keyWords.put(
        "MONTHLYEXPENDITURE",
        (userInfo) -> {
          this.monthlyExpenditure(userInfo);
          return null;
        });
    keyWords.put(
        "CHANGENAME",
        (userInfo) -> {
          this.changeName(userInfo);
          return null;
        });
    keyWords.put(
        "DAILYREPORTS",
        (userInfo) -> {
          this.dailyReports(userInfo);
          return null;
        });
    keyWords.put(
        "GETLASTTHREE",
        (userInfo) -> {
          this.getLastThree(userInfo);
          return null;
        });
    keyWords.put(
        "USERREPORT",
        (userInfo) -> {
          this.report(userInfo);
          return null;
        });
    keyWords.put(
        "REMOVEUSER",
        (userInfo) -> {
          this.remove(userInfo);
          return null;
        });
  }
}
