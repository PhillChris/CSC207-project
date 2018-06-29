import java.util.Date;
import java.util.List;

public class Card {
  public static final int CARD_INITIAL_BALANCE = 19;
  private int balance;
  private List<Trip> allTrips;
  private Trip currentTrip;
  private boolean isActive;

  public Card() {
    this.balance = CARD_INITIAL_BALANCE;
    this.isActive = true;
  }

  public void addBalance(int toAdd) {
  }

  public void subtractBalance(int toSubtract) {
  }

  public double getAvgTripCost() {
    return 0;
  }

  public void tap(Station station, Date timeTapped) {
  }

  public Trip getCurrentTrip() {
    return new Trip();
  }
}
