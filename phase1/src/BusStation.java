import java.util.HashMap;
import java.util.Objects;

/** Represents a bus station in this transit system */
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
    setStations(busStations);
  }
}
