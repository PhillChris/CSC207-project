package transit.system;

/** Exception thrown when a transit.system.Trip is deemed invalid */
public class InvalidTripException extends TransitException {
  private String userName;
  private String stationName;

  /**
   * Create a new instance of this transit.system.InvalidTripException
   *
   * @param userName the username of the user taking this trip
   * @param stationName the station that the user taking this trip taps out at
   */
  public InvalidTripException(String userName, String stationName) {
    this.userName = userName;
    this.stationName = stationName;
  }
  /** @return the error message that this exception returns. */
  public String getMessage() {
    return String.format(
        "Invalid transit.system.Trip found, max fee charged, %s tapped out at %s",
        this.userName, this.stationName);
  }
}
