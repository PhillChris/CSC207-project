import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** Represents a User in a transit system. */
public class User {
  /** HashMap linking each email to its User */
  private static HashMap<String, User> allUsers = new HashMap<>();
  /** The User's email */
  private final String email;
  /** Calculates and sends the daily revenue recieved from this user to the system */
  protected CostCalculator calculator;
  /** An ArrayList of this User's last three trips */
  private ArrayList<Trip> lastThreeTrips = new ArrayList<>();
  /** HashMap linking each month to the total expenditure for that month */
  private HashMap<YearMonth, Integer> ExpenditureMonthly;
  /** An ArrayList of this User's cards */
  private List<Card> cards;
  /** This User's name */
  private String name;
  /** The id given to the next card added by the user */
  private int cardCounter;

  /**
   * Construct a new instance of User
   *
   * @param name the name of this User
   * @param email the email of this User
   */
  public User(String name, String email) throws EmailInUseException {
    if (allUsers.keySet().contains(email)) { // If this User already exists
      throw new EmailInUseException();
    }
    this.name = name;
    this.email = email;
    this.cards = new ArrayList<>();
    allUsers.put(email, this);
    cardCounter = 1;
    ExpenditureMonthly = new HashMap<>();
    calculator = new CostCalculator();
  }

  /** @return a copy of the HashMap of all Users */
  static HashMap<String, User> getAllUsersCopy() {
    HashMap<String, User> copy = new HashMap<>();
    for (String name : allUsers.keySet()) {
      copy.put(name, allUsers.get(name));
    }
    return copy;
  }

  /** Removes this user from the system. */
  void removeUser() {
    allUsers.remove(this.email);
  }

  /** @return This User's email */
  String getEmail() {
    return this.email;
  }

  /** @return A copy of the cards this user holds */
  List<Card> getCardsCopy() {
    List<Card> copy = new ArrayList<>(this.cards);
    return copy;
  }

  /** @return A String detailing average expenditure per month of this User. */
  String getAvgMonthly() {
    String message = "Cost per month for user: " + this.name + System.lineSeparator();
    List<YearMonth> months = new ArrayList<>(this.ExpenditureMonthly.keySet());
    for (YearMonth month : months) {
      message +=
          month.toString()
              + " : "
              + "$"
              + String.format(
                  "%.2f", ExpenditureMonthly.get(month) / (month.lengthOfMonth() * 100.0));
      message += System.lineSeparator();
    }
    return message.trim();
  }

  /**
   * Return the last three trips taken on any of this User's cards.
   *
   * @return up to the last three trips taken by this User.
   */
  List<Trip> getLastThreeCopy() {
    List<Trip> copy = new ArrayList<>(lastThreeTrips);
    return copy;
  }

  /**
   * Change this User's name.
   *
   * @param newName the new name of this User.
   */
  void changeName(String newName) {
    this.name = newName;
  }

  /** Add a card to this User's list of cards. */
  void addCard() {
    this.cards.add(new Card(cardCounter++));
  }

  /**
   * Remove a card from this User's list of cards. If this user does not own the card, do nothing.
   *
   * @param card the card to be removed from this Users collection of cards.
   */
  void removeCard(Card card) {
    if (this.cards.contains(card)) {
      this.cards.remove(card);
    }
  }

  /** @return A string representation of a user */
  public String toString() {
    return this.name;
  }

  /**
   * Updates card and user information after this User taps their card
   *
   * @param card The card which this User taps
   * @param station The station which this User taps at
   * @param timeTapped The time this User taps their Card
   * @throws InsufficientFundsException Thrown if the given card contains a negative balance
   * @throws CardSuspendedException Thrown if the given card is suspended
   * @throws InvalidTripException Thrown if the final trip made by the user is invalid
   */
  void tap(Card card, Station station, LocalDateTime timeTapped)
      throws InsufficientFundsException, CardSuspendedException, InvalidTripException {

    if (card.isSuspended()) {
      throw new CardSuspendedException();
    }
    if (card.getCurrentTrip() == null) {
      tapIn(card, station, timeTapped);
    } else {
      tapOut(card, station, timeTapped);
    }
  }

  /**
   * A helper method simulating this User starting a new trip. This method starts a Trip on the
   * given Card object.
   *
   * @param card The card which this User taps
   * @param station The station which this User taps at
   * @param timeTapped The time this User taps their Card
   * @throws InsufficientFundsException Thrown if the final trip made by the user is invalid
   */
  private void tapIn(Card card, Station station, LocalDateTime timeTapped)
      throws InsufficientFundsException {
    if (card.getBalance() <= 0) throw new InsufficientFundsException();

    // Check if this User is continuing a Trip
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
   * Helper method which simulates this User ending a trip. This method sets the given Card's
   * current Trip to null and updates the Card's balance.
   *
   * @param card The card which this User taps
   * @param station The station which this User taps at
   * @param timeTapped The time this User taps their Card
   * @throws InvalidTripException Thrown if the final trip made by the user is invalid
   */
  private void tapOut(Card card, Station station, LocalDateTime timeTapped)
      throws InvalidTripException {
    Trip trip = card.getCurrentTrip();
    trip.endTrip(station, timeTapped); // ends the trip
    card.subtractBalance(trip.getFee()); // deducts the balance
    card.setLastTrip(trip);
    card.setCurrentTrip(null);
    updateSpendingHistory(card); // Update's this User's spending history
    // Update System's spending history
    calculator.updateSystemRevenue(trip.getFee(), Math.max(trip.getTripLegLength(), 0));
    if (!lastThreeTrips.contains(trip)) {
      lastThreeTrips.add(trip);
    }
    if (lastThreeTrips.size() == 4) {
      lastThreeTrips.remove(0);
    }
    if (!trip.isValidTrip()) {
      throw new InvalidTripException(this.name, station.getName());
    }
  }

  /**
   * Updates the spending history for this User
   *
   * @param card The card most recently used by the User
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
  }
}
