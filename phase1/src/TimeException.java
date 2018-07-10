/** Thrown when an invalid time is detected in the transit system */
public class TimeException extends TransitException {
  public String getMessage() {
    return "Invalid Time: Attempted to enter events in non-chronological order";
  }
}
