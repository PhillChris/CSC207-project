import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// TODO: Figure out how to calculate average monthly cost. Get clarification on "last three trips".

/** Represents a CardHolder in a transit system. */
public class CardHolder {
  /** HashMap linking each email to its CardHolder */
  private static HashMap<String, CardHolder> allUsers = new HashMap<>();
  /** The CardHolder's email */
  private final String email;
  /** An ArrayList of this CardHolder's last three trips */
  ArrayList<Trip> lastThreeTrips = new ArrayList<>();
  /** HashMap linking each month to the total expenditure for that month */
  private HashMap<YearMonth, Integer> ExpenditureMonthly;
  /** HashMap linking each day to the total expenditure for that day */
  private HashMap<LocalDate, Integer> ExpenditureDaily;
  /** An ArrayList of this CardHolder's cards */
  private List<Card> cards;
  /** This CardHolder's name */
  private String name;
  /** The id given to the next card added by the user */
  private int cardCounter;

  /**
   * Construct a new instance of CardHolder
   *
   * @param name the name of this CardHolder
   * @param email the email of this CardHolder
   */
  public CardHolder(String name, String email) throws EmailInUseException {
    if (allUsers.keySet().contains(email)) { // If this CardHolder already exists
      throw new EmailInUseException();
    }
    this.name = name;
    this.email = email;
    this.cards = new ArrayList<Card>();
    allUsers.put(email, this);
    cardCounter = 1;
    ExpenditureMonthly = new HashMap<>();
    ExpenditureDaily = new HashMap<>();
  }

  /**
   * @param email The email of a CardHolder
   * @return The CardHolder with given email
   * @throws UserNotFoundException Thrown if no such CardHolder has given email
   */
  public static CardHolder findUser(String email) throws UserNotFoundException {
    if (allUsers.containsKey(email)) {
      return allUsers.get(email);
    } else {
      throw new UserNotFoundException();
    }
  }

  /** @return This CardHolder's email */
  public String getEmail() {
    return this.email;
  }

  /**
   * Retrieves this CardHolder's card with the given ID
   *
   * @param cardId The user-specific id of this card
   * @return The Card with the given ID
   * @throws CardNotFoundException Thrown if this CardHolder contains no card with given ID
   */
  public Card getCard(int cardId) throws CardNotFoundException {
    for (Card tempCard : this.cards) {
      if (tempCard.getId() == cardId) {
        return tempCard;
      }
    }
    throw new CardNotFoundException();
  }

  /** @return A String detaily average expenditure per month */
  public int getAvgMonthly() {
    List<Integer> monthlyCosts = new ArrayList<>(this.ExpenditureMonthly.values());
    int total = 0;
    for (Integer month : monthlyCosts) {
      total += month;
    }
    int numMonths = this.ExpenditureMonthly.keySet().size();
    return total / numMonths;
  }

  /**
   * Return the last three trips taken on any of this CardHolder's cards.
   *
   * @return up to the last three trips taken by this CardHolder.
   */
  public List<Trip> getLastThree() {
    return lastThreeTrips;
  }

  /**
   * Change this CardHolder's name
   *
   * @param newName The new name of this CardHolder
   */
  public void changeName(String newName) {
    this.name = newName;
  }

  /** Add a card to this CardHolder's list of cards. */
  public void addCard() {
    this.cards.add(new Card(cardCounter++));
  }

  /**
   * Remove a card from this CardHolder's list of cards.
   *
   * @param card
   */
  public void removeCard(Card card) {
    if (this.cards.contains(card)) {
      this.cards.remove(card);
    }
  }

  /**
   * Updates card and user information after this CardHolder taps their card
   *
   * @param card The card which this CardHolder taps
   * @param station The station which this CardHolder taps at
   * @param timeTapped The time this CardHolder taps their Card
   * @throws InsufficientFundsException Thrown if the given card contains a negative balance
   * @throws CardSuspendedException Thrown if the given card is suspended
   * @throws InvalidTripException Thrown if the final trip made by the user is invalid
   */
  public void tap(Card card, Station station, LocalDateTime timeTapped)
      throws InsufficientFundsException, CardSuspendedException, InvalidTripException {

    if (!card.isActive) {
      throw new CardSuspendedException();
    }
    if (card.getCurrentTrip() == null) {
      tapIn(card, station, timeTapped);
    } else {
      tapOut(card, station, timeTapped);
    }
  }

  /**
   * A helper method simulating this CardHolder starting a new trip
   *
   * @param card The card which this CardHolder taps
   * @param station The station which this CardHolder taps at
   * @param timeTapped The time this CardHolder taps their Card
   * @throws InsufficientFundsException Thrown if the final trip made by the user is invalid
   */
  private void tapIn(Card card, Station station, LocalDateTime timeTapped)
      throws InsufficientFundsException {
    if (card.getBalance() <= 0) throw new InsufficientFundsException();

    // Check if this CardHolder is continuing a Trip
    boolean foundContinuousTrip = false;
    Trip lastTrip = card.getLastTrip();
    if (lastTrip != null) { // if there is no lastTrip for this card
      if (lastTrip.isContinuousTrip(station, timeTapped)) {
        card.setCurrentTrip(lastTrip); // continue the last trip
        lastTrip.continueTrip(station);
        foundContinuousTrip = true;
      }
    }
    if (!foundContinuousTrip) {
      card.setCurrentTrip(new Trip(timeTapped, station));
    }
  }

  /**
   * Helper method which simulates this CardHolder end a trip
   *
   * @param card The card which this CardHolder taps
   * @param station The station which this CardHolder taps at
   * @param timeTapped The time this CardHolder taps their Card
   * @throws InvalidTripException Thrown if the final trip made by the user is invalid
   */
  private void tapOut(Card card, Station station, LocalDateTime timeTapped)
      throws InvalidTripException {
    Trip trip = card.getCurrentTrip();
    trip.endTrip(station, timeTapped); // ends the trip
    card.subtractBalance(trip.getFee()); // deducts the balance
    card.setLastTrip(trip);
    card.setCurrentTrip(null);
    updateSpendingHistory(card); // Update's this CardHolder's spending history
    CostCalculator.updateSystemRevenue(
        card.getLastTrip().getFee()); // Update System's spending history
    if (!lastThreeTrips.contains(trip)) {
      lastThreeTrips.add(trip);
    }
    if (lastThreeTrips.size() == 4) {
      lastThreeTrips.remove(0);
    }
    if (!trip.isValidTrip()) {
      throw new InvalidTripException();
    }
  }

  /**
   * Updates the spending history for this User
   *
   * @param card The card most recently used by the CardHolder
   */
  private void updateSpendingHistory(Card card) {
    LocalDate date = TransitTime.getCurrentDate();
    YearMonth month = YearMonth.of(date.getYear(), date.getMonth());
    Trip lastTrip = card.getLastTrip();

    // Update Monthly Expenditure History
    if (ExpenditureMonthly.containsKey(month)) {
      ExpenditureMonthly.put(month, ExpenditureMonthly.get(month) + lastTrip.getFee());
    } else {
      ExpenditureMonthly.put(month, lastTrip.getFee());
    }

    // Update Daily Expenditure History
    if (ExpenditureDaily.containsKey(date)) {
      ExpenditureDaily.put(date, ExpenditureDaily.get(date) + lastTrip.getFee());
    } else {
      ExpenditureDaily.put(date, lastTrip.getFee());
    }
  }
}
