public class InvalidCredentialsException extends TransitException {
  public String getMessage() {
    return "Invalid credentials: The credentials presented to this given account are invalid";
  }
}
