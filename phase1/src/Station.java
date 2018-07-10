import java.util.HashMap;

public abstract class Station {
  /** Total number of stations in the transit system */
  private static int totalStations;
  /** The unique ID of this station */
  final int stationID;
  /** The fee charged per station travelled by this station */
  private int perStationFee;
  /**
   * The initial fee charged by this station at the start of a trip
   */
  private int initialFee;
  /** The name of this station */
  private String name;
  /**
   * Create a new instance of Station
   *
   * @param name The name of this station
   */
  public Station(String name) {
    this.name = name;
    stationID = totalStations;
    totalStations++;
  }

  /**
   * Adds the newStation to either the list of BusStations or SubwayStations
   *
   * @param newStation the station to be added to allStations.
   */
  public static void addStation(Station newStation) {
    if (newStation instanceof BusStation) {
      BusStation.newBusStation(newStation);
    } else if (newStation instanceof SubwayStation) {
      SubwayStation.newSubwayStation(newStation);
    }
  }

  public int getPerStationFee() {
    return perStationFee;
  }

  public void setPerStationFee(int perStationFee) {
    this.perStationFee = perStationFee;
  }

  /** @return the name of this station */
  public String getName() {
    return this.name;
  }

  /** @return the fare charged by this station at the start of a new trip */
  public int getInitialFee() {
    return this.initialFee;
  }

  public void setInitialFee(int initialFee) {
    this.initialFee = initialFee;
  }

  /** @return whether the two intersect (have common name) */
  public boolean isAssociatedStation(Station otherStation) {
    return this.name.equals(otherStation.getName());
  }

  /** @return HashMap of all stations of similar type */
  public abstract HashMap<String, Station> getStations();

  /** @return A string representation of a station */
  public String toString() {
    return this.name;
  }
}
