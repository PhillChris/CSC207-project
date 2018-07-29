package transit.system;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** Represents a transit.system.User in a transit system. */
public class User implements Serializable {
  /** HashMap linking each email to its transit.system.User */
  private static HashMap<String, User> allUsers = new HashMap<>();
  /** The log mapping all users to their given password in the system */
  private static HashMap<String, String> authLog = new HashMap<>();
  /** The transit.system.User's email */
  private final String email;
  /**
   * Email format regex (from https://howtodoinjava.com/regex/java-regex-validate-email-address/)
   */
  private static final String EMAILREGEX =
      "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
  /** Calculates and sends the daily revenue recieved from this user to the system */
  protected CostCalculator calculator;
  /** A log of taps mapping a given date to the number of taps in recorded */
  private HashMap<LocalDate, Integer> tapInLog = new HashMap<>();
  /** A log of taps mapping a given date to the number of taps out recorded */
  private HashMap<LocalDate, Integer> tapOutLog = new HashMap<>();
  /** An ArrayList of this transit.system.User's last three trips */
  private ArrayList<Trip> previousTrips = new ArrayList<>();
  /** HashMap linking each month to the total expenditure for that month */
  private HashMap<YearMonth, Integer> expenditureMonthly;
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
  public User(String name, String email, String password) throws TransitException {
    if (!email.matches(EMAILREGEX)) {
      throw new TransitException();
    }
    if (allUsers.keySet().contains(email)) { // If this transit.system.User already exists
      throw new TransitException();
    }
    this.name = name;
    this.email = email;
    this.password = password;
    this.cards = new HashMap<>();
    allUsers.put(email, this);
    authLog.put(email, password);
    cardCounter = 1;
    expenditureMonthly = new HashMap<>();
    expenditureMonthly.put(YearMonth.of(2018, 3), 5);
    calculator = new CostCalculator();
  }

  /** @return a copy of the HashMap of all Users */
  public static HashMap<String, User> getAllUsersCopy() {
    HashMap<String, User> copy = new HashMap<>();
    for (String name : allUsers.keySet()) {
      copy.put(name, allUsers.get(name));
    }
    return copy;
  }

  public static HashMap<String, String> getAuthLogCopy() {
    HashMap<String, String> copy = new HashMap<>();
    for (String name : authLog.keySet()) {
      copy.put(name, authLog.get(name));
    }
    return copy;
  }

  /** Removes this user from the system. */
  public void removeUser() {
    allUsers.remove(this.email);
    authLog.remove(this.email);
  }

  public String getUserName() {
    return this.name;
  }

  /** @return This transit.system.User's email */
  String getEmail() {
    return this.email;
  }

  /** @return A copy of the cards this user holds */
  public HashMap<Integer, Card> getCardsCopy() {
    HashMap<Integer, Card> tempMap = new HashMap<>();
    for (Integer i : this.cards.keySet()) {
      tempMap.put(i, this.cards.get(i));
    }
    return tempMap;
  }

  /** @return A String detailing average expenditure per month of this transit.system.User. */
  public String getAvgMonthly() {
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
   * Takes the last 3 trips taken and returns a string representation of this list
   *
   * @return the last 3 trips message
   */
  public String getLastThreeMessage() {
    String message = "Last 3 trips by user " + this.name + ":";
    for (int i = 0; i < Math.min(3, previousTrips.size()); i++){
      Trip t = previousTrips.get(previousTrips.size() - 1 - i);
      message += "\n" + t.toString();
    }
    return message;
  }


  /**
   * Change this transit.system.User's name.
   *
   * @param newName the new name of this transit.system.User.
   */
  public void changeName(String newName) {
    this.name = newName;
  }

  /** Add a card to this transit.system.User's list of cards. */
  public void addCard() {
    this.cards.put(cardCounter, new Card(cardCounter));
    cardCounter++;
  }

  /**
   * Remove a card from this transit.system.User's list of cards. If this user does not own the
   * card, do nothing.
   *
   * @param card the card to be removed from this Users collection of cards.
   */
  public void removeCard(Card card) {
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
   */
  public void tap(Card card, Station station, LocalDateTime timeTapped) throws TransitException {

    if (card.isSuspended()) {
      throw new TransitException();
    }
    if (card.getCurrentTrip() == null) {
      tapIn(card, station);
    } else {
      tapOut(card, station);
    }
  }

  public int getTapsIn(LocalDate date) {
    if (tapInLog.get(date) != null) {
      return tapInLog.get(date);
    }
    return 0;
  }

  public int getTapsOut(LocalDate date) {
    if (tapOutLog.get(date) != null) {
      return tapOutLog.get(date);
    }
    return 0;
  }

  /**
   * A helper method simulating this transit.system.User starting a new trip. This method starts a
   * transit.system.Trip on the given transit.system.Card object.
   *
   * @param card The card which this transit.system.User taps
   * @param station The station which this transit.system.User taps at
   * @param timeTapped The time this transit.system.User taps their transit.system.Card
   */
  private void tapIn(Card card, Station station) throws TransitException {
    if (card.getBalance() <= 0) throw new TransitException();
    recordTapIn();
    station.recordTapIn();
    // Check if this transit.system.User is continuing a transit.system.Trip
    boolean foundContinuousTrip = false;
    Trip lastTrip = card.getLastTrip();
    if (lastTrip != null) { // if there is no lastTrip for this card
      if (lastTrip.isContinuousTrip(station)) {
        card.setCurrentTrip(lastTrip); // continue the last trip
        lastTrip.continueTrip(station);
        this.previousTrips.remove(lastTrip);
        foundContinuousTrip = true;
      }
    }
    if (!foundContinuousTrip) {
      card.setCurrentTrip(new Trip(station));
    }
  }

  /**
   * Helper method which simulates this transit.system.User ending a trip. This method sets the
   * given transit.system.Card's current transit.system.Trip to null and updates the
   * transit.system.Card's balance.
   *
   * @param card The card which this transit.system.User taps
   * @param station The station which this transit.system.User taps at
   * @param timeTapped The time this transit.system.User taps their transit.system.Card
   */
  private void tapOut(Card card, Station station)
      throws TransitException {
    recordTapOut();
    station.recordTapOut();
    Trip trip = card.getCurrentTrip();
    trip.endTrip(station); // ends the trip
    card.subtractBalance(trip.getFee()); // deducts the balance
    card.setLastTrip(trip);
    card.setCurrentTrip(null);
    updateSpendingHistory(card); // Update's this transit.system.User's spending history
    // Update System's spending history
    calculator.updateSystemRevenue(trip.getFee(), Math.max(trip.getTripLegLength(), 0));
    if (!previousTrips.contains(trip)) {
      previousTrips.add(trip);
    }
    if (!trip.isValidTrip()) {
      throw new TransitException();
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

  private void recordTapIn() {
    LocalDate timeTapped = TransitTime.getCurrentDate();
    if (tapInLog.get(timeTapped) != null) {
      tapInLog.put(timeTapped, tapInLog.get(timeTapped) + 1);
    } else {
      tapInLog.put(timeTapped, 1);
    }
  }

  private void recordTapOut() {
    LocalDate timeTapped = TransitTime.getCurrentDate();
    if (tapOutLog.get(timeTapped) != null) {
      tapOutLog.put(timeTapped, tapOutLog.get(timeTapped) + 1);
    } else {
      tapOutLog.put(timeTapped, 1);
    }
  }
}
