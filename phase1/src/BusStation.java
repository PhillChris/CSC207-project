import java.util.HashMap;

public class BusStation extends Station {
  /** A HashMap linking BusStation names to BusStations */
  public static HashMap<String, Station> busStations = new HashMap<>();
  /** The initial fee charged by starting a trip at a BusStation */
  private int BUS_INITIAL_FEE = 200;
  /** The fee charged per BusStation travelled */
  private int BUS_PERSTATION_FEE = 0;

  /**
   * Constructs a new instance of BusStation.
   *
   * @param name the name of this BusStation
   */
  public BusStation(String name) {
    super(name);
    this.initialFee = BUS_INITIAL_FEE;
    this.perStationFee = BUS_PERSTATION_FEE;
  }

  /**
   * Adds this station to the HashMap busStations
   *
   * @param station The station to be added
   */
  public static void newBusStation(Station station) {
    busStations.put(station.getName(), station);
  }

  /**
   * Get all the BusStations from a static context
   *
   * @return The HashMap of BusStations
   */
  public static HashMap<String, Station> getBusStations() {
    return busStations;
  }

  /**
   * Get all the BusStations from a non static context
   *
   * @return The HashMap of BusStations
   */
  public HashMap<String, Station> getStations() {
    return busStations;
  }
}
