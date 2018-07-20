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
  }

  /** Processes an add user request. */
  void add(String username, String email, String password, boolean isAdmin)
      throws EmailInUseException {
    try {
      if (isAdmin) {
        AdminUser admin = new AdminUser(username, email, password);
        write("Added admin user " + admin);
      } else {
        User user = new User(username, email, password);
        write("Added user " + user);
      }
    } catch (EmailInUseException a) {
      throw new EmailInUseException();
    }
  }

  /**
   * Processes a remove user request.
   *
   * @param user The user to be removed
   */
  void remove(User user) {
    user.removeUser();
    write("Removed user " + user);
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
      String message = "Username: " + user;
      for (int i = 0; i < user.getCardsCopy().size(); i++) {
        message += System.lineSeparator();
        message += user.getCardsCopy().get(i);
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
}
