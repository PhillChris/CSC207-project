import java.util.ArrayList;

public class SubwayStation implements Station {
  private String name;
  private String route;
  private double initialFee = 0.0;
  private double perStationFee = 0.5;
  private BusStation associatedBus;

  /**
   * Constructs a new instance of SubwayStation.
   *
   * @param name the name of this SubwayStation
   * @param route the name of the route to which this BusStation belongs
   */
  public SubwayStation(String name, String route) {
    this.name = name;
    this.route = route;
    associatedBus = null;
  }

  /** @return the name of this subway station */
  public String getName() {
    return this.name;
  }

  /** @return the route of this subway station */
  public String getRoute() {
    return this.route;
  }

  /** @return the associated bus station to this subway station */
  public Station getAssociatedStation() {
    return this.associatedBus;
  }

  /**
   * Associates a given BusStation with this SubwayStation (i.e. if they are the same station), for
   * the purpose of measuring single-trip continuity.
   *
   * @param associatedStation the station associated with this station
   */
  public void associate(Station associatedStation) {
    this.associatedBus = associatedBus;
  }

  /**
   * Called at the beginning of a subway ride.
   *
   * @return the fixed cost of taking the subway (not the per-station cost)
   */
  public double getInitialFee() {
    return this.initialFee;
  }

  /**
   * Called at the end of a subway ride.
   *
   * @param initialStation the station at which this subway trip started
   * @return the per-station fare of the subway ride (i.e. without including fixed cost)
   */
  public double getFinalFee(Station initialStation) {
    ArrayList<Station> thisRoute = TransitSystem.getRoutes().get(this.route);
    return this.perStationFee
        * Math.abs(thisRoute.indexOf(this) - thisRoute.indexOf(initialStation));
  }
}
