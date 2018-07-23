package transit.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/** A parent class for stations in this simulation */
public class Station {
  /** Maps the name of a station to the appropriate station object */
  private static HashMap<String, HashMap<String, Station>> stations = new HashMap<>();
  /** The fee charged per station travelled by this station */
  private int perStationFee;
  /** The initial fee charged by this station at the start of a trip */
  private int initialFee;
  /** The name of this station */
  private String name;
  /**
   * Create a new instance of transit.system.Station
   *
   * @param name The name of this station
   */
  Station(String name, String stationType) throws InvalidInputException {
    this.name = name;
    if (!stations.containsKey(stationType)) {
      stations.put(stationType, new HashMap<>());
    }
    if (!stations.get(stationType).containsKey(name)) {
      stations.get(stationType).put(name, this);
      this.setFees(stationType);
    } else {
      throw new InvalidInputException(); // temporary exception
    }
  }

  /** @return HashMap of all stations of similar type */
  public static HashMap<String, Station> getStationsCopy(String type) {
    return new HashMap<>(stations.get(type));
  }

  public List<Station> getStations(String stationType) {
    return new ArrayList<>(stations.get(stationType).values());
  }

  @Override
  /** @return whether or not these two stations are logically equivalent */
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Station station = (Station) o;
    return Objects.equals(name, station.name);
  }

  /** @return the name of this station */
  public String getName() {
    return this.name;
  }

  /** @return the fare charged by this station at the start of a new trip portion */
  public int getInitialFee() {
    return this.initialFee;
  }

  /**
   * Set the initialFee of this station, the fare charged by this station at the start of a new trip
   * portion.
   *
   * @param initialFee the new initialFee.
   */
  public void setInitialFee(int initialFee) {
    this.initialFee = initialFee;
  }

  /** @return the fare charged by this station when a user travels by it */
  public int getPerStationFee() {
    return perStationFee;
  }

  /**
   * Set the perStationFee of this station.
   *
   * @param perStationFee the new perStationFee.
   */
  public void setPerStationFee(int perStationFee) {
    this.perStationFee = perStationFee;
  }

  /**
   * Adds the newStation to either the list of BusStations or SubwayStations
   *
   * @param newStation the station to be added to allStations.
   */
  //  public void addStation(transit.system.Station newStation) {
  //    stations.put(newStation.name, newStation);
  //  }

  /**
   * @param otherStation the station we checking for association with.
   * @return whether this transit.system.Station intersects otherStation (have common name)
   */
  public boolean isAssociatedStation(Station otherStation) {
    return this.name.equals(otherStation.getName());
  }

  /** @return A string representation of a station */
  public String toString() {
    return this.name;
  }

  private void setFees(String stationType) {
    if (stationType.equals("Bus")) {
      this.perStationFee = 0;
      this.initialFee = 200;
    } else {
      this.perStationFee = 50;
      this.initialFee = 0;
    }
  }
}