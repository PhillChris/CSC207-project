package transit.exceptions;

import transit.exceptions.TransitException;

public class InvalidCredentialsException extends TransitException {
  public String getMessage() {
    return "Invalid credentials: The credentials presented to this given account are invalid";
  }
}
