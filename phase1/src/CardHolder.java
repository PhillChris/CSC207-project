import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

// TODO: Figure out how to calculate average monthly cost. Get clarification on "last three trips".

/** Represents a CardHolder in a transit system. */
public class CardHolder {
  private final String email;
  private List<Card> cards;
  private String name;

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
  }

  public static int sumArrayList(List<Integer> array) {
    int sum = 0;
    for (Integer i : array) {
      sum += i;
    }
    return sum;
  }

  public String getEmail() {
    return this.email;
  }

  /**
   * Add a card to this CardHolder's list of cards.
   *
   * @param card the card being added
   */
  public void addCard(Card card) {
    if (!this.cards.contains(card)) {
      this.cards.add(card);
    }
  }

  public Card getCard(int cardId) throws CardNotFoundException {
    for (Card tempCard : this.cards) {
      if (tempCard.getId() == cardId) {
        return tempCard;
      } else {
        throw new CardNotFoundException();
      }
    }
    return null;
  }

  /**
   * Remove a card from this CardHolder's list of cards.
   *
   * @param card
   */
  public void removeCard(Card card) {}

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

  public float averageMonthly() {
    // TODO: Use Strategy Design pattern instead?
    // Map a list of costs of all trips taken during a month to the month
    HashMap<YearMonth, List<Integer>> tripsByMonth = new HashMap<YearMonth, List<Integer>>();
    for (Card card : cards) {
      for (Trip trip : card.getAllTrips()) {
        LocalDate tripStart = trip.getTimeStarted();
        YearMonth tripMonthYear = YearMonth.of(tripStart.getYear(), tripStart.getMonth());
        if (!tripsByMonth.containsKey(tripMonthYear)) {
          tripsByMonth.put(tripMonthYear, new ArrayList<Integer>());
        }
        tripsByMonth.get(tripMonthYear).add(trip.getFee());
      }
    }

    // Get total spent on trips per month
    HashMap<YearMonth, Integer> monthlyTotal = new HashMap<YearMonth, Integer>();
    for (Map.Entry<YearMonth, List<Integer>> entry : tripsByMonth.entrySet()) {
      YearMonth month = entry.getKey();
      List<Integer> costs = entry.getValue();
      Integer totalCost = CardHolder.sumArrayList(costs);
      monthlyTotal.put(month, totalCost);
    }

    // Calculate average
    int total = 0;
    for (int cost : monthlyTotal.values()) {
      total += cost;
    }

    int numMonths = monthlyTotal.keySet().size();

    return total / numMonths;
  }
}
