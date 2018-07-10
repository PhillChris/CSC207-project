import java.util.HashMap;

public class BusStation extends Station {
  /** Maps the names of all BusStation to their corresponding BusStation object */
  public static HashMap<String, Station> busStations = new HashMap<>();
  /** The fee charged by starting a leg of a trip at a BusStation */
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
    this.setInitialFee(BUS_INITIAL_FEE);
    this.setPerStationFee(BUS_PERSTATION_FEE);
  }

  /**
   * Helper method to Station.addStation which adds a created BusStation to BusStation records
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
