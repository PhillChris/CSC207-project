public class Card {
  public static final int CARD_INITIAL_BALANCE = 1900;
  private static int cardCounter = 1;
  /** The current trip this card is on. null when no active trip */
  private Trip currentTrip;

  private Trip lastTrip;

  boolean isActive;
  private int balance;

  private int id;

  public Card(int cardID) {
    this.balance = CARD_INITIAL_BALANCE;
    this.isActive = true;
    this.id = cardID;
    lastTrip = null;
  }

  public int getId() {
    return this.id;
  }

  public int getBalance() {
    return this.balance;
  }

  public Trip getCurrentTrip() {
    return currentTrip;
  }

  Trip getLastTrip() {
    return lastTrip;
  }

  void setLastTrip(Trip trip){
    lastTrip =  trip;
  }

  public void setCurrentTrip(Trip trip) {
    currentTrip = trip;
  }

  public boolean getTripStarted() {
    return currentTrip != null;
  }

  /**
   * Suspend a card belonging to this CardHolder.
   *
   * @param card this CardHolder's card being suspended
   */
  public void activateCard(Card card) {
    isActive = true;
  }

  /**
   * Suspend this card.
   *
   * @param card this CardHolder's card being suspended
   */
  public void suspendCard(Card card) {
    isActive = false;
  }

  /**
   * Add given int toAdd to this Card's balance.
   *
   * @param toAdd the amount of money to add to this Card's balance
   */
  public void addBalance(int toAdd) {
    this.balance += toAdd;
  }

  /**
   * Subtract given int toSubtract to this Card's balance.
   *
   * @param toSubtract the amount of money to add to this Card's balance
   */
  public void subtractBalance(int toSubtract) {
    this.balance -= toSubtract;
  }
}
