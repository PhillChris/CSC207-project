package transit.system;

import java.time.LocalDate;

/** Represents a row in the UserStatsPage table */
public class UserRow {
  /** The username of the user in this UserRow */
  private String username;
  /** The taps in of the user in this UserRow*/
  private int tapsIn;
  /** The taps out of the user in this UserRow*/
  private int tapsOut;

  /** Creates a new instance of a UserRow*/
  public UserRow(User user, LocalDate date) {
    this.username = user.getUserName();
    this.tapsIn = user.getTapsIn(date);
    this.tapsOut = user.getTapsOut(date);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getTapsIn() {
    return tapsIn;
  }

  public void setTapsIn(int tapsIn) {
    this.tapsIn = tapsIn;
  }

  public int getTapsOut() {
    return tapsOut;
  }

  public void setTapsOut(int tapsOut) {
    this.tapsOut = tapsOut;
  }
}
