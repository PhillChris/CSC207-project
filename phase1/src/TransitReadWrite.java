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
  /** The reader which reads from events.txt */
  private BufferedReader reader;
  /** The writer which writes to output.txt */
  private BufferedWriter writer;
  /** Handles operations to be executed on users */
  private UserParser userParser;
  /** Handles operations to be executed on cards */
  private CardParser cardParser;

  public TransitReadWrite(BufferedReader reader, BufferedWriter writer) {
    this.reader = reader;
    this.writer = writer;
  }
  /**
   * Writes a given message to output.txt
   *
   * @param message The message to be written
   */
  public void write(String message) {
  }

  /**
   * Initializes the program (i.e. constructs routes) with initialization data from events.txt
   *
   * @throws IOException
   * @throws InitLineException
   */
  public void init()
      throws IOException, InitLineException {
    userParser = new UserParser(writer);
    cardParser = new CardParser(writer);

    /* Reads the opening init line determining how many routes to make and
     * the current system date */
    String initLine = reader.readLine().trim(); // removes initial and trailing whitespace
    ArrayList<String> initLineWords =
        new ArrayList<>(Arrays.asList(initLine.split(SPLIT_SYMBOL)));

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
    TransitTime.initDate(initLineWords.get(2), this.userParser);

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
        writer.write("Created new subway route" + System.lineSeparator());
      } else if (tempLineWords.get(0).equals("BUS")) {
        Route newRoute = new Route(stationNames, busFact);
        writer.write("Created new bus route" + System.lineSeparator());
      }
    }
  }

  /**
   * Runs the program (i.e. executes user commands) through commands provided in events.txt
   *
   * @throws IOException
   */
  public void run() throws IOException {

    /* Iterate through remaining action lines and execute the corresponding
     * commands in events.txt, after initializing routes */
    String actionLine = reader.readLine().trim();
    // While there are still non-empty lines in events.txt
    while (actionLine != System.lineSeparator() && actionLine != null) {
      ArrayList<String> tempLineWords =
          new ArrayList<>(Arrays.asList(actionLine.split(SPLIT_SYMBOL)));
      if (userParser.getKeyWords().get(tempLineWords.get(0)) != null) {
        userParser.parse(tempLineWords);
      } else if (cardParser.getKeyWords().get(tempLineWords.get(0)) != null) {
        cardParser.parse(tempLineWords);
      } else {
        writer.write(
            "Invalid command: This command is not recognized by the transit system"
                + System.lineSeparator());
      }
      actionLine = reader.readLine();
    }
    writer.close();
  }
}
