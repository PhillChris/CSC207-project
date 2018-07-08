import java.util.ArrayList;

public class BusRoute extends Route {

  /** An ArrayList of all the BusRoutes in the simulation */
  private static ArrayList<BusRoute> busRoutes;

  /**
   * An ArrayList of all the routeStations in this BusRoute
   */

  public BusRoute(ArrayList<String> stationNames) {
    super();

    // Add routeStations to this Route
    for (String s : stationNames) {
      Station station = new BusStation(s, this);
      getStations().add(station);
    }
    // Add itself to the ArrayLists of routeStations
    busRoutes.add(this);
    Routes.add(this);
  }

  /**
   * Check if the list of stationNames forms a valid SubwayRoute
   *
   * @param stationNames The list of station names
   * @return
   */
  public static boolean checkRoute(ArrayList<String> stationNames) {
    return false;
  }

  public ArrayList<String> getStationNames() {
      ArrayList<String> stationNames = new ArrayList<>();
    for (Station s : getStations()) {
          stationNames.add(s.name);
      }
      return stationNames;
  }
}
