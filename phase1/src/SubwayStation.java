import java.util.ArrayList;

public class SubwayStation extends Station {
  /**
   * Constructs a new instance of SubwayStation.
   *
   * @param name the name of this SubwayStation
   * @param route the name of the route to which this SubwayStation belongs
   */
  public SubwayStation(String name, String route) {
    this.name = name;
    this.route = route;
    this.initialFee = 0.0;
    this.perStationFee = 0.5;
    this.associatedStation = null;
  }

  public double getFinalFee(Station initialStation) {
    ArrayList<Station> thisRoute = TransitSystem.getSubwayRoutes().get(this.route);
    return this.perStationFee
        * Math.abs(thisRoute.indexOf(this) - thisRoute.indexOf(initialStation));

  }
}
