public class BusStation extends Station {
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
}
