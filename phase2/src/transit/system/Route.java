package transit.system;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/** Represents a travel route in this transit system */
public class Route implements Serializable {

  /** A list of all routes in the transit system */
  private static HashMap<String, ArrayList<Route>> routes = new HashMap<>();
  /** List containing all the stations of this route in travel order */
  private List<Station> routeStations;
  /**
   * The type of this route
   */
  private String routeType;
  /**
   * Constructs a new route
   *
   * @param stations List of all station names in this route in order.
   * @param type The type of stations this route contains
   */
  public Route(String type) throws TransitException {
    // Checks if the given type is a valid type
    if (!Arrays.asList(Station.POSSIBLE_TYPES).contains(type)) {
      throw new TransitException();
    } else {
      this.routeType = type;
      this.routeStations = new ArrayList<>();
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
