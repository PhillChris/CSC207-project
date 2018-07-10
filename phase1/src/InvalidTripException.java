/** Exception thrown when a Trip is deemed invalid */
public class InvalidTripException extends TransitException {
  private String userName;
  private String stationName;

  public InvalidTripException(String userName, String stationName) {
    this.userName = userName;
    this.stationName = stationName;
  }

  public String getMessage() {
    return "Invalid Trip found, max fee charged, "
        + this.userName
        + " tapped out at "
        + this.stationName;
  }
}
