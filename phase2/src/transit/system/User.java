package transit.system;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;

/** Represents a transit.system.User in a transit system. */
public class User implements Serializable {
  /**
   * Email format regex (from https://howtodoinjava.com/regex/java-regex-validate-email-address/)
   */
  private static final String EMAILREGEX =
      "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
  /** HashMap linking each email to its transit.system.User */
  private static HashMap<String, User> allUsers = new HashMap<>();
  /** The transit.system.User's email */
  private final String email;
  /** Tracks all related statistics associated with this User */
  protected UserInformation statistics;
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
    cardCounter = 1;
    this.statistics = new UserInformation(this);
  }

  public static void setAllUsers(HashMap<String, User> allUsers) {
    User.allUsers = allUsers;
  }

  /** @return a copy of the HashMap of all Users */
  public static HashMap<String, User> getAllUsersCopy() {
    HashMap<String, User> copy = new HashMap<>();
    for (String name : allUsers.keySet()) {
      copy.put(name, allUsers.get(name));
    }
    return copy;
  }

  public boolean correctAuthentification(String password) {
    return this.password.equals(password);
  }

  /** Removes this user from the system. */
  public void removeUser() {
    allUsers.remove(this.email);
  }

  /** @return The name associated with this user */
  public String getUserName() {
    return this.name;
  }

  /** @return The monthly expenditure of this User */
  public HashMap<YearMonth, Integer> getExpenditureMonthly() {
    return statistics.getMonthlyExpenditure();
  }

  /** @return This User's email */
  String getEmail() {
    return this.email;
  }

  /** @return A shallow copy of the cards this user holds */
  public HashMap<Integer, Card> getCardsCopy() {
    HashMap<Integer, Card> tempMap = new HashMap<>();
    for (Integer i : this.cards.keySet()) {
      tempMap.put(i, this.cards.get(i));
    }
    return tempMap;
  }

  /** @return A String detailing average expenditure per month of this transit.system.User. */
  public String getAvgMonthly() {
    return statistics.avgMonthlyMessage();
  }

  /** @return String representation of the last 3 trips made be this user */
  public String getLastThreeMessage() {
    return statistics.lastThreeTripsMessage();
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
   */
  public void tap(Card card, Station station) throws TransitException {

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
    return statistics.totalTapIns(date);
  }

  public int getTapsOut(LocalDate date) {
    return statistics.totalTapOuts(date);
  }

  /**
   * A helper method simulating this User starting a new trip.
   *
   * @param card The card which this transit.system.User taps
   * @param station The station which this transit.system.User taps at
   */
  private void tapIn(Card card, Station station) throws TransitException {
    if (card.getBalance() <= 0) throw new TransitException(); // Not enough fund
    // Record statistics
    statistics.recordTapIn();
    station.recordTapIn();

    // Check if this transit.system.User is continuing a transit.system.Trip
    boolean foundContinuousTrip = false;
    Trip lastTrip = card.getLastTrip();
    if (lastTrip != null) {
      if (lastTrip.isContinuousTrip(station)) { // continue the last trip
        card.setCurrentTrip(lastTrip);
        lastTrip.continueTrip(station);
        statistics.getPreviousTrips().remove(lastTrip);
        foundContinuousTrip = true;
      }
    }
    if (!foundContinuousTrip) {
      card.setCurrentTrip(new Trip(station));
    }
  }

  /**
   * Helper method which simulates this User ending a trip.
   *
   * @param card The card which this User taps
   * @param station The station which this User taps at
   */
  private void tapOut(Card card, Station station) throws TransitException {
    // Update Card and Trip information
    Trip trip = card.getCurrentTrip();
    trip.endTrip(station); // ends the trip
    card.subtractBalance(trip.getFee()); // deducts the balance
    card.setLastTrip(trip);
    card.setCurrentTrip(null);

    // Record various statistics
    statistics.recordTapOut();
    station.recordTapOut();
    statistics.updateSpendingHistory(card);
    if (!statistics.getPreviousTrips().contains(trip)) {
      statistics.getPreviousTrips().add(trip);
    }
    if (!trip.isValidTrip()) {
      throw new TransitException();
    }
  }
}
