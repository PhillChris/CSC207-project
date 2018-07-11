/** Exception thrown when the initial line of events.txt is incorrect */
public class InitLineException extends TransitException {
  public String getMessage() {
    return "Input line invalid: Program not executable";
  }
}
