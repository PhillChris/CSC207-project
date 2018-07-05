import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

  /**
   * Runs the transit system simulation, with event input from events.txt
   *
   * @param args standard Java arguments
   * @throws IOException
   */
  private static final int NEXT_WORD_SKIP = 2;

  public static void main(String[] args) throws IOException {
    // Defining readers and writers
    BufferedReader reader = new BufferedReader(new FileReader("events.txt"));
    FileWriter writer = new FileWriter("output.txt");

    // Reads the opening init line determining how many routes to make
    String initLine = reader.readLine();
    int lineIdx = 0;
    ArrayList<Integer> routeLengths = new ArrayList<>();
    while (lineIdx < initLine.length()) {
      if ("0123456789".indexOf(initLine.charAt(lineIdx)) != -1) {
        String tempValue = "";
        while ("0123456789".indexOf(initLine.charAt(lineIdx)) != -1) {
          tempValue += initLine.charAt(lineIdx);
          lineIdx++;
        }
        routeLengths.add(Integer.parseInt(tempValue));
      }
      lineIdx++;
    }

    HashMap<String, ArrayList<Station>> subwayRoutes = new HashMap<>();
    HashMap<String, ArrayList<Station>> busRoutes = new HashMap<>();

    // Constructs all routes from events.txt
    makeRoutes(subwayRoutes, routeLengths.get(0), reader, "Subway");
    makeRoutes(busRoutes, routeLengths.get(1), reader, "Bus");

    // Sets them to the transit system
    TransitSystem.setSubwayRoutes(subwayRoutes);
    TransitSystem.setBusRoutes(busRoutes);
    TransitSystem.checkIntersection(); //todo: implement checkIntersection

    // Reads out the remaining lines in events.txt, and makes the system act accordingly
    String actionLine = reader.readLine();
    while (actionLine != null) {
      lineIdx = 0;
      String actionName = "";
      while (actionLine.charAt(lineIdx) != ':') {
        actionName += actionLine.charAt(lineIdx);
        lineIdx++;
      }

      // Call the appropriate action for this line of text
      switch(actionName) {
        case "TAP":
          parseTap(actionLine, lineIdx, writer);
          break;
        case "NEWUSER":
          break;
        case "ADDCARD":
          break;
        case "REMOVECARD":
          break;
        case "REPORTTHEFT":
          break;
        case "ADDFUNDS":
          break;
        case "ENDDAY":
          break;
        case "MONTHLYEXPENDITURE":
          break;
      }

      actionLine = reader.readLine();
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

      lineIdx += NEXT_WORD_SKIP;

      while (lineIdx < tempRoute.length()) {
        String tempStation = "";
        while (lineIdx < tempRoute.length() && tempRoute.charAt(lineIdx) != ',') {
          tempStation += tempRoute.charAt(lineIdx);
          lineIdx++;
        }
        stationNames.add(tempStation);
        lineIdx += NEXT_WORD_SKIP;
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
