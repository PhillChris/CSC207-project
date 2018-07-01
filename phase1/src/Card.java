import java.util.Date;
import java.util.List;

public class Card {
  public static final int CARD_INITIAL_BALANCE = 19;
  private int balance;
  /** A list containing a history of all trips this Card has created. */
  private List<Trip> allTrips;
  /** The current trip this card is on. null when no active trip */
  private Trip currentTrip;

  private boolean isActive;

  public Card() {
    this.balance = CARD_INITIAL_BALANCE;
    this.isActive = true;
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
  public void subtractBalance(double toSubtract) {
    this.balance -= toSubtract;
  }

  /**
   * Return the average trip cost of this card.
   *
   * @return the average trip cost over all trips this Card has taken.
   */
  public double getAvgTripCost() {
    double total = 0;

    for (Trip trip : allTrips) {
      total += trip.getFee();
    }
    double avg = total / allTrips.size();
    return avg;
  }

  public void tap(Station station, Date timeTapped) {
    if (currentTrip == null) {
      boolean foundContinuousTrip = false; // a flag, just to avoid repetitive code
      if (allTrips.size() > 0) { // check this to avoid index errors
        Trip lastTrip = allTrips.get(-1);
        Station associatedAtEndStation = lastTrip.endStation.getAssociatedStation();
        // check that tapping into this station would be a continuous trip from last trip
        if (associatedAtEndStation.equals(station)
            && lastTrip.isContinuousTrip(station, timeTapped)) {
          currentTrip = lastTrip;
          currentTrip.continueTrip();
          // restore the balance of last trip so the person does not get charged twice
          this.balance += this.currentTrip.getFee();
          currentTrip.tripFee += station.initialFee;
          foundContinuousTrip = true;
        }
      }
      if (!foundContinuousTrip) tapIn(station, timeTapped);
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
  private void tapIn(Station station, Date timeTapped) {
    Trip newTrip = new Trip(timeTapped, station);
    this.currentTrip = newTrip;
  }

  private void tapOut(Station station, Date timeTapped) {
    currentTrip.endTrip(station, timeTapped);
    this.subtractBalance(currentTrip.getFee());
    allTrips.add(currentTrip);
    currentTrip = null;
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
}
