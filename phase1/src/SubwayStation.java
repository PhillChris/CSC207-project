import java.util.ArrayList;

public class SubwayStation extends Station {
  /**
   * Constructs a new instance of SubwayStation.
   *
   * @param name the name of this SubwayStation
   * @param route the name of the route to which this SubwayStation belongs
   */
  public SubwayStation(String name, Route route) {
    this.name = name;
    this.route = route;
    this.initialFee = 0;
    this.perStationFee = 50;
    this.associatedStations = null;
  }

  public int getFinalFee(Station initialStation) {
    return this.perStationFee
        * Math.abs(route.getStations().indexOf(this) - route.getStations().indexOf(initialStation));
  }
}
