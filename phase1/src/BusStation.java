import java.util.ArrayList;

public class BusStation implements Station {
  private String name;
  private ArrayList<Station> route;
  private float initialFee = 2.0f;
  private SubwayStation associatedSubway;

  /**
   * Constructs a new instance of BusStation.
   *
   * @param name the name of this BusStation
   * @param route the name of the route to which this BusStation belongs
   */
  public BusStation(String name, String route) {
    this.name = name;
    this.route = route;
    this.associatedSubway = null;
  }

  /**
   * Associates a given SubwayStation with this BusStation (i.e. if they are the same station), for
   * the purpose of measuring single-trip continuity.
   *
   * @param associatedSubway the Subway Station which this
   */
  public void associateSubway(SubwayStation associatedSubway) {
    this.associatedSubway = associatedSubway;
  }

  /**
   * Adds the appropriate fare to a card for riding the bus. Called at the beginning of a bus trip.
   *
   * @param chargedTrip the trip to add fare to for the given bus ride
   */
  public void charge(Trip chargedTrip) {
    chargedTrip.addFare(this.initialFee);
  }
}
