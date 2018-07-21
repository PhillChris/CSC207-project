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
   * @param cardInfo Information given for the card from TransitReader.run
   */
  void remove(List<String> cardInfo) {
    try {
      this.checkInput(cardInfo, 2);
      User user = findUser(cardInfo.get(0));
      Card card = findCard(user, Integer.parseInt(cardInfo.get(1)));
      user.removeCard(card);
      write("Removed card from user " + user);
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  /**
   * Creates a balance report for a given card.
   *
   * @param cardInfo Information given for the card from TransitReader.run
   */
  void report(List<String> cardInfo) {
    try {
      this.checkInput(cardInfo, 2);
      User user = findUser(cardInfo.get(0));
      Card card = findCard(user, Integer.parseInt(cardInfo.get(1)));
      write(user + "'s " + card.toString());
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  /**
   * Processes a card's tap request.
   *
   * @param cardInfo Information given for the card from TransitReader.run
   */
  void tap(List<String> cardInfo) {
    try {
      this.checkInput(cardInfo, 5);
      // Find the time, user, card and station from the given information
      LocalDateTime time = TransitTime.getTime(cardInfo.get(0));
      User user = findUser(cardInfo.get(1));
      Card card = findCard(user, Integer.parseInt(cardInfo.get(2)));
      Station station = findStation(cardInfo.get(3), cardInfo.get(4));

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
   * @param cardInfo Information given for the card from TransitReader.run
   */
  void reportTheft(List<String> cardInfo) {
    try {
      this.checkInput(cardInfo, 2);
      User user = findUser(cardInfo.get(0)); // passing user email
      Card card = findCard(user, Integer.parseInt(cardInfo.get(1))); // passing cardID.
      card.suspendCard();
      write(String.format("Theft reported for user %s card %s", cardInfo.get(0), cardInfo.get(1)));
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  /**
   * Processes a card activation request.
   *
   * @param cardInfo Information given for the card from TransitReader.run
   */
  void activate(List<String> cardInfo) {
    try {
      this.checkInput(cardInfo, 2);
      User user = findUser(cardInfo.get(0));
      Card card = findCard(user, Integer.parseInt(cardInfo.get(1)));
      card.activateCard(card);
      write(
          String.format("Card reactivated for user %s card %s", cardInfo.get(0), cardInfo.get(1)));

    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  /**
   * Processes an add funds request for a given card.
   *
   * @param cardInfo Information given for the card from TransitReader.run
   */
  void addFunds(List<String> cardInfo) {
    try {
      this.checkInput(cardInfo, 3);
      Integer amountAdd = Integer.parseInt(cardInfo.get(2)) * 100;
      User user = findUser(cardInfo.get(0));
      Card card = findCard(user, Integer.parseInt(cardInfo.get(1)));
      card.addBalance(amountAdd);
      write(
          String.format(
              "Added: $%s to %s, %s",
              String.format("%.2f", amountAdd.doubleValue() / 100), user, card));
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }
}
