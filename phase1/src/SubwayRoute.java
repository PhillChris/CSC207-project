import java.util.ArrayList;

public class SubwayRoute extends Route {

  /** An ArrayList of all the SubwayRoutes in the simulation */
  private static ArrayList<SubwayRoute> subwayRoutes;
  /** An ArrayList of all the stations in this SubwayRoute */
  private ArrayList<SubwayStation> stations;

  /**
   * @param stationNames The names of the stations in this SubwayRoute
   */
  public SubwayRoute(ArrayList<String> stationNames) {
    super();
    ArrayList<SubwayStation> stations = new ArrayList<>();

    // Add stations to this Route
    for (String s : stationNames) {
      SubwayStation station = new SubwayStation(s, this);
      stations.add(station);
    }
    // Add itself to the ArrayLists of stations
    subwayRoutes.add(this);
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

  /** @return The names of all the stations in this SubwayRoute */
  public ArrayList<String> getStations() {
    ArrayList<String> stationNames = new ArrayList<>();
    for (Station s : stations) {
    stationNames.add(s.name);
  }
    return stationNames;
}
}
