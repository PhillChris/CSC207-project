import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

  /**
   * Runs the transit system simulation, with event input from events.txt
   *
   * @param args standard Java arguments
   * @throws IOException
   */
  public static final int NEXT_WORD_SKIP = 2;

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader("events.txt"));

    // Reads the opening init line determining how many routes to make
    String initLine = reader.readLine();
    int lineIdx = 0;
    ArrayList<Integer> routeLengths = new ArrayList<>();
    while (lineIdx < initLine.length()) {
      if ("0123456789".indexOf(initLine.charAt(lineIdx)) != -1) {
        String tempValue = new String();
        while (initLine.charAt(lineIdx) != ' ') {
          tempValue += initLine.charAt(lineIdx);
          lineIdx++;
        }
        routeLengths.add(Integer.parseInt(tempValue));
      }
      lineIdx++;
    }

    // Creates each of the subway routes
    HashMap<String, ArrayList<Station>> subwayRoutes = new HashMap<>();
    for (int i = 0; i < routeLengths.get(0); i++) {
      String tempRoute = reader.readLine();
      lineIdx = 0;
      String routeName = "";
      ArrayList<String> stationNames = new ArrayList<>();

      while (tempRoute.charAt(lineIdx) != ':') {
        routeName += tempRoute.charAt(lineIdx);
        lineIdx++;
      }

      lineIdx += NEXT_WORD_SKIP;

      while (lineIdx < tempRoute.length()) {
        String tempStation = "";
        while (tempRoute.charAt(lineIdx) != ',' && tempRoute.charAt(lineIdx) != '.') {
          tempStation += tempRoute.charAt(lineIdx);
          lineIdx++;
        }
        stationNames.add(tempStation);
        lineIdx += NEXT_WORD_SKIP;
      }

      // Initialize all SubwayStations
      ArrayList<Station> tempStations = new ArrayList<>();
      for (int j = 0; j < stationNames.size(); j++) {
        SubwayStation tempStation = new SubwayStation(stationNames.get(j), routeName);
        tempStations.add(tempStation);
      }

      subwayRoutes.put(routeName, tempStations);
    }

    // Creates each of the bus routes
    HashMap<String, ArrayList<Station>> busRoutes = new HashMap<>();
    for (int i = 0; i < routeLengths.get(0); i++) {
      String tempRoute = reader.readLine();
      lineIdx = 0;
      String routeName = "";
      ArrayList<String> stationNames = new ArrayList<>();

      while (tempRoute.charAt(lineIdx) != ':') {
        routeName += tempRoute.charAt(lineIdx);
        lineIdx++;
      }

      lineIdx += NEXT_WORD_SKIP;

      while (lineIdx < tempRoute.length()) {
        String tempStation = "";
        while (tempRoute.charAt(lineIdx) != ',' && tempRoute.charAt(lineIdx) != '.') {
          tempStation += tempRoute.charAt(lineIdx);
          lineIdx++;
        }
        stationNames.add(tempStation);
        lineIdx += NEXT_WORD_SKIP;
      }

      // Initialize all BusStations
      ArrayList<Station> tempStations = new ArrayList<>();
      for (int j = 0; j < stationNames.size(); j++) {
        BusStation tempStation = new BusStation(stationNames.get(j), routeName);
        tempStations.add(tempStation);
      }

      busRoutes.put(routeName, tempStations);
    }

    TransitSystem.setSubwayRoutes(subwayRoutes);
    TransitSystem.setBusRoutes(busRoutes);
  }
}
