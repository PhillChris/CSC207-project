package transit.system;

import java.io.Serializable;

/** Stores information related to a given user */
public class UserInfo implements Serializable {
  /** The name of the user associated with this log */
  private String name;
  /** This transit.system.User's password */
  private String password;

  /**
   * Constructs a new instance of UserInfo
   *
   * @param name the username of this user
   * @param password the password of this user
   */
  public UserInfo(String name, String password) {
    this.name = name;
    this.password = password;
  }
  /**
   * @param password A given password
   * @return Return whether the given password matched the password on record
   */
  public boolean correctAuthentication(String password) {
    return this.password.equals(password);
  }

  /** @return The name associated with this user */
  public String getUserName() {
    return this.name;
  }

  /**
   * Change this transit.system.User's name.
   *
   * @param newName the new name of this transit.system.User.
   */
  public void changeName(String newName) {
    this.name = newName;
    LogWriter.getInstance()
        .logInfoMessage(UserInfo.class.getName(), "changeName", "Username changed to " + newName);
  }

  /**
   * @param currentPassword The current password of the user
   * @param newPassword The new password of the user
   * @throws IncorrectPasswordException if the current password doesn't match the stored password
   * @throws InvalidPasswordException if the new password is less than six characters
   */
  public void changePassword(String currentPassword, String newPassword)
      throws IncorrectPasswordException, InvalidPasswordException {
    if (!currentPassword.equals(password)) {
      // if the current password isn't as stored
      LogWriter.getInstance()
          .logWarningMessage(UserInfo.class.getName(), "changePassword", "Failed password change attempt: incorrect current password entered" );
      throw new IncorrectPasswordException();
    } else if (newPassword.length() < 6) {
      // if the new password isn't in a valid format (less than 6 characters)
      LogWriter.getInstance()
          .logWarningMessage(UserInfo.class.getName(), "changePassword", "Failed password change attempt: new password too long" );
      throw new InvalidPasswordException();
    } else {
      // the given information is valid: replace the given password
      password = newPassword;
      LogWriter.getInstance()
          .logInfoMessage(UserInfo.class.getName(), "changePassword", "User " + this.getUserName() + " password changed" );
    }
  }
}
