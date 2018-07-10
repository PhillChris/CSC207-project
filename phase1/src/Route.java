import java.util.ArrayList;
import java.util.List;

/** Represents an object of Route */
public class Route<T extends Station> {

  /** A list of all of the Routes */
  protected static ArrayList<Route> Routes = new ArrayList<Route>();

  private static int totalRoutes = 0;
  /** The name of this route */
  protected int routeNumber;
  /** List containing all the stations of this route in order */
  private List<Station> routeStations = new ArrayList<>();

  /**
   * Constructs a new route
   *
   * @param stationNames List of all station names in this route in order
   * @param fact The factory used to construct the stations
   */
  public Route(List<String> stationNames, StationFactory fact) {
    routeNumber = totalRoutes;
    totalRoutes++;

    for (String s : stationNames) {
      Station station;
      station = fact.newStation(s);
      // Create a new station if a station of the same name and type does not exist
      if (!station.getStations().containsKey(s)) {
        Station.addStation(station);
      }
      // station already exists
      else {
        station = station.getStations().get(s);
      }
      Routes.add(this);
      getRouteStations().add(station);
    }
  }

  /** @return An arrayList of all RouteNames */
  public static ArrayList<Route> getRoutes() {
    return Routes;
  }

  /** @return List of the stations in this route */
  public List<Station> getRouteStations() {
    return routeStations;
  }

  /**
   * Find a station in this route with given ID
   *
   * @param stationID the station ID used for search
   * @return The station with the same stationID
   */
  public Station findStation(int stationID) {
    for (Station station : getRouteStations()) {
      if (station.stationID == stationID) {
        return station;
      }
    }
    return null;
  }

  /** @return This route's route number */
  public int getRouteNumber() {
    return routeNumber;
  }
}
