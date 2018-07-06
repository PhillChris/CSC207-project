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

  private static void makeRoutes(
      HashMap<String, ArrayList<Station>> newRoutes,
      int routeNum,
      BufferedReader reader,
      String stationType)
      throws IOException {
    for (int i = 0; i < routeNum; i++) {
      String tempRoute = reader.readLine();
      int lineIdx = 0;
      String routeName = "";
      ArrayList<String> stationNames = new ArrayList<>();

      while (tempRoute.charAt(lineIdx) != ':') {
        routeName += tempRoute.charAt(lineIdx);
        lineIdx++;
      }

      while (lineIdx < tempRoute.length()) {
        String tempStation = "";
        while (lineIdx < tempRoute.length() && tempRoute.charAt(lineIdx) != ',') {
          tempStation += tempRoute.charAt(lineIdx);
          lineIdx++;
        }
        stationNames.add(tempStation);
      }

      // Generates all stations based on name data
      ArrayList<Station> tempStations = new ArrayList<>();
      for (int j = 0; j < stationNames.size(); j++) {
        Station tempStation = new BusStation("INVALID TYPE", "INVALID TYPE");
        if (stationType == "Bus") {
          tempStation = new BusStation(stationNames.get(j), routeName);
        } else if (stationType == "Subway") {
          tempStation = new SubwayStation(stationNames.get(j), routeName);
        }
        tempStations.add(tempStation);
      }
      newRoutes.put(routeName, tempStations);
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

  private static String readWord(String actionLine, int lineIdx) {
    String word = "";
    while (lineIdx < actionLine.length() && actionLine.charAt(lineIdx) != ',') {
      word += actionLine.charAt(lineIdx);
      lineIdx++;
    }
    return word;
  }

  private static int getEndOfWord(String actionLine, int lineIdx) {
    while (lineIdx < actionLine.length() && actionLine.charAt(lineIdx) != ',') {
      lineIdx++;
    }
    return lineIdx;
  }
}
