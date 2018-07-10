import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

// TODO: Figure out how to calculate average monthly cost. Get clarification on "last three trips".

/** Represents a CardHolder in a transit system. */
public class CardHolder {
  private static HashMap<String, CardHolder> allUsers = new HashMap<>();
  private final String email;
  HashMap<YearMonth, Integer> ExpenditureMonthly;
  HashMap<LocalDate, Integer> ExpenditureDaily;
  private List<Card> cards;
  private String name;
  private int cardCounter;

  /**
   * Construct a new instance of CardHolder
   *
   * @param name the name of this CardHolder
   * @param email the email of this CardHolder
   */
  public CardHolder(String name, String email) throws EmailInUseException {
    if (allUsers.keySet().contains(email)) {
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

  public static CardHolder getCardholder(String email) {
    return allUsers.get(email);
  }

  public static CardHolder findUser(String email) throws UserNotFoundException {
    if (allUsers.containsKey(email)) {
      return allUsers.get(email);
    } else {
      throw new UserNotFoundException();
    }
  }

  public static HashMap<String, CardHolder> getAllUsers() {
    return allUsers;
  }

  public String getEmail() {
    return this.email;
  }

  /**
   * Retrieves a card object given the number of this User
   *
   * @param cardId The user-specific id of this card
   * @return
   * @throws CardNotFoundException
   */
  public Card getCard(int cardId) throws CardNotFoundException {
    for (Card tempCard : this.cards) {
      if (tempCard.getId() == cardId) {
        return tempCard;
      }
    }
    throw new CardNotFoundException();
  }

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
    List<Trip> allTrips = new ArrayList<Trip>();
    // Gather all trips across all cards
    for (Card card : this.cards) {
      allTrips.addAll(card.getAllTrips());
    }
    if (allTrips.size() < 4) {
      return allTrips;
    } else {
      // Sort in descending order
      allTrips.sort(Comparator.comparing(Trip::getTimeStarted).reversed());
    }
    // Return latest three trips.
    return allTrips.subList(allTrips.size() - 3, allTrips.size());
  }

  public void changeName(String newName) {
    this.name = newName;
  }

  /** Add a card to this CardHolder's list of cards. */
  public void addCard() {
    Card card = new Card(cardCounter);
    cardCounter++;
    if (!this.cards.contains(card)) {
      this.cards.add(card);
    }
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

  public void tap(Card card, Station station, LocalDateTime timeTapped)
      throws InsufficientFundsException, CardSuspendedException, InvalidTripException {

    LocalDate date = TransitTime.getCurrentDate();
    YearMonth month = YearMonth.of(date.getYear(), date.getMonth());

    if (!card.isActive) {
      throw new CardSuspendedException();
    }
    if (card.currentTrip == null) {
      card.tapIn(station, timeTapped);
    } else {
      card.tapOut(station, timeTapped);
      Trip lastTrip = card.getLastTrip();
      updateSpendingHistory(card);
      CostCalculator.updateSystemRevenue(lastTrip.getFee());
    }
  }

  /**
   * Adds the fee of the card's last Trip to the user's spending history
   *
   * @param card The card whose trip fee to add
   */
  public void updateSpendingHistory(Card card) {
    LocalDate date = TransitTime.getCurrentDate();
    YearMonth month = YearMonth.of(date.getYear(), date.getMonth());
    Trip lastTrip = card.getLastTrip();
    if (ExpenditureMonthly.containsKey(month)) {
      ExpenditureMonthly.put(month, ExpenditureMonthly.get(month) + lastTrip.getFee());
    } else {
      ExpenditureMonthly.put(month, lastTrip.getFee());
    }
    if (ExpenditureDaily.containsKey(date)) {
      ExpenditureDaily.put(date, ExpenditureDaily.get(date) + lastTrip.getFee());
    } else {
      ExpenditureDaily.put(date, lastTrip.getFee());
    }
  }

  private void tapIn(Card card, Station station, LocalDateTime timeTapped)
      throws InsufficientFundsException {
    if (card.getBalance() <= 0) throw new InsufficientFundsException();

    boolean foundContinuousTrip = false; // a flag, just to avoid repetitive code
    Trip lastTrip = card.getLastTrip();
    if (lastTrip != null) { // check this to avoid index errors
      // check that tapping into this station would be a continuous trip from last trip
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

  private void tapOut(Card card, Station station, LocalDateTime timeTapped)
      throws InvalidTripException {
    Trip trip = card.getCurrentTrip();
    trip.endTrip(station, timeTapped);
    card.subtractBalance(trip.getFee());
    boolean validTrip = trip.isValidTrip();
    card.getAllTrips().add(trip);
    card.setCurrentTrip(null);
    if (!validTrip) {
      throw new InvalidTripException();
    }
  }
}
