import java.util.HashMap;

class SubwayStation extends Station {
  /** HashMap linking SubwayStation names to SubwayStations */
  private static HashMap<String, Station> subwayStations = new HashMap<>();
  /** The fee charged at the start of a trip by this station */
  private int SUBWAY_INITIAL_FEE = 0;
  /** The fee charged per station travelled by this station */
  private int SUBWAY_PERSTATION_FEE = 50;

  /**
   * Constructs a new instance of SubwayStation.
   *
   * @param name the name of this SubwayStation
   */
  SubwayStation(String name) {
    super(name);
    this.setInitialFee(SUBWAY_INITIAL_FEE);
    this.setPerStationFee(SUBWAY_PERSTATION_FEE);
    setStations(subwayStations);
  }
}
