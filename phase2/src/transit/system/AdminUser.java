package transit.system;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/** Represents an transit.system.AdminUser in a transit system. */
public class AdminUser extends User {

  /**
   * Construct a new instance of transit.system.AdminUser
   *
   * @param name the name of this transit.system.AdminUser
   * @param email the email of this transit.system.AdminUser
   * @param password the password of ths transit.system.AdminUser
   */
  public AdminUser(String name, String email, String password) throws EmailInUseException {
    super(name, email, password);
  }

  /** Requests the production of a daily report to dailyReports.txt */
  void dailyReports() throws IOException {
    String message = calculator.generateReportMessage();
    Writer writer =
        new BufferedWriter(
            new OutputStreamWriter(new FileOutputStream("dailyReports.txt"), "utf-8"));
    writer.write("Date         Revenue   Stations Travelled");
    writer.write(System.lineSeparator());
    writer.write(message);
    writer.close();
  }
}
