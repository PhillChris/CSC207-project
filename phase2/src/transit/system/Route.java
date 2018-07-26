package transit.system;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/** Represents a travel route in this transit system */
public class Route {

  /** A list of all routes in the transit system */
  private static HashMap<String, ArrayList<Route>> routes = new HashMap<>();
  /** List containing all the stations of this route in travel order */
  private List<Station> routeStations = new ArrayList<>();
  /**
   * Constructs a new route
   *
   * @param stations List of all station names in this route in order.
   * @param type The type of stations this route contains
   */
  public Route(List<Station> stations, String type) throws TransitException {
    // Checks if the given type is a valid type
    if (!Arrays.asList(Station.POSSIBLE_TYPES).contains(type)) {
      throw new TransitException();
    } else {
      // TODO: we may want to add a check that the list of stations is not a duplicate
      for (Station s : stations) {
        routeStations.add(s);
      }

      // Adds the given route to the appropriate place in the hash map
      if (routes.get(type) != null) {
        routes.get(type).add(this);
      } else {
        ArrayList<Route> newStations = new ArrayList<>();
        newStations.add(this);
        routes.put(type, newStations);
      }
    }
  }

  /** @return A copy of the arrayList of all RouteNames */
  public static HashMap<String, ArrayList<Route>> getRoutesCopy() {
    return new HashMap<>(routes);
  }

  /**
   * @return A copy of the list of the stations in this route (an external class shouldn't access
   *     the routes directly)
   */
  public List<Station> getRouteStationsCopy() {
    return new ArrayList<>(routeStations);
  }
}
