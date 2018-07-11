import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

/** Parses all methods pertaining to users in the transit system */
public class UserParser extends ObjectParser {

  /**
   * Constructs a new instance of UserParser.
   *
   * @param writer The file writer being used to write the results of this program to output.txt
   */
  UserParser(BufferedWriter writer) {
    super(writer);
    buildUserHashMap();
  }

  /**
   * Processes an add user request.
   *
   * @param userInfo Information given for the user from TransitReader.run
   */
  void add(List<String> userInfo) {
    try {
      this.checkInput(userInfo, 3);
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
   * Processes a remove user request.
   *
   * @param userInfo Information given for the user from TransitReader.run
   */
  void remove(List<String> userInfo) {
    try {
      this.checkInput(userInfo, 1);
      User user = findUser(userInfo.get(0));
      user.removeUser();
      write("Removed user " + user);
    } catch (TransitException a) {
      a.getMessage();
    }
  }

  /**
   * Generates a report containing all of a given user's cards and balances.
   *
   * @param userInfo Information given for the user from TransitReader.run
   */
  void report(List<String> userInfo) {
    try {
      this.checkInput(userInfo, 1);
      User user = findUser(userInfo.get(0));
      String message = "Username: " + user + System.lineSeparator();
      for (int i = 0; i < user.getCardsCopy().size(); i++) {
        message += user.getCardsCopy().get(i);
        if (i < user.getCardsCopy().size() - 1) {
          message += System.lineSeparator();
        }
      }
      write(message);
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  /**
   * Processes a change name request for a given user.
   *
   * @param userInfo Information given for the user from TransitReader.run
   */
  void changeName(List<String> userInfo) {
    try {
      this.checkInput(userInfo, 2);
      User user = findUser(userInfo.get(0));
      String newName = userInfo.get(1);
      user.changeName(newName);
      write("Changed user name to " + user);
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  /**
   * Processes a daily report request.
   *
   * @param userInfo Information given for the admin user from TransitReader.run
   */
  void dailyReports(List<String> userInfo) {
    try {
      this.checkInput(userInfo, 1);
      AdminUser user = findAdminUser(userInfo.get(0));
      user.dailyReports();
      write("Published daily reports to dailyReports.txt");
    } catch (TransitException a) {
      write(a.getMessage());
    } catch (IOException b) {
      write("File not found: This file was not located");
    }
  }

  /**
   * Generates a user's monthly expenditure profile
   *
   * @param userInfo Information given for the user from TransitReader.run
   */
  void monthlyExpenditure(List<String> userInfo) {
    try {
      this.checkInput(userInfo, 1);
      User user = findUser(userInfo.get(0));
      write(user.getAvgMonthly());
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  /**
   * Processes a request for the last three trips traveled by a given user
   *
   * @param userInfo Information given for the user from TransitReader.run
   */
  void getLastThree(List<String> userInfo) {
    try {
      User user = findUser(userInfo.get(0));
      String message =
          "These are the last three trips for user " + user + ": " + System.lineSeparator();
      for (int i = 0; i < user.getLastThreeCopy().size(); i++) {
        message += user.getLastThreeCopy().get(i).toString();
        if (i < user.getLastThreeCopy().size() - 1) {
          message += System.lineSeparator();
        }
      }
      write(message);
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  /** A helper method to add user-specific methods to the command hash map */
  private void buildUserHashMap() {
    keyWords.put(
        "ADDUSER",
        (userInfo) -> {
          this.add(userInfo);
          return null;
        });
    keyWords.put(
        "REMOVEUSER",
        (userInfo) -> {
          this.remove(userInfo);
          return null;
        });
    keyWords.put(
        "USERREPORT",
        (userInfo) -> {
          this.report(userInfo);
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
        "MONTHLYEXPENDITURE",
        (userInfo) -> {
          this.monthlyExpenditure(userInfo);
          return null;
        });
    keyWords.put(
        "GETLASTTHREE",
        (userInfo) -> {
          this.getLastThree(userInfo);
          return null;
        });
  }
}
