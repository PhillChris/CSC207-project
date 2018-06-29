public class SubwayStation extends Station {
  /**
   * Constructs a new instance of SubwayStation.
   *
   * @param name the name of this BusStation
   * @param route the name of the route to which this BusStation belongs
   */
  public SubwayStation(String name, String route) {
    this.name = name;
    this.route = route;
    this.initialFee = 0.0;
    this.perStationFee = 0.5;
    this.associatedStation = null;
  }
}
