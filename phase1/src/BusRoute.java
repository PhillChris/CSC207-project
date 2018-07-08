import java.util.ArrayList;

public class BusRoute extends Route {

  /** An ArrayList of all the BusRoutes in the simulation */
  private static ArrayList<BusRoute> busRoutes;

  public BusRoute(ArrayList<String> stationNames) {
    super();

    // Add routeStations to this Route
    for (String s : stationNames) {
      Station station = new BusStation(s);
      getStations().add(station);
      Station.addStation(station);
    }
    // Add itself to the ArrayLists of routeStations
    busRoutes.add(this);
    Routes.add(this);
  }
}
