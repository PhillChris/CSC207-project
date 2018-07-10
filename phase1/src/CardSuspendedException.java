/** Exception thrown when a suspended card is used */
public class CardSuspendedException extends TransitException {
  @Override
  public String getMessage() {
    return "This card is suspended, get an admin user to " + "reactivate this card to resume use";
  }
}
