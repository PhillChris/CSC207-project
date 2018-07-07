import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {

  /**
   * Runs the transit system simulation, with event input from events.txt
   *
   * @param args standard Java arguments
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    try {
      Parser.parse();
    } catch (InitLineException init) {
      Parser.write("Input line invalid, program not executable.");
    }
  }

  private static void parseTap(String actionLine, int lineIdx, FileWriter writer) throws IOException {
    // Read cardholderId
    lineIdx += 7;
    String cardHolderId = Main.readWord(actionLine, lineIdx);
    lineIdx = Main.getEndOfWord(actionLine, lineIdx);
    lineIdx += 7;
    String cardId = Main.readWord(actionLine, lineIdx);
    lineIdx = Main.getEndOfWord(actionLine, lineIdx);
    lineIdx += 10;
    String stationId = Main.readWord(actionLine, lineIdx);
    lineIdx = Main.getEndOfWord(actionLine, lineIdx);
    lineIdx += 7;
    String time = Main.readWord(actionLine, lineIdx);
    // Todo: initialize a proper date for time
    for (CardHolder user: TransitSystem.getUsers()) {
      if (user.getEmail().equals(cardHolderId)) {
        try {
          user.getCard(Integer.parseInt(cardId)).tap(); //todo: use java date object in tap call
          break;
        } catch(CardNotFoundException cardException) {
          writer.write("Card not found: user " + cardHolderId + " does not hold card " + cardId);
        }
      }
    }
  }
}
