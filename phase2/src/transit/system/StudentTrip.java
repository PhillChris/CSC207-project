package transit.system;

/** Represents a trip made by a student in this simulation */
public class StudentTrip extends Trip {

  /**
   * Constructs a new instance of StudentTrip
   *
   * @param station the station which starts this trip
   */
  public StudentTrip(Station station) {
    super(station);
    // charges half of the current fee per station
    perStationFee /= 2;
  }

  /**
   * Continues the trip, while preserving the fee structure from before
   *
   * @param station the station that the trip is being continued from.
   */
  @Override
  void continueTrip(Station station) {
    super.continueTrip(station);
    perStationFee /= 2;
  }
}
