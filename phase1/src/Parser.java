import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Parser {

  static LocalDate currentTime;

  static BufferedWriter writer;

  static void setWriter(BufferedWriter bufferedWriter) {
    writer = bufferedWriter;
  }

  public static void write(String message) throws IOException {
    writer.write(message + "/n");
  }

  /**
   * Taps a card
   *
   * @param cardInfo The information of the card given by the user
   */
  static void tap(List<String> cardInfo) throws IOException {
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

  static void addUser(List<String> userInfo) throws IOException {
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

  static void addCard(List<String> cardInfo) throws IOException {
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

  static void removeCard(List<String> cardInfo) throws IOException {
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

  static void reportTheft(List<String> userInfo) throws IOException {
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

  static void addFunds(List<String> userInfo) throws IOException {
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

  static void endDay(List<String> emptyList) {}

  static void monthlyExpenditue(List<String> userInfo) throws IOException {
    try {
      CardHolder user = CardHolder.findUser(userInfo.get(0));
      write("Monthly expenditures:" + user.averageMonthly());
    } catch (UserNotFoundException e) {
      write(e.getMessage())
    }

  }

  static LocalDate parseTime(String time) throws IOException {
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
