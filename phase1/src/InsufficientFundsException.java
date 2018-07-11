/** Exception thrown when a card with insufficient funds is used */
public class InsufficientFundsException extends TransitException {
  public String getMessage() {
    return "Insufficient funds: Attempted to excessively reduce a given card's balance";
  }
}
