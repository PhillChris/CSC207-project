package transit.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * UserCardCommands processes all user-card related commands.
 */
public class UserCardCommands implements Serializable {
  /** An ArrayList of this transit.system.User's cards */
  private HashMap<Integer, Card> cards;
  /** The id given to the next card added by the user */
  private int cardCounter = 1;
  /** Stores associated cardStatistics to this user */
  private HashMap<String, Statistics> cardStatistics = new HashMap<>();
  /** Determines the permissions and pricing of this user */
  private String permission;
  /** A list of the previous trips of the associated User */
  private List<Trip> previousTrips = new ArrayList<>();
  /**
   * The name of the user associated with these commands.
   */
  private String userName;

  /** Initialize a new instance of UserCardCommands */
  UserCardCommands(String permission, String userName) {
    this.cards = new HashMap<>();
    cardStatistics.put("Expenditure", new Statistics("Expenditure"));
    cardStatistics.put("Taps", new Statistics("Taps"));
    this.permission = permission;
    this.userName = userName;
    addCard();
  }

  @Override
  public String toString() {
    return userName;
  }

  /** @return The permission on this user */
  public String getPermission() {
    return this.permission;
  }

  /** @return the string representation of the last three trips over all cards. */
  public String lastThreeTripsString() {
    StringBuilder stringRep = new StringBuilder("Recent trips:" + System.lineSeparator());

    // loop over the last 3 trips or all trips if there are less than 3 trips made.
    for (int i = 0; i < min(previousTrips.size(), 3); i++) { // las
      stringRep.append(previousTrips.get(previousTrips.size() - (1 + i)).toString()).append(System.lineSeparator());
    }
    return stringRep.toString().trim();
  }

  /** @return HashMap of cardStatistics associated with this User */
  public HashMap<String, Statistics> getCardStatistics() {
    return cardStatistics;
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
    LogWriter.getInstance().logRemoveCard(card.getId());
  }

  /** Add a card to this transit.system.User's list of cards. */
  public void addCard() {
    this.cards.put(cardCounter, new Card(cardCounter));
    cardCounter++;
    LogWriter.getInstance().logAddCard();
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
    cardStatistics.get("Taps").update(1);
    if (card.getCurrentTrip() == null) {
      // if this card was not already on a trip
      station.record("Taps In", 1);
      tapIn(card, station);
    } else {
      // if this card was already on a trip
      station.record("Taps Out", 1);
      tapOut(card, station);
    }
  }

  /**
   * Finds the station most frequently tapped at (NOTE: intersections are only counted for one tap
   * in this statistic)
   *
   * @return A string message of a User's most frequently viewed station, and the number of taps in
   *     it
   */
  public String mostFrequentStationMessage() {
    // Makes the hash map mapping station to the number of taps this user
    HashMap<Station, Integer> frequentStationCount = new HashMap<>();
    for (Trip t : previousTrips) {
      for (Station s : t.getPriorStops()) {
        updateCount(frequentStationCount, s);
      }
      updateCount(frequentStationCount, t.getEndStation());
    }

    // Finds the maximum tap value for this station
    int maxTapValue = 0;
    Station maxTapped = null;
    for (Station s : frequentStationCount.keySet()) {
      if (frequentStationCount.get(s) > maxTapValue) {
        maxTapped = s;
        maxTapValue = frequentStationCount.get(s);
      }
    }

    // If there are no stations in this list
    if (maxTapped == null) {
      return "No stations tapped";
    } else {
      return "Most frequent station: "
          + System.lineSeparator()
          + maxTapped
          + " with "
          + maxTapValue
          + " taps";
    }
  }

  /**
   * @param frequentStationCount A hash map of stations to number of times tapped
   * @param s the station
   */
  private void updateCount(HashMap<Station, Integer> frequentStationCount, Station s) {
    if (frequentStationCount.containsKey(s)) {
      frequentStationCount.put(s, frequentStationCount.get(s) + 1);
    } else {
      frequentStationCount.put(s, 1);
    }
  }

  /**
   * A helper method simulating this User starting a new trip.
   *
   * @param card The card which this transit.system.User taps
   * @param station The station which this transit.system.User taps at
   */
  private void tapIn(Card card, Station station) throws TransitException {
    if (card.getBalance() <= 0) {
      throw new TransitException(); // If there aren't enough funds
    }

    // Check if this tap in is continuing a trip
    boolean foundContinuousTrip = false;
    Trip lastTrip = card.getLastTrip();
    if (lastTrip != null) {
      if (lastTrip.isContinuousTrip(station)) { // continue the last trip
        card.setCurrentTrip(lastTrip);
        lastTrip.continueTrip(station, permission);
        foundContinuousTrip = true;
      }
    }

    // If this trip is not continuing
    if (!foundContinuousTrip) {
      card.setCurrentTrip(new Trip(station, permission));
    }
    LogWriter.getInstance().logTapIn(this.toString(), station.toString(), card.getId());
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

    // Record various cardStatistics
    if (!trip.isValidTrip()) {
      LogWriter.getInstance().logInvalidTrip(this.toString(), station.toString(), card.getId());
      throw new TransitException();
    }
    LogWriter.getInstance()
        .logTapOut(this.toString(), station.toString(), card.getId(), trip.getFee());
  }

  /** Updates the cardStatistics associated with this user and the system */
  private void updateStatistic(Trip trip) {
    previousTrips.add(trip);
    cardStatistics.get("Expenditure").update(trip.getFee());
    Statistics.getSystemStatistics().get("SystemRevenue").update(trip.getFee());
    Statistics.getSystemStatistics().get("SystemTripLengh").update(max(trip.getTripLegLength(), 0));
  }
}
