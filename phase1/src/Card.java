import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Card {
  public static final int CARD_INITIAL_BALANCE = 1900;
  private static int cardCounter = 1;
  /** The current trip this card is on. null when no active trip */
  Trip currentTrip;

  boolean isActive;
  private int balance;
  /** A list containing a history of all trips this Card has created. */
  private List<Trip> allTrips = new ArrayList<>();

  private int id;

  public Card(int cardID) {
    this.balance = CARD_INITIAL_BALANCE;
    this.isActive = true;
    this.id = cardID;
  }

  public int getId() {
    return this.id;
  }

  public int getBalance() {
    return this.balance;
  }

  public Trip getCurrentTrip(){
    return currentTrip;
  }

  public List<Trip> getAllTrips() {
    return allTrips;
  }

  public boolean getTripStarted() {
    return currentTrip != null;
  }

  /**
   * Return up to the last three trips taken on this Card.
   *
   * @return a list containing up to the last three trips taken on this Card
   */
  public List<Trip> getLastThree() {
    return allTrips.subList(Math.max(allTrips.size() - 3, 0), allTrips.size());
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

  public void setCurrentTrip(Trip trip){
    currentTrip = trip;
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

  Trip getLastTrip() {
    if (allTrips.size() > 0) { // check this to avoid index errors
      return allTrips.get(allTrips.size() - 1);
    }
    return null;
  }
}
