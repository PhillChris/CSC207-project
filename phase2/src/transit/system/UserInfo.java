package transit.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Stores information related to a given user */
public class UserInfo implements Serializable {
  /** Records the last three trips associated to this log */
  private ArrayList<Trip> tripLog;
  /** The name of the user associated with this log */
  private String name;
  /** This transit.system.User's password */
  private String password;
  /** Determines the permissions and pricing of this user */
  private String permission;

  public UserInfo(String name, String password, String permission) {
    this.name = name;
    this.password = password;
    this.permission = permission;
  }
  /**
   * @param password A given password
   * @return Return whether the given password matched the password on record
   */
  public boolean correctAuthentification(String password) {
    return this.password.equals(password);
  }

  /** @return The name associated with this user */
  public String getUserName() {
    return this.name;
  }

  /** @return The permission on this user */
  public String getPermission() {
    return this.permission;
  }

  /**
   * Change this transit.system.User's name.
   *
   * @param newName the new name of this transit.system.User.
   */
  public void changeName(String newName) {
    this.name = newName;
    LogWriter.getLogWriter()
        .logInfoMessage(UserInfo.class.getName(), "changeName", "Username changed to " + newName);
  }

  /**
   * @param currentPassword The current password of the user
   * @param newPassword The new password of the user
   * @throws IncorrectPasswordException
   * @throws InvalidPasswordException
   */
  public void changePassword(String currentPassword, String newPassword)
      throws IncorrectPasswordException, InvalidPasswordException {
    if (!currentPassword.equals(password)) {
      LogWriter.getLogWriter()
          .logWarningMessage(UserInfo.class.getName(), "changePassword", "Failed password change attempt: incorrect current password entered" );
      throw new IncorrectPasswordException();
    } else if (newPassword.length() < 6) {
      LogWriter.getLogWriter()
          .logWarningMessage(UserInfo.class.getName(), "changePassword", "Failed password change attempt: new password too long" );
      throw new InvalidPasswordException();
    } else {
      password = newPassword;
      LogWriter.getLogWriter()
          .logInfoMessage(UserInfo.class.getName(), "changePassword", "User " + this.getUserName() + " password changed" );
    }
  }
}
