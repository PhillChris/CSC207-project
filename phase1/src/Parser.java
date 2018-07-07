import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Parser {

  static LocalDate currentTime;

  static BufferedWriter writer;

  static void setWriter(BufferedWriter bufferedWriter){
    writer = bufferedWriter;
  }

  public static void write(String message, BufferedWriter writer) throws IOException {
    writer.write(message + "/n");
  }



  /**
   * Taps a card
   * @param cardInfo The information of the card given by the user
   */
  static void tap(List<String> cardInfo) throws IOException {
    try{
    String time = cardInfo.get(0);
    if (checkTimeOrder(time)){


    }
    else{
      throw new TapException();
    }
    }
    catch (TapException e) {
      write(e.getMessage(), writer);

    }


    }






  static void addUser(List<String> userInfo) {}

  static void addCard(List<String> cardInfo) {}

  static void removeCard(List<String> cardInfo) {}

  static void reportTheft(List<String> userInfo) {}

  static void addFunds(List<String> userInfo) {}

  static void endDay(List<String> emptyList) {}

  static void monthlyExpenditue(List<String> userInfo) {}

  static boolean checkTimeOrder(String time){
    try{
    String[] formatted = time.split("-");
    LocalDate newTime = LocalDate.of(Integer.parseInt(formatted[0]), Integer.parseInt(formatted[1]),
            Integer.parseInt(formatted[2]));

    if




  }
  catch (Exception e){
    return false;

    }
  }

}
