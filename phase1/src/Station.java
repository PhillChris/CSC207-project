import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Station {
  private static int totalStations;
  private static HashMap<String, List<Station>> allStations = new HashMap<>();
  final int stationID;
  protected String name;
  protected int initialFee;
  protected int perStationFee;
  protected List<Station> associatedStations;

  public Station(String name) {
    this.name = name;
    stationID = totalStations;
    totalStations++;
    this.associatedStations = null;
  }

  public static HashMap<String, List<Station>> getAllStations() {
    return allStations;
  }

  /**
   * Adds the newStation to allStations. Then associates all stations of the same name
   * with newStation.
   *
   * @param newStation the station to be added to allStations.
   */
  public static void addStation(Station newStation) {
    String stationName = newStation.name;
    // if no station of this name, add it to allStations
    if (!allStations.containsKey(stationName)) {
      allStations.put(stationName, new ArrayList<>());
      allStations.get(stationName).add(newStation);
    }
    // associate all stations of matching name
    for (Station station : allStations.get(stationName)) {
      station.associate(newStation);
    }
  }

  /** @return the name of this station */
  public String getName() {
    return this.name;
  }

  /**
   * Called at the beginning of a ride between stations
   *
   * @return the constant fare for this type of station
   */
  public int getInitialFee() {
    return this.initialFee;
  }

  /**
   * @return the associated station to this station (i.e. the bus station in this subway station or
   *     the subway station connected to this bus station)
   */
  public boolean isAssociatedStation(Station station) {
    return this.associatedStations.contains(station);
  }

  /**
   * Associates a given station with this one.
   *
   * @param associatedStation the station to be associated with this one.
   */
  public void associate(Station associatedStation) {
    this.associatedStations.add(associatedStation);
  }

  /**
   * Called at the end of a ride.
   * NOTE: we can move this to subclasses, and use more specific Route
   * lists to get better runtime. This seems like it sacrifices extensibility though
   *
   * @param initialStation the station at which this ride started (i.e. when last changing modes of
   *     transportation or vehicle lines).
   * @return the per-station fare for this ride
   */
  public int getFinalFee(Station initialStation) {
    if (perStationFee > 0) { // this saves useless searching
      for (Route route : Route.getRoutes()) {
        int distance = distanceInRoute(this, initialStation, route.getStations());
        if (distance > -1) { // return the first valid trip containing these stations.
          return perStationFee * distance;
        }
      }
      return Trip.MAXFEE; // no valid trips with given station so charge them MAXFEE.
    }
    return 0;
  }

  /**
   * @param station1 the first station in the Trip.
   * @param station2 the second station in the Trip.
   * @param routeStations the route we are querying for stations.
   * @return distance between the two stations in the given route. -1 if stations aren't both
   *     contained in the given route.
   */
  private int distanceInRoute(Station station1, Station station2, List<Station> routeStations) {
    Integer firstStation = null;
    Integer secondStation = null;
    for (int i = 0; i <= routeStations.size(); i++) {
      Station station = routeStations.get(i);
      if (station1.equals(station)) {
        firstStation = i;
      }
      if (station2.equals(station)) {
        secondStation = i;
      }
    }
    // check that both stations are in the route
    if (firstStation != null && secondStation != null) {
      return Math.abs(firstStation - secondStation) * perStationFee;
    } else {
      return -1;
    }
  }
}
