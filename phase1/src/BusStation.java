import java.util.HashMap;

public class BusStation extends Station {

  public static HashMap<String, BusStation> busStations = new HashMap<>();

  /**
   * Constructs a new instance of BusStation.
   *
   * @param name the name of this BusStation
   */
  public BusStation(String name) {
    super(name);
    this.initialFee = 200;
    this.perStationFee = 0;
  }

  public static HashMap<String, BusStation> getStations() {
    return busStations;
  }

  public static void addStation(BusStation station) {
    busStations.put(station.name, station);
  }
}
