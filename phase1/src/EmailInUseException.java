/**
 * Exception thrown when an attempt to create a user with an email already present in the system is
 * made
 */
public class EmailInUseException extends TransitException {
  public String getMessage() {
    return "Email already in use: A cardholder with this email already exists in this transit system";
  }
}
