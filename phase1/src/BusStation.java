import java.util.HashMap;

public class BusStation extends Station {
  private int BUS_INITIAL_FEE = 200;
  private int BUS_PERSTATION_FEE = 0;

  public static HashMap<String, BusStation> busStations = new HashMap<>();

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

  public static HashMap<String, BusStation> getStations() {
    return busStations;
  }

  public static void addStation(BusStation station) {
    busStations.put(station.getName(), station);
  }
}
