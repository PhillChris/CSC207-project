import java.util.HashMap;

public class SubwayStation extends Station {
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
  public SubwayStation(String name) {
    super(name);
    this.setInitialFee(SUBWAY_INITIAL_FEE);
    this.setPerStationFee(SUBWAY_PERSTATION_FEE);
  }

  /**
   * Adds a new station to the HashMap of subwayStations
   *
   * @param station The station to be added
   */
  public static void newSubwayStation(Station station) {
    subwayStations.put(station.getName(), station);
  }

  /**
   * Gets HashMap of all SubwayStations from a static context
   *
   * @return HashMap linking SubwayStation names to SubwayStations
   */
  public static HashMap<String, Station> getSubwayStations() {
    return subwayStations;
  }

  /**
   * Gets HashMap of all SubwayStations from a static context
   *
   * @return HashMap linking SubwayStation names to SubwayStations
   */
  public HashMap<String, Station> getStations() {
    return subwayStations;
  }
}
