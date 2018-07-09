import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Card {
  public static final int CARD_INITIAL_BALANCE = 1900;
  private static int cardCounter = 1;
  private int balance;
  /** A list containing a history of all trips this Card has created. */
  private List<Trip> allTrips = new ArrayList<>();
  /** The current trip this card is on. null when no active trip */
  private Trip currentTrip;

  private int id;

  private boolean isActive;

  public Card(int cardID) {
    this.balance = CARD_INITIAL_BALANCE;
    this.isActive = true;
    this.id = cardID;
  }

  public int getId() {
    return this.id;
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

  /**
   * Return the average trip cost of this card.
   *
   * @return the average trip cost over all trips this Card has taken.
   */
  public double getAvgTripCost() {
    double total = allTrips.stream().mapToDouble(Trip::getFee).sum();
    return total / allTrips.size();
  }

  public void tap(Station station, LocalDateTime timeTapped)
      throws InsufficientFundsException, CardSuspendedException, InvalidTripException {
    if (!this.isActive) {
      throw new CardSuspendedException();
    }
    if (currentTrip == null) {
      tapIn(station, timeTapped);
    } else tapOut(station, timeTapped);
  }

  /**
   * Tap into the current station, creating a new Trip object with startStation station and
   * timeStarted timeTapped. As of now this method is only called if there is no currentTrip on this
   * Card.
   *
   * @param station the station that this Card tapped in at.
   * @param timeTapped the time at which this Card tapped in.
   */
  private void tapIn(Station station, LocalDateTime timeTapped) throws InsufficientFundsException {
    if (balance <= 0) throw new InsufficientFundsException();

    boolean foundContinuousTrip = false; // a flag, just to avoid repetitive code
    if (allTrips.size() > 0) { // check this to avoid index errors
      Trip lastTrip = allTrips.get(allTrips.size() - 1);
      // check that tapping into this station would be a continuous trip from last trip
      if (lastTrip.isContinuousTrip(station, timeTapped)) {
        currentTrip = lastTrip; // continue the last trip
        // restore the cost of last trip so the person does not get charged twice
        addBalance(this.currentTrip.getFee());
        currentTrip.continueTrip(station);
        foundContinuousTrip = true;
      }
    }
    if (!foundContinuousTrip) {
      this.currentTrip = new Trip(timeTapped, station);
    }
  }

  private void tapOut(Station station, LocalDateTime timeTapped) throws InvalidTripException {
    currentTrip.endTrip(station, timeTapped);
    boolean validTrip = currentTrip.isValidTrip();
    currentTrip = null;
    if (!validTrip) {
      throw new InvalidTripException();
    }
  }

  public boolean tripStarted() {
    return currentTrip != null;
  }

  public Trip getCurrentTrip() {
    return currentTrip;
  }

  public List<Trip> getAllTrips() {
    return allTrips;
  }

  public void setActive(Boolean bool) {
    this.isActive = bool;
  }

  /**
   * Return up to the last three trips taken on this Card.
   *
   * @return a list containing up to the last three trips taken on this Card
   */
  public List<Trip> getLastThree() {
    return allTrips.subList(Math.max(allTrips.size() - 3, 0), allTrips.size());
  }
}
