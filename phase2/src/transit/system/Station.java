package transit.system;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;

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
    if (o instanceof Station) {
      return this.name.equals(((Station) o).name);
    }
    return false;
  }

  /** @return the name of this station */
  public String getName() {
    return this.name;
  }

  /** @return the fare charged by this station at the start of a new trip portion */
  public int getInitialFee() {
    return this.initialFee;
  }

  /** @return the fare charged by this station when a user travels by it */
  public int getPerStationFee() {
    return perStationFee;
  }

  /**
   * Records the tapping in of a station
   * @param date
   */
  void recordTapIn() {
    LocalDate day = TransitTime.getCurrentDate();
    if (tapsOn.containsKey(day)) {
      tapsOn.put(TransitTime.getCurrentDate(), tapsOn.get(day) + 1);
    } else {
      tapsOn.put(day, 1);
    }
  }

  void recordTapOut() {
    LocalDate date = TransitTime.getCurrentDate();
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
