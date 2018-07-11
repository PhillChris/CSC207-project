import java.io.BufferedWriter;
import java.io.IOException;
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
    buildCardHashMap();
  }

  /**
   * Processes an add card request.
   *
   * @param cardInfo Information given for the card from TransitReader.run
   */
  public void add(List<String> cardInfo) {
    try {
      this.checkInput(cardInfo, 1);
      User user = findUser(cardInfo.get(0));
      user.addCard();
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  /**
   * Processes a remove card request.
   *
   * @param cardInfo Information given for the card from TransitReader.run
   */
  public void remove(List<String> cardInfo) {
    try {
      this.checkInput(cardInfo, 2);
      User user = findUser(cardInfo.get(0));
      Card card = findCard(user, Integer.parseInt(cardInfo.get(1)));
      user.removeCard(card);
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  /**
   * Creates a balance report for a given card.
   *
   * @param cardInfo Information given for the card from TransitReader.run
   */
  public void report(List<String> cardInfo) {
    try {
      this.checkInput(cardInfo, 2);
      User user = findUser(cardInfo.get(0));
      Card card = findCard(user, Integer.parseInt(cardInfo.get(1)));
      write(user.getEmail() + "'s " + card.toString());
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  /**
   * Processes a card's tap request.
   *
   * @param cardInfo Information given for the card from TransitReader.run
   * @throws IOException
   */
  public void tap(List<String> cardInfo) {
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
        write("User " + user.getEmail() + " tapped on at " + station);
      } else {
        write("User " + user.getEmail() + " tapped off at " + station);
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
  public void reportTheft(List<String> cardInfo) {
    try {
      this.checkInput(cardInfo, 2);
      User user = findUser(cardInfo.get(0)); // passing user email
      Card card = findCard(user, Integer.parseInt(cardInfo.get(1))); // passing cardID.
      card.suspendCard();
      write(
          "Theft reported for user " + cardInfo.get(0) + " card " + cardInfo.get(1));
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  /**
   * Processes a card activation request.
   *
   * @param cardInfo Information given for the card from TransitReader.run
   */
  public void activate(List<String> cardInfo) {
    try {
      this.checkInput(cardInfo, 2);
      User user = findUser(cardInfo.get(0));
      Card card = findCard(user, Integer.parseInt(cardInfo.get(1)));
      card.activateCard(card);
      write(
          "Card reactivated for user " + cardInfo.get(0) + " card " + cardInfo.get(1));

    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  /**
   * Processes an add funds request for a given card.
   *
   * @param cardInfo Information given for the card from TransitReader.run
   */
  public void addFunds(List<String> cardInfo) {
    try {
      this.checkInput(cardInfo, 3);
      Integer amountAdd = Integer.parseInt(cardInfo.get(2)) * 100;
      User user = findUser(cardInfo.get(0));
      Card card = findCard(user, Integer.parseInt(cardInfo.get(1)));
      card.addBalance(amountAdd);
      write(String.format("Added: $%s to %s, %s", String.format("%.2f", amountAdd.doubleValue() / 100),  user, card)
      );
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  /** A helper method to add card-specific methods to the command hash map */
  private void buildCardHashMap() {
    keyWords.put(
        "ADDCARD",
        (cardInfo) -> {
          this.add(cardInfo);
          return null;
        });
    keyWords.put(
        "REMOVECARD",
        (cardInfo) -> {
          this.remove(cardInfo);
          return null;
        });
    keyWords.put(
        "CHECKBALANCE",
        (cardInfo) -> {
          this.report(cardInfo);
          return null;
        });
    keyWords.put(
        "TAP",
        (cardInfo) -> {
          this.tap(cardInfo);
          return null;
        });
    keyWords.put(
        "REPORTTHEFT",
        (userInfo) -> {
          this.reportTheft(userInfo);
          return null;
        });
    keyWords.put(
        "ACTIVATECARD",
        (cardInfo) -> {
          this.activate(cardInfo);
          return null;
        });
    keyWords.put(
        "ADDFUNDS",
        (userInfo) -> {
          this.addFunds(userInfo);
          return null;
        });
  }
}
