/** Thrown when a call is made to a non-existing user */
public class UserNotFoundException extends TransitException {
  /** @return the error message that this exception returns */
  public String getMessage() {
    return "User Not Found: The given information does not map to a user in this transit system";
  }
}
