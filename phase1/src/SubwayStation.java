import java.util.HashMap;

public class SubwayStation extends Station {
  private static HashMap<String, Station> subwayStations = new HashMap<>();
  private int SUBWAY_INITIAL_FEE = 0;
  private int SUBWAY_PERSTATION_FEE = 50;

  /**
   * Constructs a new instance of SubwayStation.
   *
   * @param name the name of this SubwayStation
   */
  public SubwayStation(String name) {
    super(name);
    this.initialFee = SUBWAY_INITIAL_FEE;
    this.perStationFee = SUBWAY_PERSTATION_FEE;
  }

  public static void newSubwayStation(Station station) {
    subwayStations.put(station.getName(), station);
  }

  public static HashMap<String, Station> getSubwayStations() {
    return subwayStations;
  }

  public HashMap<String, Station> getStations() {
    return subwayStations;
  }
}
