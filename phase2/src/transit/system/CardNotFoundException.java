package transit.system;

/** Exception thrown when a card is not found */
public class CardNotFoundException extends TransitException {
  /** @return the error message that this exception returns. */
  public String getMessage() {
    return "Card Not Found: The given information does not map to a card in this transit system";
  }
}
