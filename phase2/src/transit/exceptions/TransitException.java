package transit.exceptions;

/** Thrown when an exception occurs in the transit system */
public class TransitException extends Exception {
  /** @return the error message that this exception returns */
  public String getMessage() {
    return "Invalid operation: An invalid operation was performed in the runtime of this system";
  }
}
