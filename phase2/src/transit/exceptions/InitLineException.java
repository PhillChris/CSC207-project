package transit.exceptions;

import transit.exceptions.TransitException;

/** Exception thrown when the initial line of events.txt is incorrect */
public class InitLineException extends TransitException {
  /** @return the error message that this exception returns. */
  public String getMessage() {
    return "Input line invalid: Program not executable";
  }
}
