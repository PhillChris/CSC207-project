package transit.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/** Represents a travel route in this transit system */
public class Route implements Serializable {

  /** The total number of routes in this station */
  private static int numRoutes = 0;
  /** A list of all routes in the transit system */
  private static HashMap<String, ArrayList<Route>> routes = new HashMap<>();
  /** List containing all the stations of this route in travel order */
  private List<Station> routeStations;
  /** The type of this route */
  private String routeType;
  /** The number of this route. -1 for routes that have not been officially added to this system */
  private int routeNum;
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
      this.routeNum = -1;
    }
  }

  /** @return A copy of the arrayList of all RouteNames */
  public static HashMap<String, ArrayList<Route>> getRoutesCopy() {
    return new HashMap<>(routes);
  }

  /**
   * Adds a new station to this route
   *
   * @param stationType The type of this station
   * @param stationName The name of this new station
   * @throws TransitException Thrown if the type of this station does not match the type of this
   *     route
   */
  public void addStation(String stationType, String stationName) throws TransitException {
    if (!stationType.equals(routeType)) {
      throw new TransitException();
    } else {
      routeStations.add(new Station(stationName, stationType));
    }
  }

  /** Gives this route an official route number and saves this route to the system */
  public void saveRoute() {
    this.routeNum = numRoutes++;
    routes.get(this.routeType).add(this);
  }

  /**
   * @return A copy of the list of the stations in this route (an external class shouldn't access
   *     the routes directly)
   */
  public List<Station> getRouteStationsCopy() {
    return new ArrayList<>(routeStations);
  }
}
