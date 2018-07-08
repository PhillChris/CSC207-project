import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Station {
  private static int totalStations;
  private static HashMap<String, List<Station>> allStations = new HashMap<>();
  final int stationID;
  protected String name;
  protected Route route;
  protected int initialFee;
  protected int perStationFee;
  protected List<Station> associatedStations;

  public Station(String name, Route route) {
    this.name = name;
    this.route = route;
    stationID = totalStations;
    totalStations++;
    this.associatedStations = null;
  }

  public static HashMap<String, List<Station>> getAllStations() {
    return allStations;
  }

  /** @return the name of this station */
  public String getName() {
    return this.name;
  }

  /** @return the name of this station's route */
  public Route getRoute() {
    return this.route;
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
   *
   * @param initialStation the station at which this ride started (i.e. when last changing modes of
   *     transportation or vehicle lines).
   * @return the per-station fare for this ride
   */
  public int getFinalFee(Station initialStation) {
    return this.perStationFee
            * Math.abs(route.getStations().indexOf(this) - route.getStations().indexOf(initialStation));
  }

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
}
