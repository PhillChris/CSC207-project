import java.util.ArrayList;

/** Represents an object of Route */
public abstract class Route {

  /** A list of all of the Routes */
  protected static ArrayList<Route> Routes = new ArrayList<Route>();
  /** The name of this route */
  protected String routeName;

  public Route(String name) {
    name = routeName;
  }

  /** @return An arrayList of all RouteNames */
  public static ArrayList<Route> getRoutes() {
    return Routes;
  }

  /** @return An ArrayList containing the names of all the stations in the route in order */
  public abstract ArrayList<String> getStations();
}
