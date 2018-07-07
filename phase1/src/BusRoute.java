import java.util.ArrayList;

public class BusRoute extends Route {

  /** An ArrayList of all the BusRoutes in the simulation */
  private static ArrayList<BusRoute> busRoutes;
  /** An ArrayList of all the stations in this BusRoute */
  private ArrayList<BusStation> stations;

  public BusRoute(ArrayList<String> stationNames) {
    super();
    ArrayList<BusStation> stations = new ArrayList<>();

    // Add stations to this Route
    for (String s : stationNames) {
      BusStation station = new BusStation(s, this);
      stations.add(station);
    }
    // Add itself to the ArrayLists of stations
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

  public BusStation findStation(int stationID){
    for (BusStation station: stations){
      if (station.stationID == stationID){
        return station;
      }
    }
    return null;
  }

  @Override
  public ArrayList<String> getStations() {
      ArrayList<String> stationNames = new ArrayList<>();
      for (Station s : stations) {
          stationNames.add(s.name);
      }
      return stationNames;
  }
}
