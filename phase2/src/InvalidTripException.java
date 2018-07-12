/** Exception thrown when a Trip is deemed invalid */
public class InvalidTripException extends TransitException {
  private String userName;
  private String stationName;

  /**
   * Create a new instance of this InvalidTripException
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
        "Invalid Trip found, max fee charged, %s tapped out at %s",
        this.userName, this.stationName);
  }
}
