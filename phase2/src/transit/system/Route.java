package transit.system;

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
   * @param type The type of the station that you are constructing routes over
   */
  public Route(List<String> stationNames, String type) {

    for (String s : stationNames) {
      Station station;
      station = Station.getStationsCopy(type).get(s);
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
