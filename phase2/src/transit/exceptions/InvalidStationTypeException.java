package transit.exceptions;

import transit.exceptions.TransitException;

/** Exception thrown when a non-existing station type is called */
public class InvalidStationTypeException extends TransitException {
  /** @return the error message that this exception returns. */
  public String getMessage() {
    return "Invalid station type: Invalid station type requested";
  }
}
