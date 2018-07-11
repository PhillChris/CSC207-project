/** A Factory for constructing BusStations */
public class BusFactory implements StationFactory {

  @Override
  /**
   * Construct a new BusStation
   *
   * @param name the name of this BusStation
   */
  public Station newStation(String name) {
    return new BusStation(name);
  }
}
