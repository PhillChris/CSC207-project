/** Represents a travel card in a transit system */
public class Card {
  /** The starting balance for all cards */
  public static final int CARD_INITIAL_BALANCE = 1900;
  /** The activity of this card */
  boolean isActive;
  /** The current trip this card is on. null when no active trip */
  private Trip currentTrip;
  /** The last trip completed by this card */
  private Trip lastTrip;
  /** The balance of this card */
  private int balance;
  /** The cardID of this card */
  private int id;

  /**
   * Contruct a new card
   *
   * @param cardID The ID for this card
   */
  public Card(int cardID) {
    this.balance = CARD_INITIAL_BALANCE;
    this.isActive = true;
    this.id = cardID;
    lastTrip = null;
  }

  /** @return The ID of this card */
  public int getId() {
    return this.id;
  }

  /** @return The balance of this card */
  public int getBalance() {
    return this.balance;
  }

  /** @return The current Trip of this card */
  public Trip getCurrentTrip() {
    return currentTrip;
  }

  /**
   * Sets the current trip of this card to a new trip
   *
   * @param trip The new current trip of this card
   */
  public void setCurrentTrip(Trip trip) {
    currentTrip = trip;
  }

  /** @return The last Trip completed by this Card */
  Trip getLastTrip() {
    return lastTrip;
  }

  /**
   * Sets the last trip of this card to a new trip
   *
   * @param trip The new last trip of this card
   */
  void setLastTrip(Trip trip) {
    lastTrip = trip;
  }

  /** @return whether this card is currently on a trip */
  public boolean getTripStarted() {
    return currentTrip != null;
  }

  /**
   * Activate this card
   *
   * @param card this User's card being suspended
   */
  public void activateCard(Card card) {
    isActive = true;
  }

  /** Suspend this card */
  public void suspendCard() {
    isActive = false;
  }

  /**
   * Add given int to this Card's balance.
   *
   * @param toAdd the amount of money to add to this Card's balance
   */
  public void addBalance(int toAdd) {
    this.balance += toAdd;
  }

  /**
   * Subtract given int from this Card's balance.
   *
   * @param toSubtract the amount of money to subtract to this Card's balance
   */
  public void subtractBalance(int toSubtract) {
    this.balance -= toSubtract;
  }

  /** Returns a string representation of this card */
  public String toString() {
    return "Card #" + this.id + " has " + String.format("$%.2f", this.balance / 100.0)
            + " remaining";
  }
}
