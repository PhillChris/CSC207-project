package transit.system;

import java.time.LocalDate;

/** Represents a row in the UserTablePage table */
public class UserRow extends Row {
  /** Creates a new instance of a UserRow */
  public UserRow(User user, LocalDate date) {
    this.name = user.getUserName();
    this.tapsIn = user.getTapsIn(date);
    this.tapsOut = user.getTapsOut(date);
  }}
