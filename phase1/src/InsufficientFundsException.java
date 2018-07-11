/** Exception thrown when a card with insufficient funds is used */
public class InsufficientFundsException extends TransitException {
  /** @return the error message that this exception returns. */
  public String getMessage() {
    return "Insufficient funds: Attempted to excessively reduce a given card's balance";
  }
}
