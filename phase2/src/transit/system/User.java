package transit.system;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

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
  /** This transit.system.User's name */
  /** The id given to the next card added by the user */
  private int cardCounter;
  /** Determines the permissions and pricing of this user */
  private String permission;
  /** Stores associated tripStatistics to this user */
  private HashMap<String, Statistics> tripStatistics;
  /** Records the last three trips associated to this log */
  private ArrayList<Trip> tripLog;
  /** The name of the user associated with this log */
  private String name;
  /** This transit.system.User's password */
  private String password;

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
    this.name = name;
    this.email = email;
    this.password = password;
    this.permission = permission;
    this.cards = new HashMap<>();
    allUsers.put(email, this);
    cardCounter = 1;
    tripStatistics = new HashMap<>();
    tripStatistics.put("Expenditure", new QuantitativeStatistics());
    tripStatistics.put("Taps", new QuantitativeStatistics());
    tripStatistics.put("Trip Logs", new QualitativeStatistics<Trip>());
  }

  private static HashMap<String, User> setAllUsers()   {
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

  /** @return The permission on this user */
  public String getPermission() {
    return this.permission;
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
  /**
   * Change this transit.system.User's name.
   *
   * @param newName the new name of this transit.system.User.
   */
  public void changeName(String newName) {
    this.name = newName;
  }

  public void changePassword(String currentPassword, String newPassword)
      throws IncorrectPasswordException, InvalidPasswordException {
    if (!currentPassword.equals(password)) {
      throw new IncorrectPasswordException();
    } else if (newPassword.length() < 6) {
      throw new InvalidPasswordException();
    } else {
      password = newPassword;
    }
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
        lastTrip.continueTrip(station, this.permission);
        foundContinuousTrip = true;
      }
    }
    if (!foundContinuousTrip) {
      card.setCurrentTrip(new Trip(station, this.permission));
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
    tripStatistics.get("Trip Logs").update(trip);
    tripStatistics.get("Expenditure").update(trip.getFee());
    QuantitativeStatistics.getSystemRevenue().update(trip.getFee());
    QuantitativeStatistics.getSystemTripLength().update(Math.max(trip.getTripLegLength(), 0));
  }
}
