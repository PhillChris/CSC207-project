import java.util.ArrayList;
import java.util.List;

/** Represents an object of Route */
public abstract class Route {

  /** A list of all of the Routes */
  protected static ArrayList<Route> Routes = new ArrayList<Route>();

  private static int totalRoutes = 0;
  /** The name of this route */
  protected int routeNumber;

  private List<Station> routeStations = new ArrayList<>();

  public Route() {
    routeNumber = totalRoutes;
    totalRoutes++;
  }

  /** @return An arrayList of all RouteNames */
  public static ArrayList<Route> getRoutes() {
    return Routes;
  }

  public List<Station> getStations() {
    return routeStations;
  }

  public Station findStation(int stationID) {
    for (Station station : getStations()) {
      if (station.stationID == stationID) {
        return station;
      }
    }
    return null;
  }

  public int getRouteNumber() {
    return routeNumber;
  }
}
