import java.util.ArrayList;

public class SubwayStation implements Station {
  private String name;
  private ArrayList<Station> route;
  private float perStationFee = 0.5f;
  private BusStation associatedBus;

  /**
   * Constructs a new instance of SubwayStation.
   *
   * @param name the name of this SubwayStation
   * @param route the name of the route to which this BusStation belongs
   */
  public SubwayStation(String name, ArrayList<Station> route) {
    this.name = name;
    this.route = route;
    associatedBus = null;
  }

  /**
   * Associates a given BusStation with this SubwayStation (i.e. if they are the same station), for
   * the purpose of measuring single-trip continuity.
   *
   * @param associatedBus the Subway Station which this
   */
  public void associateBus(BusStation associatedBus) {
    this.associatedBus = associatedBus;
  }

  /**
   * Adds the appropriate fare to a card for riding the subway. Called at the end of a subway ride.
   *
   * @param chargedTrip the trip to add fare to for the given subway ride
   */
  public void charge(Trip chargedTrip) {
    chargedTrip.addFee(
        this.perStationFee
            * Math.abs(
                this.route.indexOf(this) - this.route.indexOf(chargedTrip.getStartStation())));
  }
}
