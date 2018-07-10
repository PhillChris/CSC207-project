/** Thrown when a non-existing station is called */
public class StationNotFoundException extends TransitException {
  public String getMessage() {
    return "Station Not Found: The given information does not "
        + "map to a station in this transit system.";
  }
}
