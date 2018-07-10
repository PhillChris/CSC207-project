import java.util.ArrayList;
import java.util.List;

/** Represents a travel route in this transit system */
public class Route {

  /** A list of all routes in the transit system */
  protected static ArrayList<Route> routes = new ArrayList<Route>();
  /** A static counter for the number of totalRoutes in the simulation, used to assign route ids */
  private static int totalRoutes = 0;
  /** The numerical id of this route */
  protected int routeID;
  /** List containing all the stations of this route in travel order */
  private List<Station> routeStations = new ArrayList<>();

  /**
   * Constructs a new route
   *
   * @param stationNames List of all station names in this route in order
   * @param fact The factory used to construct the stations
   */
  public Route(List<String> stationNames, StationFactory fact) {
    routeID = totalRoutes;
    totalRoutes++;

    for (String s : stationNames) {
      Station station;
      station = fact.newStation(s);
      // Create a new station if a station of the same name and type does not exist
      if (!station.getStations().containsKey(s)) {
        Station.addStation(station);
      }
      // station of the same type and same name already exists
      else {
        station = station.getStations().get(s);
      }
      getRouteStations().add(station);
    }
    routes.add(this);
  }

  /** @return An arrayList of all RouteNames */
  public static ArrayList<Route> getRoutes() {
    return routes;
  }

  /** @return A list of the stations in this route */
  public List<Station> getRouteStations() {
    return routeStations;
  }

  /**
   * Find a station in this route with given ID
   *
   * @param stationID the station ID to be searched for
   * @return The station with the given stationID
   */
  public Station findStation(int stationID) {
    for (Station station : getRouteStations()) {
      if (station.stationID == stationID) {
        return station;
      }
    }
    return null;
  }

  /** @return This route's routeID */
  public int getRouteId() {
    return routeID;
  }
}
