package transit.exceptions;

import transit.exceptions.TransitException;

/** Exception thrown when someone attempts to use a suspended card */
public class CardSuspendedException extends TransitException {
  @Override
  /** @return the error message that this exception returns. */
  public String getMessage() {
    return "This card is suspended, get an admin user to reactivate this card to resume use";
  }
}
