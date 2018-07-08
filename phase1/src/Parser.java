import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class Parser {
  /** The current year of this simulation*/
  static int currentYear;

  /** The current month of this simulation*/
  static Month currentMonth;

  /** The current day of this simulation */
  static int currentDay;

  /** The current time of the simulation */
  static LocalDateTime currentTime;

  /** The writer for the Parser to write to */
  static BufferedWriter writer;

  /** @param bufferedWriter The writer for this Parser */
  static void setWriter(BufferedWriter bufferedWriter) {
    writer = bufferedWriter;
  }

  /** @param message The message to be outputted through writer */
  public static void write(String message) {
    try {
      writer.write(message + "/n");
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

    LocalDateTime time = parseTime(cardInfo.get(0));
    try {
      CardHolder user = CardHolder.getCardholder(cardInfo.get(1));
      Card card = user.getCard(Integer.parseInt(cardInfo.get(2)));
      String stationType = cardInfo.get(3);
      String stationName = cardInfo.get(4);
      Station station;
      if (stationType.equals("Bus")) {
        station = BusStation.getStations().get(stationName);
      } else {
        station = SubwayStation.getStations().get(stationName);
      }
      card.tap(station, time);
    } catch (InsufficientFundsException a) {
      a.getMessage();
    } catch (CardNotFoundException b) {
      write(b.getMessage());
    }
  }

  /**
   * Adds a new user to the Transit System
   *
   * @param userInfo Info given from the user
   */
  static void addUser(List<String> userInfo) {
    LocalDateTime time = parseTime(userInfo.get(0));
    if (userInfo.get(2).equals("yes")) {
      AdminUser admin = new AdminUser(userInfo.get(1), userInfo.get(3));
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
    LocalDateTime time = parseTime(cardInfo.get(0));
    try {
      Card card = new Card();
      CardHolder.findUser(cardInfo.get(1)).addCard(card);
    } catch (UserNotFoundException e) {
      e.getMessage();
    }
  }

  /**
   * Removes a user's card
   *
   * @param cardInfo Information given from the user
   */
  static void removeCard(List<String> cardInfo) {
    LocalDateTime time = parseTime(cardInfo.get(0));
    try {
      CardHolder user = CardHolder.findUser(cardInfo.get(1));
      Card card = user.getCard(Integer.parseInt(cardInfo.get(2)));
      user.removeCard(card);
    } catch (UserNotFoundException a) {
      write(a.getMessage());
    } catch (CardNotFoundException b) {
      write(b.getMessage());
    }
  }

  /**
   * Deactivates a user's card
   *
   * @param userInfo Information given from the user
   */
  static void reportTheft(List<String> userInfo) {
    LocalDateTime time = parseTime(userInfo.get(0));
    try {
      CardHolder user = CardHolder.findUser(userInfo.get(1));
      Card card = user.getCard(Integer.parseInt(userInfo.get(2)));
      user.suspendCard(card);
    } catch (UserNotFoundException a) {
      write(a.getMessage());
    } catch (CardNotFoundException b) {
      write(b.getMessage());
    }
  }

  /**
   * Adds funds to a card
   *
   * @param userInfo Information given from the user
   */
  static void addFunds(List<String> userInfo) {
    LocalDateTime time = parseTime(userInfo.get(0));
    try {
      CardHolder user = CardHolder.findUser(userInfo.get(1));
      Card card = user.getCard(Integer.parseInt(userInfo.get(2)));
      card.addBalance(Integer.parseInt(userInfo.get(3)));
    } catch (UserNotFoundException a) {
      write(a.getMessage());
    } catch (CardNotFoundException b) {
      write(b.getMessage());
    }
  }

  /**
   * Ends a day in the simulation
   *
   * @param emptyList Information given by the user
   */
  static void endDay(List<String> emptyList) {
    AdminUser.dailyReports();
    if (currentDay < currentMonth.length(false)) {
      currentDay++;
    } else {
      currentDay = 1;
      if (currentMonth.getValue() < 12) {
        currentMonth = Month.of(currentMonth.getValue() + 1);
      } else {
        currentMonth = Month.of(1);
        currentYear++;
      }
    }
  }

  /**
   * Gets the monthly expenditures for the Transit System
   *
   * @param userInfo Information given from the user
   */
  static void monthlyExpenditure(List<String> userInfo) {
    try {
      CardHolder user = CardHolder.findUser(userInfo.get(0));
      write("Monthly expenditures:" + user.averageMonthly());
    } catch (UserNotFoundException e) {
      write(e.getMessage());
    }
  }

  /**
   * Initializes the date in the transit system. Sets system time to midnight of the first date by
   * default.
   *
   * @param initialDate the initial date of the transit system simulation
   */
  static void initDate(String initialDate) {
    String[] formatted = initialDate.split("-");
    currentYear = Integer.parseInt(formatted[0]);
    currentMonth = Month.of(Integer.parseInt(formatted[1]));
    currentDay = Integer.parseInt(formatted[2]);
    currentTime =
        LocalDateTime.of(
            currentYear,
            currentMonth,
            currentDay,
            0,
            0);
  }

  /**
   * Reads, check and updates the time of the simulation
   *
   * @param time The time inputted by the user
   * @return The updated time or null if invalid input was given
   */
  static LocalDateTime parseTime(String time) {
    try {
      String[] formatted = time.split(":");
      LocalDateTime newTime =
          LocalDateTime.of(
              currentYear,
              currentMonth,
              currentDay,
              Integer.parseInt(formatted[0]),
              Integer.parseInt(formatted[1]));

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
