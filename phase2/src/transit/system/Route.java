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
  private static HashMap<String, ArrayList<Route>> routes = setRoutes();
  /**
   * HashMap containing HashMap of all stations of given types as values, where the keys of the
   * inner HashMap are the station names.
   */
  private static HashMap<String, HashMap<String, Station>> allStations = setAllStations();
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

  public static HashMap<String, HashMap<String, Station>> getAllStationsCopy() {
    return allStations;
  }

  static HashMap<String, HashMap<String, Station>> setAllStations() {
    HashMap<String, HashMap<String, Station>> allStations = new HashMap<>();
    for (String type : Station.POSSIBLE_TYPES) {
      allStations.put(type, new HashMap<>());
      for (Route route : routes.get(type)) {
        for (Station station : route.getRouteStationsCopy()) {
          if (!allStations.get(type).containsKey(station.toString())) {
            allStations.get(type).put(station.toString(), station);
          }
        }
      }
    }
    return allStations;
  }

  /**
   * Set the static routes attribute to the passed parameter. Note: this method should only be used
   * for deserialization of Route objects when the program is first being loaded in.
   *
   */
  private static HashMap<String, ArrayList<Route>> setRoutes() {
    HashMap<String, ArrayList<Route>> storedRoutes =
        (HashMap<String, ArrayList<Route>>) Database.readObject(Database.ROUTE_LOCATION);
    if (storedRoutes != null) {
      return storedRoutes;
    }
    else{
      HashMap<String, ArrayList<Route>> routes = new HashMap<>();
      for (String type: Station.POSSIBLE_TYPES){
        routes.put(type, new ArrayList<>());
      }
      return routes;
    }
  }

  /** @return A shallow copy of the arrayList of all RouteNames */
  public static HashMap<String, ArrayList<Route>> getRoutesCopy() {
    HashMap<String, ArrayList<Route>> copy = new HashMap<>();
    for (String type: routes.keySet()){
      copy.put(type, new ArrayList<>(routes.get(type)));
    }
    return copy;
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
      Station newStation = checkStationExists(name, routeType);
      stations.add(newStation);
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
    LogWriter.getLogWriter().logInfoMessage(Route.class.getName(), "saveRoute", "Route sucessfully saved to transit system");
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

  /**
   * Check if a station of given name and type has already been created. If it has not, it creates
   * the station of given name and type and adds it to the allStations HashMap.
   *
   * @param name the name of the station.
   * @param type the type of the station.
   * @return a Station with name name and type type.
   */
  private Station checkStationExists(String name, String type) {
    if (allStations.get(type).containsKey(name)) {
      return allStations.get(type).get(name);
    } else {
      Station newStation = new Station(name, type);
      allStations.get(type).put(name, newStation);
      return newStation;
    }
  }
}
