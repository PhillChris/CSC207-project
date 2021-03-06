package transit.system;

import java.io.Serializable;

/** Represents a travel card in a transit system */
public class Card implements Serializable {
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
  public boolean isSuspended() {
    return !isActive;
  }

  /** @return The ID of this card */
  public int getId() {
    return this.id;
  }

  /** @return The balance of this card, in cents */
  public int getBalance() {
    return this.balance;
  }

  /** @return The current transit.system.Trip this card is on, or null if it is not on a trip */
  public Trip getCurrentTrip() {
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
  public Trip getLastTrip() {
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

  /** Activates this card, after having been found */
  public void activateCard() {
    isActive = true;
    LogWriter.getInstance().logInfoMessage(Card.class.getName(), "activateCard", "Card activated");
  }

  /** Suspends this card, after having been reported stolen */
  public void suspendCard() {
    isActive = false;
    LogWriter.getInstance().logInfoMessage(Card.class.getName(), "suspendCard", "Card suspended");
  }

  /**
   * Adds the given number of cents to this transit.system.Card's balance.
   *
   * @param toAdd the amount of money to add to this transit.system.Card's balance, in cents
   */
  public void addBalance(int toAdd) {
    this.balance += toAdd;
    LogWriter.getInstance().logAddBalance(toAdd, this.getId());
  }

  /** @return a string representation of this card */
  public String toString() {
    return "Card #" + this.id + " ($" + String.format("%.2f", this.balance / 100.0) + " remaining)";
  }

  /**
   * Subtracts given number of cents from this transit.system.Card's balance.
   *
   * @param toSubtract the amount of money to subtract to this transit.system.Card's balance
   */
  void subtractBalance(int toSubtract) {
    this.balance -= toSubtract;
  }
}
