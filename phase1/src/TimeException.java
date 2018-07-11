/** Thrown when an invalid time is detected in the transit system */
public class TimeException extends TransitException {

  /** @return the error message that this exception returns */
  public String getMessage() {
    return "Invalid Time: Attempted to enter events in non-chronological order";
  }
}
