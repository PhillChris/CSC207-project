/** Exception thrown when someone attempts to use a suspended card */
public class CardSuspendedException extends TransitException {
  @Override
  public String getMessage() {
    return "This card is suspended, get an admin user to reactivate this card to resume use";
  }
}
