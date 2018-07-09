import java.util.ArrayList;
import java.util.List;

public class SubwayRoute extends Route {

  /** An ArrayList of all the SubwayRoutes in the simulation */
  private static List<SubwayRoute> subwayRoutes = new ArrayList<>();

  /** @param stationNames The names of the stations in this SubwayRoute */
  public SubwayRoute(List<String> stationNames) {
    super();
    // Add stations to this Route
    for (String s : stationNames) {
      SubwayStation station;
      if (!SubwayStation.getStations().containsKey(s)) { // there are no stations with the same name.
        station = new SubwayStation(s);
        Station.addStation(station); // associate stations.
        SubwayStation.addStation(station);
      } else {
        station = SubwayStation.getStations().get(s);
      }
      getRouteStations().add(station);
    }
    // Add itself to the ArrayLists of stations
    subwayRoutes.add(this);
    Routes.add(this);
  }
}
