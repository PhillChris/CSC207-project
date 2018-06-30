// TODO: Figure out how to do daily report stuff. Get clarification on "operating costs".

import java.util.List;

/** Represents an AdminUser in a transit system. */
public class AdminUser extends CardHolder {

  /**
   * Construct a new instance of AdminUser
   *
   * @param name the name of this AdminUser
   * @param email the email of this AdminUser
   */
  public AdminUser(String name, String email) {
    super(name, email);
  }

  /**
   * Add a new route to the transit system if there is not already a route with the same name.
   *
   * @param name the name of the new route
   * @param route the list of stations in the new route
   */
  public void addRoute(String name, List<Station> route) {
    if (!TransitSystem.getRoutes().containsKey(name)) {
      TransitSystem.getRoutes().put(name, route);
    }
  }
}