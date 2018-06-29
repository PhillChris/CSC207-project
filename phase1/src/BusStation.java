public class BusStation extends Station {
  /**
   * Constructs a new instance of BusStation.
   *
   * @param name the name of this BusStation
   * @param route the name of the route to which this BusStation belongs
   */
  public BusStation(String name, String route) {
    this.name = name;
    this.route = route;
    this.initialFee = 2.0;
    this.perStationFee = 0.0;
    this.associatedStation = null;
  }
}
