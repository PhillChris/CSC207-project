public class CardSuspendedException extends TransitException {
  @Override
  public String getMessage() {
    return "This card is suspended. Get an admin user to " +
            "reactivate this card to resume use.";
  }
}
