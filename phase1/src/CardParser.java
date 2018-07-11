import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/** Parses all methods pertaining to cards in the transit system */
public class CardParser extends ObjectParser {
  public CardParser(BufferedWriter writer) {
    super(writer);
    buildHashMap();
  }
  /**
   * Processes a card's tap request
   *
   * @param cardInfo Information given for the card from TransitReadWrite.read
   * @throws IOException
   */
  public void tap(List<String> cardInfo) {
    try {
      ObjectParser.checkInput(cardInfo, 5);
      // Find the time, user, card and station from the given information
      LocalDateTime time = TransitTime.getTime(cardInfo.get(0));
      User user = User.findUser(cardInfo.get(1));
      Card card = user.getCard(Integer.parseInt(cardInfo.get(2)));
      String stationType = cardInfo.get(3);
      String stationName = cardInfo.get(4);
      Station station;
      StationFactory fact;
      // Check if the station is a Bus or Subway
      if (stationType.equals("BUS")) {
        fact = new BusFactory();
        fact.newStation("");
        station = fact.newStation("").getStations().get(stationName);
      } else if (stationType.equals("SUBWAY")) {
        fact = new SubwayFactory();
        station = fact.newStation("").getStations().get(stationName);
      } else {
        throw new InvalidStationTypeException();
      }

      // If no such station is found
      if (station == null) {
        throw new StationNotFoundException();
      }
      user.tap(card, station, time);
      // if this user has just started a trip
      if (card.getTripStarted()) {
        TransitReadWrite.write("User " + user.getEmail() + " tapped on at " + stationName);
      } else {
        TransitReadWrite.write("User " + user.getEmail() + " tapped off at " + stationName);
      }
    } catch (TransitException b) {
      TransitReadWrite.write(b.getMessage());
    }
  }

  /**
   * Processes an add card request
   *
   * @param cardInfo Information given for the card from TransitReadWrite.read
   */
  public void add(List<String> cardInfo) {
    try {
      ObjectParser.checkInput(cardInfo, 1);
      User user = User.findUser(cardInfo.get(0));
      user.addCard();
    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }

  /**
   * Processes a remove card request
   *
   * @param cardInfo Information given for the card from TransitReadWrite.read
   */
  public void remove(List<String> cardInfo) {
    try {
      ObjectParser.checkInput(cardInfo, 2);
      User user = User.findUser(cardInfo.get(0));
      Card card = user.getCard(Integer.parseInt(cardInfo.get(1)));
      user.removeCard(card);
    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }

  /**
   * Process a theft report for a given card
   *
   * @param cardInfo Information given for the card from TransitReadWrite.read
   */
  public void reportTheft(List<String> cardInfo) {
    try {
      ObjectParser.checkInput(cardInfo, 2);
      User user = User.findUser(cardInfo.get(0)); // passing user email
      Card card = user.getCard(Integer.parseInt(cardInfo.get(1))); // passing cardID.
      card.suspendCard();
      TransitReadWrite.write(
          "Theft reported for user " + cardInfo.get(0) + " card " + cardInfo.get(1));
    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }

  /**
   * Processes an add funds request for a given card
   *
   * @param cardInfo Information given for the card from TransitReadWrite.read
   */
  public void addFunds(List<String> cardInfo) {
    try {
      ObjectParser.checkInput(cardInfo, 3);
      User user = User.findUser(cardInfo.get(0));
      Card card = user.getCard(Integer.parseInt(cardInfo.get(1)));
      card.addBalance(Integer.parseInt(cardInfo.get(2)) * 100);
    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }

  /**
   * Creates a balance report for a given card
   *
   * @param cardInfo Information given for the card from TransitReadWrite.read
   */
  public void report(List<String> cardInfo) {
    try {
      ObjectParser.checkInput(cardInfo, 2);
      User user = User.findUser(cardInfo.get(0));
      Card card = user.getCard(Integer.parseInt(cardInfo.get(1)));
      TransitReadWrite.write(user.getEmail() + "'s " + card.toString());
    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }

  /**
   * Processes a card activation request
   *
   * @param cardInfo Information given for the card from TransitReadWrite.read
   */
  public void activate(List<String> cardInfo) {
    try {
      ObjectParser.checkInput(cardInfo, 2);
      User user = User.findUser(cardInfo.get(0));
      Card card = user.getCard(Integer.parseInt(cardInfo.get(1)));
      card.activateCard(card);
      TransitReadWrite.write(
          "Card reactivated for user " + cardInfo.get(0) + " card " + cardInfo.get(1));

    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }

  private void buildHashMap() {
    keyWords.put(
        "TAP",
        (cardInfo) -> {
          this.tap(cardInfo);
          return null;
        });
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
        "REPORTTHEFT",
        (userInfo) -> {
          this.reportTheft(userInfo);
          return null;
        });
    keyWords.put(
        "ADDFUNDS",
        (userInfo) -> {
          this.addFunds(userInfo);
          return null;
        });
    keyWords.put(
        "CHECKBALANCE",
        (cardInfo) -> {
          this.report(cardInfo);
          return null;
        });
    keyWords.put(
        "ACTIVATECARD",
        (cardInfo) -> {
          this.activate(cardInfo);
          return null;
        });
  }
}
