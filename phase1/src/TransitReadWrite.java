import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/** A class to handle all text file handling in the transit system simulation */
public class TransitReadWrite {
  /** The token which separates parameters in the events.txt file */
  private static final String SPLIT_SYMBOL = "\\s\\|\\s";
  /** Stores the commands which can be execute from events.txt */
  static HashMap<String, Function<List<String>, Void>> keyWords = new HashMap<>();
  /** The writer which writes to output.txt */
  static BufferedWriter writer;

  /**
   * Writes a given message to output.txt
   *
   * @param message The message to be written
   */
  public static void write(String message) {
    try {
      writer.write(message + System.lineSeparator());
    } catch (IOException e) {
      System.out.println("File not found, create an events.txt and rerun the program");
    }
  }

  /**
   * Reads the events.txt file, calling appropriate methods for each line of the program to be
   * executed. The proper format to use for events.txt can be found in README.txt
   *
   * @throws IOException
   * @throws InitLineException
   */
  public static void read(BufferedReader reader, BufferedWriter writer)
      throws IOException, InitLineException {

    TransitReadWrite.buildHashMap();
    TransitReadWrite.writer = writer;

    /* Reads the opening init line determining how many routes to make and
     * the current system date */
    String initLine = reader.readLine().trim(); // removes initial and trailing whitespace
    ArrayList<String> initLineWords =
        new ArrayList<String>(Arrays.asList(initLine.split(SPLIT_SYMBOL)));

    if (initLineWords.size() != 3) { // if the formatting is incorrect for the initial line
      throw new InitLineException();
    }

    int numRoutes;
    // throws an error if the given number of routes is not parseable as an integer
    try {
      numRoutes = Integer.parseInt(initLineWords.get(1));
    } catch (NumberFormatException numberException) {
      throw new InitLineException();
    }

    // Sets the initial date for the system
    TransitTime.initDate(initLineWords.get(2));

    /* Iterate through routes and names of stations given in the first lines of events.txt,
     *  and constructs route objects containing station objects*/
    StationFactory subFact = new SubwayFactory(); // used to construct subway stations
    StationFactory busFact = new BusFactory(); // used to construct bus stations
    for (int i = 0; i < numRoutes; i++) {
      String tempLine = reader.readLine().trim();
      ArrayList<String> tempLineWords =
          new ArrayList<>(Arrays.asList(tempLine.split(SPLIT_SYMBOL)));
      List<String> stationNames = tempLineWords.subList(1, tempLineWords.size());
      if (tempLineWords.get(0).equals("SUBWAY")) {
        Route newRoute = new Route(stationNames, subFact);
        TransitReadWrite.write(
            "Created new subway route: " + newRoute.getRouteId() + System.lineSeparator());
      } else if (tempLineWords.get(0).equals("BUS")) {
        Route newRoute = new Route(stationNames, busFact);
        TransitReadWrite.write(
            "Created new bus route: " + newRoute.getRouteId() + System.lineSeparator());
      }
    }

    /* Iterate through remaining action lines and execute the corresponding
     * commands in events.txt, after initializing routes */
    String actionLine = reader.readLine().trim();
    /* While there are still non-empty lines in events.txt*/
    while (actionLine != System.lineSeparator() && actionLine != null) {
      ArrayList<String> tempLineWords =
          new ArrayList<>(Arrays.asList(actionLine.split(SPLIT_SYMBOL)));
      if (TransitReadWrite.keyWords.get(tempLineWords.get(0)) == null) {
        TransitReadWrite.write("Invalid command: This command does not exist");
      } else if (tempLineWords.size() > 1) {
        /* executes the command which the given keyword
         * maps to by passing the appropriate parameters */
        TransitReadWrite.keyWords
            .get(tempLineWords.get(0))
            .apply(tempLineWords.subList(1, tempLineWords.size()));
      } else {
        // executes a parameterless function with an empty ArrayList for type consistency
        TransitReadWrite.keyWords.get(tempLineWords.get(0)).apply(new ArrayList<>());
      }
      actionLine = reader.readLine();
    }
    writer.close();
  }

  /**
   * A helper method which constructs the keyWords hash map, mapping to the executable functions in
   * the transit system simulation
   */
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
