public class BusStation implements Station {
  private String name;
  private String route;
  private double initialFee = 2.0;
  private Station associatedStation;

  /**
   * Constructs a new instance of BusStation.
   *
   * @param name the name of this BusStation
   * @param route the name of the route to which this BusStation belongs
   */
  public BusStation(String name, String route) {
    this.name = name;
    this.route = route;
    this.associatedStation = null;
  }

  /** @return the name of this bus station */
  public String getName() {
    return this.name;
  }

  /** @return the route of this bus station */
  public String getRoute() {
    return this.route;
  }

  /** @return the associated subway station to this bus station */
  public Station getAssociatedStation() {
    return this.associatedStation;
  }

  /**
   * Associates a given SubwayStation with this BusStation (i.e. if they are the same station), for
   * the purpose of measuring single-trip continuity.
   *
   * @param associatedStation the station associated with this station
   */
  public void associate(Station associatedStation) {
    this.associatedStation = associatedStation;
  }

  /**
   * Called at the beginning of a bus ride
   *
   * @return the constant fare for this bus ride
   */
  public double getInitialFee() {
    return this.initialFee;
  }

  /**
   * Called at the end of a bus ride
   *
   * @return the per-station fare for this bus ride
   */
  public double getFinalFee(Station initialStation) {
    return 0;
  }
}
