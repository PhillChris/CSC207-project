/** Thrown when an exception occurs in the transit system */
public class TransitException extends Exception {
  public String getMessage() {
    return "Invalid operation: An invalid operation was performed in the runtime of this system";
  }
}
