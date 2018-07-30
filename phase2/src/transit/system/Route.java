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
  /** The number of this route. */
  private int routeNum;
  /**
   * Constructs a new route
   *
   * @param type The type of stations this route contains
   */
  public Route(String type) {
    this.routeType = type;
    this.routeStations = new ArrayList<>();
    this.routeNum = numRoutes + 1;
  }

  /** @return A shallow copy of the arrayList of all RouteNames */
  public static HashMap<String, ArrayList<Route>> getRoutesCopy() {
    return new HashMap<>(routes);
  }

  /** @return A string representation of this route */
  public String toString() {
    String s = "Route Number: " + this.routeNum;
    s += System.lineSeparator();
    for (int i = 0; i < this.routeStations.size(); i++) {
      s += this.routeStations.get(i).toString();
      if (i < this.routeStations.size() - 1) {
        s += "< - >";
      }
    }
    return s;
  }

  /**
   * Sets the new station to this route at the start of the route
   *
   * @throws TransitException Thrown if the type of this station does not match the type of this
   *     route
   */
  public void setRouteStations(List<String> stationNames) {
    ArrayList<Station> stations = new ArrayList<>();
    for (String name : stationNames) {
      stations.add(new Station(name, routeType));
    }
    this.routeStations = stations;
  }

  /** Officially adds this route to the system */
  public void saveRoute() {
    numRoutes++;
    // If no route of the same type already exists
    if (!routes.containsKey(this.routeType)) {
      ArrayList<Route> typeList = new ArrayList<>();
      typeList.add(this);
      routes.put(routeType, typeList);
    }
    // If routes of the same type already exist
    else {
      if (!routes.get(routeType).contains(this)) {
        routes.get(this.routeType).add(this); // Only add this route to hashmap if not already there
      }
    }
  }

  /**
   * @return A copy of the list of the stations in this route (an external class shouldn't access
   *     the routes directly)
   */
  public List<Station> getRouteStationsCopy() {
    return new ArrayList<>(routeStations);
  }

  /** @return The number of this route */
  public int getRouteNum() {
    return this.routeNum;
  }
}
