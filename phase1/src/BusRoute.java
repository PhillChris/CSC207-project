import java.util.ArrayList;
import java.util.List;

public class BusRoute extends Route {

  /** An ArrayList of all the BusRoutes in the simulation */
  private static ArrayList<BusRoute> busRoutes = new ArrayList<>();

  public BusRoute(ArrayList<String> stationNames) {
    super();

    // Add routeStations to this Route
    for (String s : stationNames) {
      Station station = null; // stupid compiler error when not assigned null.
      if (!Station.getAllStations().containsKey(s)) { // there are no stations with the same name.
        station = new BusStation(s);
        Station.addStation(station); // associate stations
      } else {
        List<Station> sameNameStations = Station.getAllStations().get(s);
        for (Station sameNameStation : sameNameStations) {
          // check if a station of the same name and type already exists.
          if (sameNameStation instanceof BusStation) {
            station = sameNameStation;
          }
        }

      }
      getStations().add(station);
    }
    // Add itself to the ArrayLists of routeStations
    busRoutes.add(this);
    Routes.add(this);
  }
}
