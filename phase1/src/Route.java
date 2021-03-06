import java.util.ArrayList;
import java.util.List;

/** Represents a travel route in this transit system */
public class Route {

  /** A list of all routes in the transit system */
  private static ArrayList<Route> routes = new ArrayList<>();
  /** List containing all the stations of this route in travel order */
  private List<Station> routeStations = new ArrayList<>();

  /**
   * Constructs a new route
   *
   * @param stationNames List of all station names in this route in order
   * @param fact The factory used to construct the stations
   */
  public Route(List<String> stationNames, StationFactory fact) {

    for (String s : stationNames) {
      Station station;
      station = fact.newStation(s);
      // Create a new station if a station of the same name and type does not exist
      if (!station.getStationsCopy().containsKey(s)) {
        station.addStation(station);
      }
      // station of the same type and same name already exists
      else {
        station = station.getStationsCopy().get(s);
      }
      routeStations.add(station);
    }
    routes.add(this);
  }

  /** @return A copy of the arrayList of all RouteNames */
  static ArrayList<Route> getRoutesCopy() {
    return new ArrayList<>(routes);
  }

  /**
   * @return A copy of the list of the stations in this route (an external class shouldn't access
   *     the routes directly)
   */
  List<Station> getRouteStationsCopy() {
    return new ArrayList<>(routeStations);
  }
}
