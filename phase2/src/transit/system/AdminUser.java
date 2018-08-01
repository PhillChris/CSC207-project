package transit.system;

import java.io.Serializable;

/** Represents an transit.system.AdminUser in a transit system. */
public class AdminUser extends User implements Serializable {

  /**
   * Construct a new instance of transit.system.AdminUser
   *
   * @param name the name of this transit.system.AdminUser
   * @param email the email of this transit.system.AdminUser
   * @param password the password of ths transit.system.AdminUser
   */
  public AdminUser(String name, String email, String password) throws MessageTransitException {
    super(name, email, password);
  }
}
