import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Parser {

  static LocalDate currentTime;

  static BufferedWriter writer;

  static void setWriter(BufferedWriter bufferedWriter){
    writer = bufferedWriter;
  }

  public static void write(String message) throws IOException {
    writer.write(message + "/n");
  }



  /**
   * Taps a card
   * @param cardInfo The information of the card given by the user
   */
  static void tap(List<String> cardInfo) throws IOException {
    try{
    LocalDate time = parseTime(cardInfo.get(0));
    if (checkTimeOrder(time)){
      CardHolder user = CardHolder.getCardholder(cardInfo.get(2));
      Card card = user.getCard(Integer.parseInt(cardInfo.get(3)));
      card.tap(cardInfo.get(4), cardInfo.get(0));


    }
    else{
      throw new TapException();
    }
    }
    catch (CardNotFoundException c){
      write(c.getMessage());
    }
    catch (TapException e) {
      write(e.getMessage());

    }


    }






  static void addUser(List<String> userInfo) {}

  static void addCard(List<String> cardInfo) {}

  static void removeCard(List<String> cardInfo) {}

  static void reportTheft(List<String> userInfo) {}

  static void addFunds(List<String> userInfo) {}

  static void endDay(List<String> emptyList) {}

  static void monthlyExpenditue(List<String> userInfo) {}

  static LocalDate parseTime(String time) throws IOException {
    try{
    String[] formatted = time.split("-");
    LocalDate newTime = LocalDate.of(Integer.parseInt(formatted[0]), Integer.parseInt(formatted[1]),
            Integer.parseInt(formatted[2]));

    if (currentTime == null){
      currentTime = newTime;
      return currentTime;
    }
    else{
      if (currentTime.isAfter(newTime)){
        throw new TimeException();
      }
      else{
        currentTime = newTime;
        return currentTime;
      }
    }
  }
    catch (TimeException t){
      write(t.getMessage());
      return null;

    }
  catch (Exception e){
    return null;
    }
  }

}
