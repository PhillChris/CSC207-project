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

  /**
   * Tap into the current station, creating a new Trip object with startStation station and
   * timeStarted timeTapped. As of now this method is only called if there is no currentTrip on this
   * Card.
   *
   * @param station the station that this Card tapped in at.
   * @param timeTapped the time at which this Card tapped in.
   */
  void tapIn(Station station, LocalDateTime timeTapped) throws InsufficientFundsException {
    if (balance <= 0) throw new InsufficientFundsException();

    boolean foundContinuousTrip = false; // a flag, just to avoid repetitive code
    Trip lastTrip = getLastTrip();
    if (lastTrip != null) { // check this to avoid index errors
      // check that tapping into this station would be a continuous trip from last trip
      if (lastTrip.isContinuousTrip(station, timeTapped)) {
        currentTrip = lastTrip; // continue the last trip
        currentTrip.continueTrip(station);
        foundContinuousTrip = true;
      }
    }
    if (!foundContinuousTrip) {
      this.currentTrip = new Trip(timeTapped, station);
    }
  }

  void tapOut(Station station, LocalDateTime timeTapped) throws InvalidTripException {
    currentTrip.endTrip(station, timeTapped);
    subtractBalance(currentTrip.getFee());
    boolean validTrip = currentTrip.isValidTrip();
    allTrips.add(currentTrip);
    currentTrip = null;
    if (!validTrip) {
      throw new InvalidTripException();
    }
  }
}
