import java.util.ArrayList;
import java.util.List;

public class SubwayRoute extends Route {

  /** An ArrayList of all the SubwayRoutes in the simulation */
  private static ArrayList<SubwayRoute> subwayRoutes;
  /** An ArrayList of all the stations in this SubwayRoute */

  /** @param stationNames The names of the stations in this SubwayRoute */
  public SubwayRoute(ArrayList<String> stationNames) {
    super();
    // Add stations to this Route
    for (String s : stationNames) {
      Station station = new SubwayStation(s, this);
      getStations().add(station);
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
  public List<String> getStationNames() {
    ArrayList<String> stationNames = new ArrayList<>();
    for (Station s : getStations()) {
      stationNames.add(s.name);
    }
    return stationNames;
  }

  public Station findStation(int stationId) {
    for (Station station : getStations()) {
      if (station.stationID == stationId) {
        return station;
      }
    }
    return null;
  }
}
