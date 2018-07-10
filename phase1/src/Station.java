import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Station {
  private static int totalStations; // to ensure no station has the same ID.
  private static HashMap<String, List<Station>> allStations = new HashMap<>();
  final int stationID;
  protected int initialFee;
  protected int perStationFee;
  private String name;

  public Station(String name) {
    this.name = name;
    stationID = totalStations;
    totalStations++;
  }


  /**
   * Adds the newStation to allStations. Then associates all stations of the same name with
   * newStation.
   *
   * @param newStation the station to be added to allStations.
   */
  public static void addStation(Station newStation) {
    if (newStation instanceof BusStation) {
      BusStation.newBusStation(newStation);
    } else if (newStation instanceof SubwayStation) {
      SubwayStation.newSubwayStation(newStation);
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
  public boolean isAssociatedStation(Station otherStation) {
    return this.name.equals(otherStation.getName());
  }

  public abstract HashMap<String, Station> getStations();
}
