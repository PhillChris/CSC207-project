import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
}
