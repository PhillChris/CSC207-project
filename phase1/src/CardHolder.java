import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

// TODO: Figure out how to calculate average monthly cost. Get clarification on "last three trips".

/** Represents a CardHolder in a transit system. */
public class CardHolder {
  private static HashMap<String, CardHolder> allUsers = new HashMap<>();
  private final String email;
  private List<Card> cards;
  private String name;
  private int cardCounter;
  HashMap<YearMonth, Integer > ExpenditureMonthly;
  HashMap<LocalDate, Integer > ExpenditureDaily;

  /**
   * Construct a new instance of CardHolder
   *
   * @param name the name of this CardHolder
   * @param email the email of this CardHolder
   */
  public CardHolder(String name, String email) {
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

  public String getEmail() {
    return this.email;
  }

  /**
   * Add a card to this CardHolder's list of cards.
   *
   */
  public void addCard() {
    Card card = new Card(cardCounter);
    cardCounter++;
    if (!this.cards.contains(card)) {
      this.cards.add(card);
    }
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

  public void changeName(String newName) {
    this.name = newName;
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
   * Suspend a card belonging to this CardHolder.
   *
   * @param card this CardHolder's card being suspended
   */
  public void suspendCard(Card card) {
    if (this.cards.contains(card)) {
      card.setActive(false);
    }
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

  public List<Card> getCards() {
    return this.cards;
  }

  public static CardHolder findUser(String email) throws UserNotFoundException{
    if (allUsers.containsKey(email)) {
      return allUsers.get(email);
    }
    else{
      throw new UserNotFoundException();
    }
  }

  public static HashMap<String, CardHolder> getAllUsers() {
    return allUsers;
  }

  public void addMonthlyFee(int monthlyFee){
    LocalDate date = TransitTime.getCurrentDate();
    ExpenditureMonthly.put(YearMonth.of(date.getYear(), date.getMonth()), monthlyFee);

  }

  public void addDailyFee(int dailyFee){
    LocalDate date = TransitTime.getCurrentDate();
    ExpenditureDaily.put(date, dailyFee);
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

}
