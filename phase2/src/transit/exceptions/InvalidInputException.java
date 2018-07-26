package transit.exceptions;

import transit.exceptions.TransitException;

/** Exception thrown when the parameters provided to a command in events.txt are incorrect */
public class InvalidInputException extends TransitException {
  /** @return the error message that this exception returns. */
  public String getMessage() {
    return "Invalid Input: The parameters provided to this function are not correct";
  }
}
