import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class TransitReadWrite {
  private static final String SPLIT_SYMBOL = "\\s\\|\\s";
  static HashMap<String, Function<List<String>, Void>> keyWords = new HashMap<>();
  /** The writer for the TransitReadWrite to write to */
  static BufferedWriter writer;

  /** @param message The message to be outputted through writer */
  public static void write(String message) {
    try {
      writer.write(message + System.lineSeparator());
    } catch (IOException e) {
      System.out.println("File not found, create an events.txt and rerun the program");
    }
  }

  /**
   * Parses the events.txt file, calling appropriate methods throughout the program
   *
   * @throws IOException
   * @throws InitLineException
   */
  public static void read(BufferedReader reader, BufferedWriter writer)
      throws IOException, InitLineException {

    // Build the command hashmap
    TransitReadWrite.buildHashMap();
    TransitReadWrite.writer = writer;

    // Reads the opening init line determining how many routes to make
    String initLine = reader.readLine().trim();
    ArrayList<String> initLineWords =
        new ArrayList<String>(Arrays.asList(initLine.split(SPLIT_SYMBOL)));

    if (initLineWords.size() != 4) {
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

    TransitTime.initDate(initLineWords.get(3));

    // Iterate through subway routes, constructing from events.txt
    for (int i = 0; i < numSubwayRoutes; i++) {
      String tempLine = reader.readLine().trim();
      ArrayList<String> tempLineWords =
          new ArrayList<>(Arrays.asList(tempLine.split(SPLIT_SYMBOL)));
      Route newRoute = new SubwayRoute(tempLineWords.subList(1, tempLineWords.size()));
//      Route newRoute = new SubwayRoute(tempLineWords);
      TransitReadWrite.write("Created new subway route: " + newRoute.getRouteNumber() + System.lineSeparator());
    }

    // Iterate through bus routes, constructing from events.txt
    for (int i = 0; i < numBusRoutes; i++) {
      String tempLine = reader.readLine().trim();
      ArrayList<String> tempLineWords =
          new ArrayList<>(Arrays.asList(tempLine.split(SPLIT_SYMBOL)));
      Route newRoute = new BusRoute(tempLineWords.subList(1, tempLineWords.size()));
//      Route newRoute = new BusRoute(tempLineWords);
      TransitReadWrite.write("Created new bus route: " + newRoute.getRouteNumber() + System.lineSeparator());
    }

    // Execute remaining commands in events.txt
    String actionLine = reader.readLine().trim();
    while (actionLine != "\n" && actionLine != null) {
      ArrayList<String> tempLineWords =
          new ArrayList<>(Arrays.asList(actionLine.split(SPLIT_SYMBOL)));
      if (TransitReadWrite.keyWords.get(tempLineWords.get(0)) == null) {
        TransitReadWrite.write("Invalid command: This command does not exist");
      } else if (tempLineWords.size() > 1) {
        TransitReadWrite.keyWords
            .get(tempLineWords.get(0))
            .apply(tempLineWords.subList(1, tempLineWords.size()));
      } else {
        TransitReadWrite.keyWords.get(tempLineWords.get(0)).apply(new ArrayList<>());
      }
      actionLine = reader.readLine();
    }
    writer.close();
  }

  private static void buildHashMap() {
    TransitReadWrite.keyWords.put(
        "TAP",
        (cardInfo) -> {
          CardParser.tap(cardInfo);
          return null;
        });
    TransitReadWrite.keyWords.put(
        "ADDUSER",
        (userInfo) -> {
          UserParser.addUser(userInfo);
          return null;
        });
    TransitReadWrite.keyWords.put(
        "ADDCARD",
        (cardInfo) -> {
          CardParser.addCard(cardInfo);
          return null;
        });
    TransitReadWrite.keyWords.put(
        "REMOVECARD",
        (cardInfo) -> {
          CardParser.removeCard(cardInfo);
          return null;
        });
    TransitReadWrite.keyWords.put(
        "REPORTTHEFT",
        (userInfo) -> {
          CardParser.reportTheft(userInfo);
          return null;
        });
    TransitReadWrite.keyWords.put(
        "ADDFUNDS",
        (userInfo) -> {
          CardParser.addFunds(userInfo);
          return null;
        });
    TransitReadWrite.keyWords.put(
        "ENDDAY",
        (emptyList) -> {
          TransitTime.endDay(emptyList);
          return null;
        });
    TransitReadWrite.keyWords.put(
        "MONTHLYEXPENDITURE",
        (userInfo) -> {
          UserParser.monthlyExpenditure(userInfo);
          return null;
        });
    TransitReadWrite.keyWords.put(
        "CHECKBALANCE",
        (cardInfo) -> {
          CardParser.checkBalance(cardInfo);
          return null;
        });
    TransitReadWrite.keyWords.put(
        "CHANGENAME",
        (userInfo) -> {
          UserParser.changeName(userInfo);
          return null;
        });
    TransitReadWrite.keyWords.put(
        "ACTIVATECARD",
        (cardInfo) -> {
          CardParser.activate(cardInfo);
          return null;
        });
    TransitReadWrite.keyWords.put(
        "DAILYREPORTS",
        (userInfo) -> {
          UserParser.dailyReports(userInfo);
          return null;
        });
  }
}

