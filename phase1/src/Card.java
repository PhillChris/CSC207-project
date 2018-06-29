import java.util.Date;
import java.util.List;

public class Card {
  public static final int CARD_INITIAL_BALANCE = 19;
  private int balance;
  /**
   * A list containing a history of all trips this Card has created.
   */
  private List<Trip> allTrips;
  /**
   * The current trip this card is on. null when no active trip
   */
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
  public void subtractBalance(int toSubtract) {
    this.balance -= toSubtract;
  }

  /**
   * Return the average trip cost of this card.
   *
   * @return the average trip cost over all trips this Card has taken.
   */
  public double getAvgTripCost() {
    return 0;
  }

  public void tap(Station station, Date timeTapped) {}

  public Trip getCurrentTrip() {
    return currentTrip;
  }
}
