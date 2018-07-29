package transit.system;

import java.io.Serializable;
import java.util.ArrayList;
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
   * @param type The type of stations this route contains
   */
  public Route(String type) {
    this.routeType = type;
    this.routeStations = new ArrayList<>();
    this.routeNum = -1;
  }

  /** @return A copy of the arrayList of all RouteNames */
  public static HashMap<String, ArrayList<Route>> getRoutesCopy() {
    return new HashMap<>(routes);
  }

  /** @return The type of this route */
  public String getRouteType() {
    return routeType;
  }

  /** @return A string representation of this route */
  public String toString() {
    String s = "Route Number: " + this.routeNum;
    s += System.lineSeparator();
    for (Station station : this.routeStations) {
      s += station.toString() + " < - > ";
    }
    return s;
  }

  /**
   * Adds a new station to this route at the start of the route
   *
   * @param stationName The name of this new station
   * @throws TransitException Thrown if the type of this station does not match the type of this
   *     route
   */
  public void addStationAtStart(String stationName) {
    routeStations.add(0, new Station(stationName, this.routeType));
  }

  /**
   * Adds a new station to this route at the end of the route
   *
   * @param stationName The name of this new station
   * @throws TransitException Thrown if the type of this station does not match the type of this
   *     route
   */
  public void addStationAtEnd(String stationName) {
    routeStations.add(new Station(stationName, this.routeType));
  }

  /** Gives this route an official route number and saves this route to the system */
  public void saveRoute() {
    this.routeNum = numRoutes++;
    if (!routes.containsKey(this.routeType)) {
      ArrayList<Route> typeList = new ArrayList<>();
      typeList.add(this);
      routes.put(routeType, typeList);
    } else {
      routes.get(this.routeType).add(this);
    }
  }

  /**
   * @return A copy of the list of the stations in this route (an external class shouldn't access
   *     the routes directly)
   */
  public List<Station> getRouteStationsCopy() {
    return new ArrayList<>(routeStations);
  }
}
