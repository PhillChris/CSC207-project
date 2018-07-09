import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public abstract class Station {
  private static int totalStations;
  private static HashMap<String, List<Station>> allStations = new HashMap<>();
  final int stationID;
  protected String name;
  protected int initialFee;
  protected int perStationFee;
  protected List<Station> associatedStations = new ArrayList<>();

  public Station(String name) {
    this.name = name;
    stationID = totalStations;
    totalStations++;
  }

  public static HashMap<String, List<Station>> getAllStations() {
    return allStations;
  }

  /**
   * Adds the newStation to allStations. Then associates all stations of the same name with
   * newStation.
   *
   * @param newStation the station to be added to allStations.
   */
  public static void addStation(Station newStation) {
    String stationName = newStation.name;
    // if no station of this name, add it to allStations
    if (!allStations.containsKey(stationName)) {
      allStations.put(stationName, new ArrayList<>());
      allStations.get(stationName).add(newStation);
    }
    // associate all stations of matching name
    for (Station station : allStations.get(stationName)) {
      station.associatedStations.add(newStation);
      newStation.associatedStations.add(station);
    }
  }

  /** @return the name of this station */
  public String getName() {
    return this.name;
  }

  /**
   * Called at the beginning of a ride between stations
   *
   * @return the constant fare for this type of station
   */
  public int getInitialFee() {
    return this.initialFee;
  }

  /**
   * @return the associated station to this station (i.e. the bus station in this subway station or
   *     the subway station connected to this bus station)
   */
  public boolean isAssociatedStation(Station station) {
    return this.associatedStations.contains(station);
  }

}
