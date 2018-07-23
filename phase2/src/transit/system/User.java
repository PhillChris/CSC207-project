package transit.system;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** Represents a transit.system.User in a transit system. */
public class User {
  /** HashMap linking each email to its transit.system.User */
  private static HashMap<String, User> allUsers = new HashMap<>();
  /** The transit.system.User's email */
  private final String email;
  /** Calculates and sends the daily revenue recieved from this user to the system */
  protected CostCalculator calculator;
  /** An ArrayList of this transit.system.User's last three trips */
  private ArrayList<Trip> lastThreeTrips = new ArrayList<>();
  /** HashMap linking each month to the total expenditure for that month */
  private HashMap<YearMonth, Integer> expenditureMonthly;
  /** The log mapping all users to their given password in the system */
  private static HashMap<String, String> authLog = new HashMap<>();
  /** An ArrayList of this transit.system.User's cards */
  private HashMap<Integer, Card> cards;
  /** This transit.system.User's name */
  private String name;
  /** This transit.system.User's password */
  private String password;
  /** The id given to the next card added by the user */
  private int cardCounter;

  /**
   * Construct a new instance of transit.system.User
   *
   * @param name the name of this transit.system.User
   * @param email the email of this transit.system.User
   * @param password the password of ths transit.system.User
   */
  public User(String name, String email, String password) throws EmailInUseException {
    if (allUsers.keySet().contains(email)) { // If this transit.system.User already exists
      throw new EmailInUseException();
    }
    this.name = name;
    this.email = email;
    this.password = password;
    this.cards = new HashMap<>();
    allUsers.put(email, this);
    authLog.put(email, password);
    cardCounter = 1;
    expenditureMonthly = new HashMap<>();
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

  static HashMap<String, String> getAuthLogCopy() {
    HashMap<String, String> copy = new HashMap<>();
    for (String name : authLog.keySet()) {
      copy.put(name, authLog.get(name));
    }
    return copy;
  }

  /** Removes this user from the system. */
  void removeUser() {
    allUsers.remove(this.email);
    authLog.remove(this.email);
  }

  String getUserName() {
    return this.name;
  }

  /** @return This transit.system.User's email */
  String getEmail() {
    return this.email;
  }

  /** @return A copy of the cards this user holds */
  HashMap<Integer, Card> getCardsCopy() {
    HashMap<Integer, Card> tempMap = new HashMap<>();
    for (Integer i: this.cards.keySet()) {
      tempMap.put(i, this.cards.get(i));
    }
    return tempMap;
  }

  /** @return A String detailing average expenditure per month of this transit.system.User. */
  String getAvgMonthly() {
    String message = "Cost per month for user: " + this.name + System.lineSeparator();
    List<YearMonth> months = new ArrayList<>(this.expenditureMonthly.keySet());
    for (YearMonth month : months) {
      message +=
          month.toString()
              + " : "
              + "$"
              + String.format(
                  "%.2f", expenditureMonthly.get(month) / (month.lengthOfMonth() * 100.0));
      message += System.lineSeparator();
    }
    return message.trim();
  }

  /**
   * Return the last three trips taken on any of this transit.system.User's cards.
   *
   * @return up to the last three trips taken by this transit.system.User.
   */
  List<Trip> getLastThreeCopy() {
    List<Trip> copy = new ArrayList<>(lastThreeTrips);
    return copy;
  }

  /**
   * Change this transit.system.User's name.
   *
   * @param newName the new name of this transit.system.User.
   */
  void changeName(String newName) {
    this.name = newName;
  }

  /** Add a card to this transit.system.User's list of cards. */
  void addCard() {
    this.cards.put(cardCounter, new Card(cardCounter));
    cardCounter++;
  }

  /**
   * Remove a card from this transit.system.User's list of cards. If this user does not own the card, do nothing.
   *
   * @param card the card to be removed from this Users collection of cards.
   */
  void removeCard(Card card) {
    if (card != null) {
      if (this.cards.get(card.getId()) == card) {
        this.cards.remove(card.getId(), card);
      }
    }
  }

  /** @return A string representation of a user */
  public String toString() {
    return this.name;
  }

  /**
   * Updates card and user information after this transit.system.User taps their card
   *
   * @param card The card which this transit.system.User taps
   * @param station The station which this transit.system.User taps at
   * @param timeTapped The time this transit.system.User taps their transit.system.Card
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
   * A helper method simulating this transit.system.User starting a new trip. This method starts a transit.system.Trip on the
   * given transit.system.Card object.
   *
   * @param card The card which this transit.system.User taps
   * @param station The station which this transit.system.User taps at
   * @param timeTapped The time this transit.system.User taps their transit.system.Card
   * @throws InsufficientFundsException Thrown if the final trip made by the user is invalid
   */
  private void tapIn(Card card, Station station, LocalDateTime timeTapped)
      throws InsufficientFundsException {
    if (card.getBalance() <= 0) throw new InsufficientFundsException();

    // Check if this transit.system.User is continuing a transit.system.Trip
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
   * Helper method which simulates this transit.system.User ending a trip. This method sets the given transit.system.Card's
   * current transit.system.Trip to null and updates the transit.system.Card's balance.
   *
   * @param card The card which this transit.system.User taps
   * @param station The station which this transit.system.User taps at
   * @param timeTapped The time this transit.system.User taps their transit.system.Card
   * @throws InvalidTripException Thrown if the final trip made by the user is invalid
   */
  private void tapOut(Card card, Station station, LocalDateTime timeTapped)
      throws InvalidTripException {
    Trip trip = card.getCurrentTrip();
    trip.endTrip(station, timeTapped); // ends the trip
    card.subtractBalance(trip.getFee()); // deducts the balance
    card.setLastTrip(trip);
    card.setCurrentTrip(null);
    updateSpendingHistory(card); // Update's this transit.system.User's spending history
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
   * Updates the spending history for this transit.system.User
   *
   * @param card The card most recently used by the transit.system.User
   */
  private void updateSpendingHistory(Card card) {
    LocalDate date = TransitTime.getCurrentDate();
    YearMonth month = YearMonth.of(date.getYear(), date.getMonth());
    Trip lastTrip = card.getLastTrip();

    // Update Monthly Expenditure History
    if (expenditureMonthly.containsKey(month)) {
      expenditureMonthly.put(month, expenditureMonthly.get(month) + lastTrip.getFee());
    } else {
      expenditureMonthly.put(month, lastTrip.getFee());
    }
  }

  public HashMap<YearMonth, Integer> getExpenditureMonthly() {
    return expenditureMonthly;
  }
}
