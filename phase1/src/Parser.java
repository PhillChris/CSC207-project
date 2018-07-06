import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class Parser {
  static HashMap<String, Function<List<String>, Void>> keyWords = new HashMap<>();

  private static FileWriter writer;

  /**
   * Parses the events.txt file, calling appropriate methods throughout the program
   *
   * @throws IOException
   * @throws InitLineException
   */
  public static void parse() throws IOException, InitLineException {
    // Defining readers and writers
    BufferedReader reader = new BufferedReader(new FileReader("events.txt"));
    Parser.writer = new FileWriter("output.txt");

    // Reads the opening init line determining how many routes to make
    String initLine = reader.readLine();
    ArrayList<String> initLineWords = new ArrayList<String>(Arrays.asList(initLine.split(" | ")));

    if (initLineWords.size() != 3) {
      throw new InitLineException();
    }

    int numSubwayRoutes;
    int numBusRoutes;

    // Checks if the two parameters given for subway station and bus station numbers are valid ints
    try {
      numSubwayRoutes = Integer.parseInt(initLineWords.get(1));
      numBusRoutes = Integer.parseInt(initLineWords.get(2));
    } catch (NumberFormatException numberException) {
      throw new InitLineException();
    }

    // Iterate through subway routes, constructing from events.txt
    for (int i = 0; i < numSubwayRoutes; i++) {
      String tempLine = reader.readLine();
      ArrayList<String> tempLineWords = new ArrayList<>(Arrays.asList(tempLine.split(" | ")));
      ArrayList<Station> tempStations = new ArrayList<>();
      for (int j = 1; j < tempLineWords.size(); j++) {
        tempStations.add(new SubwayStation(tempLineWords.get(j), tempLineWords.get(0)));
      }
      TransitSystem.addSubwayRoute(tempLineWords.get(0), tempStations);
    }

    // Iterate through bus routes, constructing from events.txt
    for (int i = 0; i < numBusRoutes; i++) {
      String tempLine = reader.readLine();
      ArrayList<String> tempLineWords = new ArrayList<>(Arrays.asList(tempLine.split(" | ")));
      ArrayList<Station> tempStations = new ArrayList<>();
      for (int j = 1; j < tempLineWords.size(); j++) {
        tempStations.add(new BusStation(tempLineWords.get(j), tempLineWords.get(0)));
      }
      TransitSystem.addBusRoute(tempLineWords.get(0), tempStations);
    }

    // Build the command hashmap and execute remaining commands in events.txt
    Parser.buildHashMap();
    String actionLine = reader.readLine();
    while (actionLine != null) {
      ArrayList<String> tempLineWords =
          new ArrayList<String>(Arrays.asList(actionLine.split(" | ")));
      Parser.keyWords.get(tempLineWords.get(0)).apply(tempLineWords.subList(1, tempLineWords.size()));
      actionLine = reader.readLine();
    }
  }

  private static void buildHashMap() {
    Parser.keyWords.put(
        "TAP",
        (cardInfo) -> {
          Parser.tap(cardInfo);
          return null;
        });
    Parser.keyWords.put(
        "ADDUSER",
        (userInfo) -> {
          Parser.addUser(userInfo);
          return null;
        });
    Parser.keyWords.put(
        "ADDCARD",
        (cardInfo) -> {
          Parser.addCard(cardInfo);
          return null;
        });
    Parser.keyWords.put(
        "REMOVECARD",
        (cardInfo) -> {
          Parser.removeCard(cardInfo);
          return null;
        });
    Parser.keyWords.put(
        "REPORTTHEFT",
        (userInfo) -> {
          Parser.reportTheft(userInfo);
          return null;
        });
    Parser.keyWords.put(
        "ADDFUNDS",
        (userInfo) -> {
          Parser.addFunds(userInfo);
          return null;
        });
    Parser.keyWords.put(
        "ENDDAY",
        (emptyList) -> {
          Parser.endDay(emptyList);
          return null;
        });
    Parser.keyWords.put(
        "MONTHLYEXPENDITURE",
        (emptyList) -> {
          Parser.monthlyExpenditue(emptyList);
          return null;
        });
  }

  public static void write(String message) {}

  private static void tap(List<String> cardInfo) {}

  private static void addUser(List<String> userInfo) {}

  private static void addCard(List<String> cardInfo) {}

  private static void removeCard(List<String> cardInfo) {}

  private static void reportTheft(List<String> userInfo) {}

  private static void addFunds(List<String> userInfo) {}

  private static void endDay(List<String> emptyList) {}

  private static void monthlyExpenditue(List<String> emptyList) {}
}
