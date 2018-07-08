import java.util.ArrayList;

public class SubwayRoute extends Route {

  /** An ArrayList of all the SubwayRoutes in the simulation */
  private static ArrayList<SubwayRoute> subwayRoutes;
  /** An ArrayList of all the stations in this SubwayRoute */

  /** @param stationNames The names of the stations in this SubwayRoute */
  public SubwayRoute(ArrayList<String> stationNames) {
    super();
    // Add stations to this Route
    for (String s : stationNames) {
      Station station = new SubwayStation(s);
      getStations().add(station);
      Station.addStation(station);
    }
    // Add itself to the ArrayLists of stations
    subwayRoutes.add(this);
    Routes.add(this);
  }
}
