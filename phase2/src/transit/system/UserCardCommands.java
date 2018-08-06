package transit.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** The commands stores and processes all user-card related commands */
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

  /** Initialized a new instance of UserCardCommands */
  UserCardCommands(String permission) {
    this.cards = new HashMap<>();
    cardStatistics.put("Expenditure", new Statistics("Expenditure"));
    cardStatistics.put("Taps", new Statistics("Taps"));
    this.permission = permission;
  }

  /** @return The permission on this user */
  public String getPermission() {
    return this.permission;
  }

  /** @return The previous trips made by this user */
  public List<Trip> getPreviousTrips() {
    return previousTrips;
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
    LogWriter.getLogWriter()
        .logInfoMessage(User.class.getName(), "removeCard", "User removed card #" + card.getId());
  }

  /** Add a card to this transit.system.User's list of cards. */
  public void addCard() {
    this.cards.put(cardCounter, new Card(cardCounter));
    cardCounter++;
    LogWriter.getLogWriter()
        .logInfoMessage(
            User.class.getName(), "addCard", "User added new card with default balance");
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
      station.record("Tap In", 1);
      tapIn(card, station);
    } else {
      station.record("Tap Out", 1);
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
    // Record cardStatistics

    // Check if this transit.system.User is continuing a transit.system.Trip
    boolean foundContinuousTrip = false;
    Trip lastTrip = card.getLastTrip();
    if (lastTrip != null) {
      if (lastTrip.isContinuousTrip(station)) { // continue the last trip
        card.setCurrentTrip(lastTrip);
        lastTrip.continueTrip(station, permission);
        foundContinuousTrip = true;
      }
    }
    if (!foundContinuousTrip) {
      card.setCurrentTrip(new Trip(station, permission));
    }
    LogWriter.getLogWriter()
        .logInfoMessage(
            User.class.getName(),
            "tapIn",
            "User "
                + this.toString()
                + " tapped in at station "
                + station
                + " with card #"
                + card.getId());
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
      LogWriter.getLogWriter()
          .logWarningMessage(
              User.class.getName(),
              "tapOut",
              "User "
                  + this.toString()
                  + " tapped out improperly at station "
                  + station
                  + " with card #"
                  + card.getId()
                  + ", charged maximum possible fee.");
      throw new TransitException();
    }
    LogWriter.getLogWriter()
        .logInfoMessage(
            User.class.getName(),
            "tapOut",
            "User "
                + this.toString()
                + " tapped out at station "
                + station
                + " with card #"
                + card.getId()
                + ", charged $"
                + String.format("%.2f", trip.getFee() / 100.0));
  }

  /** Updates the cardStatistics associated with this user and the system */
  private void updateStatistic(Trip trip) {
    previousTrips.add(trip);
    cardStatistics.get("Expenditure").update(trip.getFee());
    Statistics.getSystemStatistics().get("SystemRevenue").update(trip.getFee());
    Statistics.getSystemStatistics()
        .get("SystemTripLengh")
        .update(Math.max(trip.getTripLegLength(), 0));
  }
}
