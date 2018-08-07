package transit.system;

import java.io.Serializable;
import java.util.HashMap;

import static transit.system.Database.readObject;

/**
 * Represents a User in a transit system. Each User has access to commands that can used to execute
 * actions in the transit system.
 */
public class User implements Serializable {
  /**
   * Email format regex (from https://howtodoinjava.com/regex/java-regex-validate-email-address/)
   */
  private static final String EMAILREGEX =
      "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
  /**
   * HashMap linking each email to its User
   */
  private static HashMap<String, User> allUsers = setAllUsers();
  /** The transit.system.User's email */
  private final String email;
  /** Stores the personal information associated with this user */
  private UserInfo personalInfo;
  /** The card commands associated with this user */
  private UserCardCommands cardCommands;

  /**
   * Construct a new instance of transit.system.User
   *
   * @param name the name of this transit.system.User
   * @param email the email of this transit.system.User
   * @param password the password of ths transit.system.User
   */
  public User(String name, String email, String password, String permission)
      throws MessageTransitException {
    if (!email.matches(EMAILREGEX)) { // check for valid email format
      throw new InvalidEmailException();
    }
    if (allUsers.keySet().contains(email)) { // If this transit.system.User already exists
      throw new EmailInUseException();
    }
    if (password.length() < 6) {
      throw new InvalidPasswordException();
    }
    this.email = email;
    allUsers.put(email, this);
    personalInfo = new UserInfo(name, password);
    cardCommands = new UserCardCommands(permission);
  }

  /** @return The set of all Users in the start of the program launch */
  private static HashMap<String, User> setAllUsers() {
    HashMap<String, User> users = (HashMap<String, User>) readObject(Database.USERS_LOCATION);
    if (users != null) {
      return users;
    }
    return new HashMap<>();
  }

  /** @return a copy of the HashMap of all Users */
  public static HashMap<String, User> getAllUsersCopy() {
    HashMap<String, User> copy = new HashMap<>();
    for (String name : allUsers.keySet()) {
      copy.put(name, allUsers.get(name));
    }
    return copy;
  }

  /** @return The card commands associated with this User */
  public UserCardCommands getCardCommands() {
    return cardCommands;
  }

  public UserInfo getPersonalInfo() {
    return personalInfo;
  }

  /** Removes this user from the transit system. */
  public void removeUser() {
    allUsers.remove(this.email);
    LogWriter.getLogWriter().logRemoveUser(personalInfo.getUserName());
  }

  /** @return The string representation of this user. */
  public String toString() {
    return personalInfo.getUserName();
  }
}
