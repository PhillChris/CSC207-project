/** Exception thrown when a Trip is deemed invalid */
public class InvalidTripException extends TransitException {
  public String getMessage() {
    return "Invalid Trip Found. Max Fee Charged.";
  }
}
