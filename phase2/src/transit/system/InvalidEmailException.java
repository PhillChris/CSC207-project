package transit.system;

public class InvalidEmailException extends TransitException {
  public String getMessage() {
    return "Invalid email format: Please enter a valid email.";
  }
}
