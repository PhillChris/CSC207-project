import java.util.HashMap;

public class BusStation extends Station {
  public static HashMap<String, Station> busStations = new HashMap<>();
  private int BUS_INITIAL_FEE = 200;
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

  public static void newBusStation(Station station) {
    busStations.put(station.getName(), station);
  }

  public static HashMap<String, Station> getBusStations() {
    return busStations;
  }

  public HashMap<String, Station> getStations() {
    return busStations;
  }
}
