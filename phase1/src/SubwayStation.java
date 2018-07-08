public class SubwayStation extends Station {
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
}
