import java.util.ArrayList;

public abstract class Station {
  protected String name;
  protected String route;
  protected int initialFee;
  protected int perStationFee;
  protected Station associatedStation;

  /** @return the name of this station */
  public String getName() {
    return this.name;
  }

  /** @return the name of this station's route */
  public String getRoute() {
    return this.route;
  }

  /**
   * Called at the beginning of a ride between stations
   *
   * @return the constant fare for this type of station
   */
  public int getInitialFee() {
    return this.initialFee;
  }

  /**
   * Called at the end of a ride.
   *
   * @param initialStation the station at which this ride started (i.e. when last changing modes of
   *     transportation or vehicle lines).
   * @return the per-station fare for this ride
   */
  public abstract int getFinalFee(Station initialStation);

  /**
   * @return the associated station to this station (i.e. the bus station in this subway station or
   *     the subway station connected to this bus station)
   */
  public Station getAssociatedStation() {
    return this.associatedStation;
  }

  /**
   * Associates a given station with this one.
   *
   * @param associatedStation the station to be associated with this one.
   */
  public void associate(Station associatedStation) {
    this.associatedStation = associatedStation;
  }
}
