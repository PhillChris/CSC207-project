/** Exception thrown when a non-existing station type is called */
public class InvalidStationTypeException extends TransitException {
  public String getMessage() {
    return "Invalid station type: Invalid station type requested";
  }
}
