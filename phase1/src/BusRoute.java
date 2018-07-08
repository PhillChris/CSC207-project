import java.util.ArrayList;

public class BusRoute extends Route {

  /** An ArrayList of all the BusRoutes in the simulation */
  private static ArrayList<BusRoute> busRoutes = new ArrayList<>();

  public BusRoute(ArrayList<String> stationNames) {
    super();

    // Add routeStations to this Route
    for (String s : stationNames) {
      BusStation station;
      if (!BusStation.getStations().containsKey(s)) { // there are no stations with the same name.
        station = new BusStation(s);
        Station.addStation(station); // associate stations
        BusStation.addStation(station);
      } else {
        station = BusStation.getStations().get(s);
        }
      getRouteStations().add(station);
    }
    // Add itself to the ArrayLists of routeStations
    busRoutes.add(this);
    Routes.add(this);
  }
}
