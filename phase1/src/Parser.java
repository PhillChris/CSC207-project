import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Parser {

  /**
   * The current time of the simulation
   */
  static LocalDate currentTime;

  /**
   * The writer for the Parser to write to
   */
  static BufferedWriter writer;

  /**
   * @param bufferedWriter The writer for this Parser
   */
  static void setWriter(BufferedWriter bufferedWriter) {
    writer = bufferedWriter;
  }

  /**
   * @param message The message to be outputted through writer
   */
  public static void write(String message) {
    try {
      writer.write(message + "/n");
    } catch (IOException e) {
      System.out.println("File not found, create an events.txt and rerun the program");
    }
  }


  // TODO: wait for Andre before touching this. I have an idea to make this cleaner possibly.
  /**
   * Processes a card's tap request
   * @param cardInfo Info given from the user
   * @throws IOException
   */
  static void tap(List<String> cardInfo) {
    LocalDate time = parseTime(cardInfo.get(0));
    try {
      if (time != null) {
        CardHolder user = CardHolder.getCardholder(cardInfo.get(1));
        Card card = user.getCard(Integer.parseInt(cardInfo.get(2)));
        Station station =
            Route.getRoutes()
                .get(Integer.parseInt(cardInfo.get(3)))
                .findStation(Integer.parseInt(cardInfo.get(4)));

        card.tap(station, time);
      } else {
        write("Invalid Input given for Tap call");
      }

    } catch (InsufficientFundsException a) {
      a.getMessage();
    } catch (CardNotFoundException b) {
      write(b.getMessage());
    }
  }

  /**
   * Adds a new user to the Transit System
   * @param userInfo Info given from the user
   * @throws IOException
   */
  static void addUser(List<String> userInfo) {
    LocalDate time = parseTime(userInfo.get(0));
    if (time != null) {
      if (userInfo.get(2).equals("yes")) {
        AdminUser admin = new AdminUser(userInfo.get(1), userInfo.get(3));
      } else {
        CardHolder user = new CardHolder(userInfo.get(2), userInfo.get(3));
      }
    } else {
      write("Invalid Input given for addUser call");
    }
  }

  /**
   * Adds a new card for a user
   * @param cardInfo The info given from the user
   * @throws IOException
   */
  static void addCard(List<String> cardInfo) {
    LocalDate time = parseTime(cardInfo.get(0));
    try {
      if (time != null) {
        Card card = new Card();
        CardHolder.findUser(cardInfo.get(1)).addCard(card);
      } else {
        write("Invalid input for addCard call");
      }
    } catch (UserNotFoundException e) {
      e.getMessage();
    }
  }

  /**
   * Removes a user's card
   * @param cardInfo Information given from the user
   * @throws IOException
   */
  static void removeCard(List<String> cardInfo) {
    LocalDate time = parseTime(cardInfo.get(0));
    try {
      if (time != null) {
        CardHolder user = CardHolder.findUser(cardInfo.get(1));
        Card card = user.getCard(Integer.parseInt(cardInfo.get(2)));
        user.removeCard(card);
      } else {
        write("Invalid input for removeCard call");
      }
    } catch (UserNotFoundException a) {
      write(a.getMessage());
    } catch (CardNotFoundException b) {
      write(b.getMessage());
    }
  }

  /**
   * Deactivates a user's card
   * @param userInfo Information given from the user
   * @throws IOException
   */
  static void reportTheft(List<String> userInfo) {
    LocalDate time = parseTime(userInfo.get(0));
    try {
      if (time != null) {
        CardHolder user = CardHolder.findUser(userInfo.get(1));
        Card card = user.getCard(Integer.parseInt(userInfo.get(2)));
        user.suspendCard(card);
      } else {
        write("Invalid input for reportTheft call");
      }
    } catch (UserNotFoundException a) {
      write(a.getMessage());
    } catch (CardNotFoundException b) {
      write(b.getMessage());
    }
  }

  /**
   * Adds funds to a card
   * @param userInfo Information given from the user
   * @throws IOException
   */
  static void addFunds(List<String> userInfo) {
    LocalDate time = parseTime(userInfo.get(0));
    try {
      if (time != null) {
        CardHolder user = CardHolder.findUser(userInfo.get(1));
        Card card = user.getCard(Integer.parseInt(userInfo.get(2)));
        card.addBalance(Integer.parseInt(userInfo.get(3)));
      } else {
        write("Invalid input for AddFunds call");
      }
    } catch (UserNotFoundException a) {
      write(a.getMessage());
    } catch (CardNotFoundException b) {
      write(b.getMessage());
    }
  }

  /**
   * Ends a day in the simulation
   * @param emptyList Information given by the user
   * @throws IOException
   */
  static void endDay(List<String> emptyList) {
    AdminUser.dailyReports();
  }

  /**
   * Gets the monthly expenditures for the Transit System
   * @param userInfo Information given from the user
   * @throws IOException
   */
  static void monthlyExpenditue(List<String> userInfo) {
    try {
      CardHolder user = CardHolder.findUser(userInfo.get(0));
      write("Monthly expenditures:" + user.averageMonthly());
    } catch (UserNotFoundException e) {
      write(e.getMessage());
    }

  }

  /**
   * Reads, check and updates the time of the simulation
   * @param time The time inputted by the user
   * @return The updated time or null if invalid input was given
   */
  static LocalDate parseTime(String time) {
    try {
      String[] formatted = time.split("-");
      LocalDate newTime =
          LocalDate.of(
              Integer.parseInt(formatted[0]),
              Integer.parseInt(formatted[1]),
              Integer.parseInt(formatted[2]));

      if (currentTime == null) {
        currentTime = newTime;
        return currentTime;
      } else {
        if (currentTime.isAfter(newTime)) {
          throw new TimeException();
        } else {
          currentTime = newTime;
          return currentTime;
        }
      }
    } catch (TimeException t) {
      write(t.getMessage());
      return null;

    } catch (Exception e) {
      return null;
    }
  }
}
