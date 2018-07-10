import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class CardParser {
  /**
   * Processes a card's tap request
   *
   * @param cardInfo Info given from the user
   * @throws IOException
   */
  static void tap(List<String> cardInfo) {
    // Get the time of the tap
    try {
      // Find the user, card and station from the given information
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

      if (station == null) {
        throw new StationNotFoundException();
      }
      try {
        user.tap(card, station, time);
        if (card.getTripStarted()) {
          TransitReadWrite.write("User " + user.getEmail() + " tapped on at " + stationName);
        } else {
          TransitReadWrite.write("User " + user.getEmail() + " tapped off at " + stationName);
        }
      } catch (InvalidTripException t) {
        TransitReadWrite.write(t.getMessage());
        TransitReadWrite.write("User " + user.getEmail() + " tapped off at " + stationName);
      }
    } catch (TransitException b) {
      TransitReadWrite.write(b.getMessage());
    }
  }

  /**
   * Adds a new card for a user
   *
   * @param cardInfo The info given from the user
   */
  static void addCard(List<String> cardInfo) {
    try {
      LocalDateTime time = TransitTime.getTime(cardInfo.get(0));
      User user = User.findUser(cardInfo.get(1));
      user.addCard();
    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }

  /**
   * Removes a user's card
   *
   * @param cardInfo Information given from the user
   */
  static void removeCard(List<String> cardInfo) {
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
   * Deactivates a user's card
   *
   * @param userInfo Information given from the user
   */
  static void reportTheft(List<String> userInfo) {
    try {
      User user = User.findUser(userInfo.get(0));
      Card card = user.getCard(Integer.parseInt(userInfo.get(1)));
      card.suspendCard();
      TransitReadWrite.write(
          "Theft reported for user " + userInfo.get(0) + " card " + userInfo.get(1));
    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }

  /**
   * Adds funds to a card
   *
   * @param userInfo Information given from the user
   */
  static void addFunds(List<String> userInfo) {
    try {
      LocalDateTime time = TransitTime.getTime(userInfo.get(0));
      User user = User.findUser(userInfo.get(1));
      Card card = user.getCard(Integer.parseInt(userInfo.get(2)));
      card.addBalance(Integer.parseInt(userInfo.get(3)) * 100);
    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }

  static void checkBalance(List<String> cardInfo) {
    try {
      User user = User.findUser(cardInfo.get(0));
      Card card = user.getCard(Integer.parseInt(cardInfo.get(1)));
      TransitReadWrite.write("This card has " + card.getBalance() / 100.0 + " dollars remaining");
    } catch (TransitException a) {
      TransitReadWrite.write(a.getMessage());
    }
  }

  static void activate(List<String> cardInfo) {
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
