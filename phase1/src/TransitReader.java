import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class TransitReader {
  private static final String SPLIT_SYMBOL = "\\s\\|\\s";
  static HashMap<String, Function<List<String>, Void>> keyWords = new HashMap<>();

  /**
   * Parses the events.txt file, calling appropriate methods throughout the program
   *
   * @throws IOException
   * @throws InitLineException
   */
  public static void read(BufferedReader reader, BufferedWriter writer)
      throws IOException, InitLineException {

    // Build the command hashmap
    TransitReader.buildHashMap();
    Parser.setWriter(writer);

    // Reads the opening init line determining how many routes to make
    String initLine = reader.readLine();
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

    // Iterate through subway routes, constructing from events.txt
    for (int i = 0; i < numSubwayRoutes; i++) {
      String tempLine = reader.readLine();
      ArrayList<String> tempLineWords =
          new ArrayList<>(Arrays.asList(tempLine.split(SPLIT_SYMBOL)));
      Route newRoute = new SubwayRoute(tempLineWords);
      writer.write("Created new subway route:" + newRoute.getRouteNumber() + "\n");
    }

    // Iterate through bus routes, constructing from events.txt
    for (int i = 0; i < numBusRoutes; i++) {
      String tempLine = reader.readLine();
      ArrayList<String> tempLineWords =
          new ArrayList<>(Arrays.asList(tempLine.split(SPLIT_SYMBOL)));
      Route newRoute = new BusRoute(tempLineWords);
      writer.write("Created new bus route:" + newRoute.getRouteNumber() + "\n");
    }

    // Execute remaining commands in events.txt
    String actionLine = reader.readLine();
    while (actionLine != null) {
      ArrayList<String> tempLineWords =
          new ArrayList<>(Arrays.asList(actionLine.split(SPLIT_SYMBOL)));
      if (tempLineWords.size() <= 1) {
        TransitReader.keyWords
            .get(tempLineWords.get(0))
            .apply(tempLineWords.subList(1, tempLineWords.size()));
      } else {
        TransitReader.keyWords.get(tempLineWords.get(0)).apply(new ArrayList<>());
      }
      actionLine = reader.readLine();
    }
    writer.close();
  }

  private static void buildHashMap() {
    TransitReader.keyWords.put(
        "TAP",
        (cardInfo) -> {
          Parser.tap(cardInfo);
          return null;
        });
    TransitReader.keyWords.put(
        "ADDUSER",
        (userInfo) -> {
          Parser.addUser(userInfo);
          return null;
        });
    TransitReader.keyWords.put(
        "ADDCARD",
        (cardInfo) -> {
          Parser.addCard(cardInfo);
          return null;
        });
    TransitReader.keyWords.put(
        "REMOVECARD",
        (cardInfo) -> {
          Parser.removeCard(cardInfo);
          return null;
        });
    TransitReader.keyWords.put(
        "REPORTTHEFT",
        (userInfo) -> {
          Parser.reportTheft(userInfo);
          return null;
        });
    TransitReader.keyWords.put(
        "ADDFUNDS",
        (userInfo) -> {
          Parser.addFunds(userInfo);
          return null;
        });
    TransitReader.keyWords.put(
        "ENDDAY",
        (emptyList) -> {
          TransitTime.endDay(emptyList);
          return null;
        });
    TransitReader.keyWords.put(
        "MONTHLYEXPENDITURE",
        (userInfo) -> {
          Parser.monthlyExpenditure(userInfo);
          return null;
        });
  }
}
