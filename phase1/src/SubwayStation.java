import java.util.HashMap;

public class SubwayStation extends Station {

  private static HashMap<String, SubwayStation> subwayStations = new HashMap<>();

  /**
   * Constructs a new instance of SubwayStation.
   *
   * @param name the name of this SubwayStation
   */
  public SubwayStation(String name) {
    super(name);
    this.initialFee = 0;
    this.perStationFee = 50;
  }

  public static HashMap<String, SubwayStation> getStations() { return subwayStations; }

  public static void addStation(SubwayStation station) {
    subwayStations.put(station.name, station);
  }
}
