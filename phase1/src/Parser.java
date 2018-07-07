import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Parser {

  public static void write(String message, FileWriter writer) throws IOException {
    writer.write(message + "/n");
  }

  static void tap(List<String> cardInfo) {}

  static void addUser(List<String> userInfo) {}

  static void addCard(List<String> cardInfo) {}

  static void removeCard(List<String> cardInfo) {}

  static void reportTheft(List<String> userInfo) {}

  static void addFunds(List<String> userInfo) {}

  static void endDay(List<String> emptyList) {}

  static void monthlyExpenditue(List<String> userInfo) {}
}
