import java.util.ArrayList;

/** Represents an object of Route */
public abstract class Route {

  /** A list of all of the Routes */
  protected static ArrayList<Route> Routes = new ArrayList<Route>();
  /** The name of this route */
  protected int routeNumber;
  private static int totalRoutes = 0;

  public Route() {
    routeNumber = totalRoutes;
    totalRoutes++;
  }

  /** @return An arrayList of all RouteNames */
  public static ArrayList<Route> getRoutes() {
    return Routes;
  }

  /** @return An ArrayList containing the names of all the stations in the route in order */
  public abstract ArrayList<String> getStations();

  public abstract Station findStation(int stationID);

  public int getRouteNumber(){
    return routeNumber;
  }
}
