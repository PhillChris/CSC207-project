import java.io.BufferedWriter;
import java.time.LocalDateTime;
import java.util.List;

/** Parses all methods pertaining to cards in the transit system */
public class CardParser extends ObjectParser {

  /**
   * Constructs a new instance of CardParser.
   *
   * @param writer The file writer being used to write the results of this program to output.txt
   */
  public CardParser(BufferedWriter writer) {
    super(writer);
  }

  /**
   * Processes an add card request.
   *
   * @param user The user whose card we are adding to
   */
  void add(User user) {
    user.addCard();
    write("Added card to user " + user);
  }

  /**
   * Processes a remove card request.
   *
   * @param user The user who's card we are removing
   * @param card The card which is being removed
   */
  void remove(User user, Card card) {
    user.removeCard(card);
    write("Removed card from user " + user);
  }

  /**
   * Creates a balance report for a given card.
   *
   * @param user Information given for the card from TransitReader.run
   */
  void report(User user, Card card) {
    write(user + "'s " + card.toString());
  }

  /**
   * Processes a card's tap request.
   *
   */
  void tap(User user, Card card, String timeString, String stationName, String routeName) {
    try {
      // Find the time, user, card and station from the given information
      LocalDateTime time = TransitTime.getTime(timeString);
      Station station = findStation(stationName, routeName);

      // If no such station is found
      if (station == null) {
        throw new StationNotFoundException();
      }
      user.tap(card, station, time);
      // if this user has just started a trip
      if (card.getTripStarted()) {
        write(String.format("User %s tapped on at %s", user, station));
      } else {
        write(String.format("User %s tapped off at %s", user, station));
      }
    } catch (TransitException b) {
      write(b.getMessage());
    }
  }

  /**
   * Process a theft report for a given card.
   *
   * @param card Information given for the card from TransitReader.run
   */
  void reportTheft(Card card) {
    card.suspendCard();
  }

  /**
   * Processes a card activation request.
   *
   * @param card Information given for the card from TransitReader.run
   */
  void activate(Card card) {
    card.activateCard(card);
  }

  /**
   * Processes an add funds request for a given card.
   *
   * @param card Information given for the card from TransitReader.run
   */
  void addFunds(User user, Card card, int centAdd) {
      Integer amountAdd = centAdd * 100;
      card.addBalance(amountAdd);
      write(
          String.format(
              "Added: $%s to %s, %s",
              String.format("%.2f", amountAdd.doubleValue() / 100), user, card));
  }
}
