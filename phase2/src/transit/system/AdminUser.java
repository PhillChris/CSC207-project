package transit.system;

/** Represents an transit.system.AdminUser in a transit system. */
public class AdminUser extends User {

  /**
   * Construct a new instance of transit.system.AdminUser
   *
   * @param name the name of this transit.system.AdminUser
   * @param email the email of this transit.system.AdminUser
   * @param password the password of ths transit.system.AdminUser
   */
  public AdminUser(String name, String email, String password) throws InvalidEmailException, EmailInUseException {
    super(name, email, password);
  }

  /**
   * Creates a daily report message
   *
   * @return the daily report message
   */
  public String dailyReports() {
    String message = "Date         Revenue   Stations Travelled\n";
    message += calculator.generateReportMessage();
    return message;
  }
}
