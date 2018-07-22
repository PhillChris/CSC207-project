package transit.system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/** A reader for the transit system, making parse requests using events.txt commands */
public class TransitReader {
  /** The token which separates parameters in the events.txt file */
  private static final String SPLIT_SYMBOL = "\\s\\|\\s";
  /** The reader which reads from events.txt */
  private BufferedReader reader;
  /** The writer which writes to output.txt */
  private BufferedWriter writer;
  /** Handles operations to be executed on users */

  /**
   * Constructs a new instance of a transit.system.TransitReader
   *
   * @param reader The file reader to be used throughout the simulation (from events.txt)
   * @param writer The file writer to be used throughout the simulation (to output.txt)
   */
  TransitReader(BufferedReader reader, BufferedWriter writer) {
    this.reader = reader;
    this.writer = writer;
  }

  /**
   * Initializes the program (i.e. constructs routes) with initialization data from events.txt
   *
   * @throws IOException if either events.txt or output.txt can't be found
   * @throws InitLineException if the formatting of the initial line of events.txt is incorrect
   */
  void init() throws IOException, InitLineException {

    /* Reads the opening init line determining how many routes to make and
     * the current system date */
    String initLine = reader.readLine().trim(); // removes initial and trailing whitespace
    ArrayList<String> initLineWords = new ArrayList<>(Arrays.asList(initLine.split(SPLIT_SYMBOL)));

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

    /* Iterate through routes and names of stations given in the first lines of events.txt,
     *  and constructs route objects containing station objects*/
    //    StationFactory subFact = new SubwayFactory(); // used to construct subway stations
    //    StationFactory busFact = new BusFactory(); // used to construct bus stations
    //    for (int i = 0; i < numRoutes; i++) {
    //      String tempLine = reader.readLine().trim();
    //      // separates a given line into route type and station names
    //      ArrayList<String> tempLineWords =
    //          new ArrayList<>(Arrays.asList(tempLine.split(SPLIT_SYMBOL))); //
    //      List<String> stationNames = tempLineWords.subList(1, tempLineWords.size());
    //      if (tempLineWords.get(0).equals("SUBWAY")) {
    //        transit.system.Route newRoute = new transit.system.Route(stationNames, subFact);
    //        writer.write("Created new subway route" + System.lineSeparator());
    //      } else if (tempLineWords.get(0).equals("BUS")) {
    //        transit.system.Route newRoute = new transit.system.Route(stationNames, busFact);
    //        writer.write("Created new bus route" + System.lineSeparator());
    //      }
    //    }
  }

  /**
   * Runs the program (i.e. executes user commands) through commands provided in events.txt
   *
   * @throws IOException if either events.txt or output.txt can't be found
   */
  void run() throws IOException {}
}
