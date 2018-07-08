import java.util.ArrayList;
import java.util.List;

public class SubwayRoute extends Route {

  /** An ArrayList of all the SubwayRoutes in the simulation */
  private static ArrayList<SubwayRoute> subwayRoutes = new ArrayList<>();
  /** An ArrayList of all the stations in this SubwayRoute */

  /** @param stationNames The names of the stations in this SubwayRoute */
  public SubwayRoute(ArrayList<String> stationNames) {
    super();
    // Add stations to this Route
    for (String s : stationNames) {
      Station station = null; // stupid compiler error when not assigned null.
      if (!Station.getAllStations().containsKey(s)) { // there are no stations with the same name.
        station = new BusStation(s);
        Station.addStation(station); // associate stations.
      } else {
        List<Station> sameNameStations = Station.getAllStations().get(s);
        // check if a station of the same name and type already exists.
        for (Station sameNameStation : sameNameStations) {
          if (sameNameStation instanceof SubwayStation) {
            station = sameNameStation;
          }
        }

      }
      getStations().add(station);
    }
    // Add itself to the ArrayLists of stations
    subwayRoutes.add(this);
    Routes.add(this);
  }
}
