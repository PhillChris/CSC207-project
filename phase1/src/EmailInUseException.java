public class EmailInUseException extends TransitException{
  public String getMessage() {
    return "Email already in use: A cardholder with this email already exists in this transit system.";
  }
}
