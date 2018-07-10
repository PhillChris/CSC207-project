import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/** Parses all methods pertaining to cards in the transit system */
public class CardParser implements Parser {
  /**
   * Processes a card's tap request
   *
   * @param cardInfo Information given for the card from TransitReadWrite.read
   * @throws IOException
   */
  public void tap(List<String> cardInfo) {
    try {
      // Find the time, user, card and station from the given information
      LocalDateTime time = TransitTime.getTime(cardInfo.get(0));
      User user = User.findUser(cardInfo.get(1));
      Card card = user.getCard(Integer.parseInt(cardInfo.get(2)));
      String stationType = cardInfo.get(3);
      String stationName = cardInfo.get(4);
      Station station;
      // Check if the station is a Bus or Subway
      if (stationType.equals("BUS")) {
        station = BusStation.getBusStations().get(stationName);
      } else if (stationType.equals("SUBWAY")) {
        station = SubwayStation.getSubwayStations().get(stationName);
      } else {
        throw new InvalidStationTypeException();
      }

      // If no such station is found
      if (station == null) {
        throw new StationNotFoundException();
      }
      try {
        user.tap(card, station, time);
        // if this user has just started a trip
        if (card.getTripStarted()) {
          TransitReadWrite.write("User " + user.getEmail() + " tapped on at " + stationName);
        } else {
          TransitReadWrite.write("User " + user.getEmail() + " tapped off at " + stationName);
        }
      } catch (InvalidTripException t)  {
        // if the trip having just been processed crossed over multiple routes
        TransitReadWrite.write(t.getMessage());
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
      LocalDateTime time = TransitTime.getTime(cardInfo.get(0));
      User user = User.findUser(cardInfo.get(1));
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
      LocalDateTime time = TransitTime.getTime(cardInfo.get(0));
      User user = User.findUser(cardInfo.get(1));
      Card card = user.getCard(Integer.parseInt(cardInfo.get(2)));
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
      User user = User.findUser(cardInfo.get(0));
      Card card = user.getCard(Integer.parseInt(cardInfo.get(1)));
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
      LocalDateTime time = TransitTime.getTime(cardInfo.get(0));
      User user = User.findUser(cardInfo.get(1));
      Card card = user.getCard(Integer.parseInt(cardInfo.get(2)));
      card.addBalance(Integer.parseInt(cardInfo.get(3)) * 100);
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
      User user = User.findUser(cardInfo.get(0));
      Card card = user.getCard(Integer.parseInt(cardInfo.get(1)));
      TransitReadWrite.write(
          "This card the user card #"
              + card.getId()
              + " and has "
              + String.format("%.2f", card.getBalance() / 100.0)
              + " dollars remaining");
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
      User user = User.findUser(cardInfo.get(0));
      Card card = user.getCard(Integer.parseInt(cardInfo.get(1)));
      card.activateCard(card);
      TransitReadWrite.write(
          "Card reactivated for user " + cardInfo.get(0) + " card " + cardInfo.get(1));

    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }
}
