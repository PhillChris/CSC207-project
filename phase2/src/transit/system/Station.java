package transit.system;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/** A parent class for stations in this simulation */
public class Station implements Serializable {
  /** All acceptable possible route types in this transit system */
  public static final String[] POSSIBLE_TYPES = {"Bus", "Subway"};
  /** The fee charged per station travelled by this station */
  private int perStationFee;
  /** The initial fee charged by this station at the start of a trip */
  private int initialFee;
  /** The name of this station */
  private String name;
  /** The number of people who have tapped on at this station. */
  private HashMap<LocalDate, Integer> tapsOn = new HashMap<>();
  /** The number of people who have tapped off at this station. */
  private HashMap<LocalDate, Integer> tapsOff = new HashMap<>();
  /**
   * Create a new instance of transit.system.Station
   *
   * @param name The name of this station
   */
  public Station(String name, String stationType) {
    this.name = name;
    this.setFees(stationType);
    this.setFees(stationType);
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
  //  public void addStationAtEnd(transit.system.Station newStation) {
  //    stations.put(newStation.name, newStation);
  //  }

  /**
   * @param otherStation the station we checking for association with.
   * @return whether this transit.system.Station intersects otherStation (have common name)
   */
  public boolean isAssociatedStation(Station otherStation) {
    return this.name.equals(otherStation.getName());
  }

  void recordTapIn(LocalDate date) {
    if (tapsOn.containsKey(date)) {
      tapsOn.put(date, tapsOn.get(date) + 1);
    } else {
      tapsOn.put(date, 1);
    }
  }

  void recordTapOut(LocalDate date) {
    if (tapsOff.containsKey(date)) {
      tapsOff.put(date, tapsOff.get(date) + 1);
    } else {
      tapsOff.put(date, 1);
    }
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

  public int getTapsOn(LocalDate date) {
    if (tapsOn.get(date) != null) {
      return tapsOn.get(date);
    }
    return 0;
  }

  public int getTapsOff(LocalDate date) {
    if (tapsOff.get(date) != null) {
      return tapsOff.get(date);
    }
    return 0;
  }
}
