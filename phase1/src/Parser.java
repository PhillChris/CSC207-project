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
        CardHolder user = CardHolder.getCardholder(cardInfo.get(2));
        Card card = user.getCard(Integer.parseInt(cardInfo.get(3)));
        Station station =
            Route.getRoutes()
                .get(Integer.parseInt(cardInfo.get(4)))
                .findStation(Integer.parseInt(cardInfo.get(5)));

        card.tap(station, time);
      } else {
        throw new TapException();
      }

    } catch (InsufficientFundsException a) {
      a.getMessage();
    } catch (CardNotFoundException b) {
      write(b.getMessage());
    } catch (TapException c) {
      write(c.getMessage());
    }
  }

  static void addUser(List<String> userInfo) throws IOException {
    LocalDate time = parseTime(userInfo.get(0));
    if (time != null) {
      if (userInfo.get(2).equals("yes")) {
        AdminUser admin = new AdminUser(userInfo.get(3), userInfo.get(4));
      } else {
        CardHolder user = new CardHolder(userInfo.get(3), userInfo.get(4));
      }
    }
  }

  static void addCard(List<String> cardInfo) throws IOException {
    LocalDate time = parseTime(cardInfo.get(0));
    try {
      Card card = new Card();
      CardHolder.findUser(cardInfo.get(2)).addCard(card);
    } catch (UserNotFoundException e) {
      e.getMessage();
    }
  }

  static void removeCard(List<String> cardInfo) {}

  static void reportTheft(List<String> userInfo) {}

  static void addFunds(List<String> userInfo) {}

  static void endDay(List<String> emptyList) {}

  static void monthlyExpenditue(List<String> userInfo) {}

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
