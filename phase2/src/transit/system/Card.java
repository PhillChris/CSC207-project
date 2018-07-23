package transit.system;

/** Represents a travel card in a transit system */
public class Card {
  /** The starting balance for all cards, in cents */
  private static final int CARD_INITIAL_BALANCE = 1900;
  /** Stores whether or not this card can be used (i.e. reported stolen) */
  private boolean isActive;
  /** The current trip this card is on, being null when no active trip */
  private Trip currentTrip;
  /** The last trip that this card has completed */
  private Trip lastTrip;
  /** The balance of this card, in cents */
  private int balance;
  /** The id of this card (i.e. which # card it is in some transit.system.User's account) */
  private int id;
  /**
   * Constructs a new card
   *
   * @param cardID The ID for this card
   */
  public Card(int cardID) {
    this.balance = CARD_INITIAL_BALANCE;
    this.isActive = true;
    this.id = cardID;
    lastTrip = null;
  }

  /** @return true if this card is not active */
  boolean isSuspended() {
    return !isActive;
  }

  /** @return The ID of this card */
  int getId() {
    return this.id;
  }

  /** @return The balance of this card, in cents */
  int getBalance() {
    return this.balance;
  }

  /** @return The current transit.system.Trip this card is on, or null if it is not on a trip */
  Trip getCurrentTrip() {
    return currentTrip;
  }

  /**
   * Sets the current trip of this card to a new trip
   *
   * @param trip The new current trip of this card
   */
  void setCurrentTrip(Trip trip) {
    currentTrip = trip;
  }

  /** @return The last transit.system.Trip that this transit.system.Card has completed */
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
  boolean getTripStarted() {
    return currentTrip != null;
  }

  /**
   * Activates this card, after having been found
   *
   * @param card the suspended transit.system.Card to be reactivated
   */
  void activateCard(Card card) {
    isActive = true;
  }

  /** Suspends this card, after having been reported stolen */
  void suspendCard() {
    isActive = false;
  }

  /**
   * Adds the given number of cents to this transit.system.Card's balance.
   *
   * @param toAdd the amount of money to add to this transit.system.Card's balance, in cents
   */
  void addBalance(int toAdd) {
    this.balance += toAdd;
  }

  /**
   * Subtracts given number of cents from this transit.system.Card's balance.
   *
   * @param toSubtract the amount of money to subtract to this transit.system.Card's balance
   */
  void subtractBalance(int toSubtract) {
    this.balance -= toSubtract;
  }

  /** Returns a string representation of this card */
  public String toString() {
    return String.format("Card #%s has $%.2f remaining", this.id, this.balance / 100.0);
  }
}
