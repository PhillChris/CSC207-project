import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class Parser {
  /** The writer for the Parser to write to */
  static BufferedWriter writer;

  /** @param bufferedWriter The writer for this Parser */
  static void setWriter(BufferedWriter bufferedWriter) {
    writer = bufferedWriter;
  }

  /** @param message The message to be outputted through writer */
  public static void write(String message) {
    try {
      writer.write(message + System.lineSeparator());
    } catch (IOException e) {
      System.out.println("File not found, create an events.txt and rerun the program");
    }
  }

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
      CardHolder user = CardHolder.getCardholder(cardInfo.get(1));
      Card card = user.getCard(Integer.parseInt(cardInfo.get(2)));
      String stationType = cardInfo.get(3);
      String stationName = cardInfo.get(4);
      Station station;
      // Check if the station is a Bus or Subway
      if (stationType.equals("BUS")) {
        station = BusStation.getStations().get(stationName);
      } else {
        station = SubwayStation.getStations().get(stationName);
      }

      if (station == null) {
        throw new StationNotFoundException();
      }
      try {
        card.tap(station, time);
        if (card.tripStarted()) {
          write("User " + user.getEmail() + " tapped on at " + stationName);
        } else {
          write("User " + user.getEmail() + " tapped off at " + stationName);
        }
      } catch (InvalidTripException t) {
        write(t.getMessage());
        write("User " + user.getEmail() + " tapped off at " + stationName);
      }
    } catch (TransitException b) {
      write(b.getMessage());
    }
  }

  /**
   * Adds a new user to the Transit System
   *
   * @param userInfo Info given from the user
   */
  static void addUser(List<String> userInfo) {
    try {
      LocalDateTime time = TransitTime.getTime(userInfo.get(0));
    } catch (TransitException a) {
      a.getMessage();
    }

    if (userInfo.get(2).equals("yes")) {
      AdminUser admin = new AdminUser(userInfo.get(2), userInfo.get(3));
    } else {
      CardHolder user = new CardHolder(userInfo.get(2), userInfo.get(3));
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
      CardHolder user = CardHolder.findUser(cardInfo.get(1));
      user.addCard();
    } catch (TransitException a) {
      write(a.getMessage());
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
      CardHolder user = CardHolder.findUser(cardInfo.get(1));
      Card card = user.getCard(Integer.parseInt(cardInfo.get(2)));
      user.removeCard(card);
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  /**
   * Deactivates a user's card
   *
   * @param userInfo Information given from the user
   */
  static void reportTheft(List<String> userInfo) {
    try {
      LocalDateTime time = TransitTime.getTime(userInfo.get(0));
      CardHolder user = CardHolder.findUser(userInfo.get(1));
      Card card = user.getCard(Integer.parseInt(userInfo.get(2)));
      user.suspendCard(card);
    } catch (TransitException a) {
      write(a.getMessage());
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
      CardHolder user = CardHolder.findUser(userInfo.get(1));
      Card card = user.getCard(Integer.parseInt(userInfo.get(2)));
      card.addBalance(Integer.parseInt(userInfo.get(3)));
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  /**
   * Gets a user's average monthly expenditures for the Transit System
   *
   * @param userInfo Information given from the user
   */
  static void monthlyExpenditure(List<String> userInfo) {
    try {
      CardHolder user = CardHolder.findUser(userInfo.get(0));
      String averageMonthly = String.format("&.2f", user.getAvgMonthly());
      write("User " + user.getEmail() +"'s average monthly expenditures: " + averageMonthly);
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  static void changeName(List<String> userInfo) {
    try {
      CardHolder user = CardHolder.findUser(userInfo.get(0));
      String newName = userInfo.get(1);
      user.changeName(newName);
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }

  static void checkBalance(List<String> cardInfo) {
    try {
      CardHolder user = CardHolder.findUser(cardInfo.get(0));
      Card card = user.getCard(Integer.parseInt(cardInfo.get(1)));
      write("This card has " + card.getBalance() / 100.0 + " dollars remaining");
    } catch (TransitException a) {
      write(a.getMessage());
    }
  }
}
