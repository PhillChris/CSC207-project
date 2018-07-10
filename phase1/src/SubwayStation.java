import java.util.HashMap;

public class SubwayStation extends Station {
  private int SUBWAY_INITIAL_FEE = 0;
  private int SUBWAY_PERSTATION_FEE = 50;


  private static HashMap<String, SubwayStation> subwayStations = new HashMap<>();

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

  public static HashMap<String, SubwayStation> getStations() { return subwayStations; }

  public static void addStation(SubwayStation station) {
    subwayStations.put(station.getName(), station);
  }
}
