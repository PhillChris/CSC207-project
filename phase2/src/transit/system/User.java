package transit.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static transit.system.Database.readObject;

/** Represents a transit.system.User in a transit system. */
public class User implements Serializable {
  /**
   * Email format regex (from https://howtodoinjava.com/regex/java-regex-validate-email-address/)
   */
  private static final String EMAILREGEX =
      "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
  /** HashMap linking each email to its transit.system.User */
  private static HashMap<String, User> allUsers = setAllUsers();
  /** The transit.system.User's email */
  private final String email;
  /** An ArrayList of this transit.system.User's cards */
  private HashMap<Integer, Card> cards;
  /** The id given to the next card added by the user */
  private int cardCounter;
  /** Stores associated tripStatistics to this user */
  private HashMap<String, Statistics> tripStatistics = new HashMap<>();
  /** Stores the personal information associated with this user */
  private UserInfo personalInfo;

  /**
   * Construct a new instance of transit.system.User
   *
   * @param name the name of this transit.system.User
   * @param email the email of this transit.system.User
   * @param password the password of ths transit.system.User
   */
  public User(String name, String email, String password, String permission)
      throws MessageTransitException {
    if (!email.matches(EMAILREGEX)) {
      throw new InvalidEmailException();
    }
    if (allUsers.keySet().contains(email)) { // If this transit.system.User already exists
      throw new EmailInUseException();
    }
    if (password.length() < 6) {
      throw new InvalidPasswordException();
    }
    this.email = email;
    this.cards = new HashMap<>();
    allUsers.put(email, this);
    personalInfo = new UserInfo(name, password, permission);
    tripStatistics.put("Expenditure", new Statistics());
    tripStatistics.put("Taps", new Statistics());
  }

  private static HashMap<String, User> setAllUsers() {
    HashMap<String, User> users = (HashMap<String, User>) readObject(Database.USERS_LOCATION);
    if (users != null) {
      return users;
    }
    return new HashMap<String, User>();
  }

  /** @return a copy of the HashMap of all Users */
  public static HashMap<String, User> getAllUsersCopy() {
    HashMap<String, User> copy = new HashMap<>();
    for (String name : allUsers.keySet()) {
      copy.put(name, allUsers.get(name));
    }
    return copy;
  }

  public UserInfo getPersonalInfo() {
    return personalInfo;
  }

  /** Removes this user from the system. */
  public void removeUser() {
    allUsers.remove(this.email);
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
    tripStatistics.get("Taps").update(1);
    if (card.getCurrentTrip() == null) {
      tapIn(card, station);
    } else {
      tapOut(card, station);
    }
  }

  /**
   * A helper method simulating this User starting a new trip.
   *
   * @param card The card which this transit.system.User taps
   * @param station The station which this transit.system.User taps at
   */
  private void tapIn(Card card, Station station) throws TransitException {
    if (card.getBalance() <= 0) throw new TransitException(); // Not enough fund
    // Record tripStatistics
    station.record("Tap In", 1);

    // Check if this transit.system.User is continuing a transit.system.Trip
    boolean foundContinuousTrip = false;
    Trip lastTrip = card.getLastTrip();
    if (lastTrip != null) {
      if (lastTrip.isContinuousTrip(station)) { // continue the last trip
        card.setCurrentTrip(lastTrip);
        lastTrip.continueTrip(station, personalInfo.getPermission());
        foundContinuousTrip = true;
      }
    }
    if (!foundContinuousTrip) {
      card.setCurrentTrip(new Trip(station, personalInfo.getPermission()));
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
    updateStatistic(trip);
    // Record various tripStatistics
    station.record("Tap Out", 1);
    if (!trip.isValidTrip()) {
      throw new TransitException();
    }
  }

  /** Updates the tripStatistics assoicated with this user and the system */
  private void updateStatistic(Trip trip) {
    personalInfo.getPreviousTrips().add(trip);
    tripStatistics.get("Expenditure").update(trip.getFee());
    Statistics.getSystemRevenue().update(trip.getFee());
    Statistics.getSystemTripLength().update(Math.max(trip.getTripLegLength(), 0));
  }
}
