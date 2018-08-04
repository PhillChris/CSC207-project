package transit.system;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;

/** A class for stations in this simulation */
public class Station implements Serializable {
  /** All acceptable possible route types in this transit system */
  public static final String[] POSSIBLE_TYPES = {"Bus", "Subway"};
  /** The fee charged when starting a bus trip to non-students */
  private static final int BUS_INITIAL_FEE = 200;
  /** The fee charged when starting a bus trip to students */
  private static final int STUDENT_BUS_INITIAL_FEE = 100;
  /** The fee charged when passing each subway station to non-students */
  private static final int SUBWAY_PERSTATION_FEE = 50;
  /** The fee charged when passing each subway station to students */
  private static final int STUDENT_SUBWAY_PERSTATION_FEE = 40;
  /** The fee charged per station travelled by this station */
  private int perStationFee;
  /** The initial fee charged by this station at the start of a trip */
  private int initialFee;
  /** The fee charged per station travelled by students by this station */
  private int perStationStudentFee;
  /** The initial fee charged by this station to students at the start of a trip*/
  private int initialStudentFee;
  /** The name of this station */
  private String name;
  /** The number of people who have tapped on at this station per day. */
  private HashMap<LocalDate, Integer> tapsOn = new HashMap<>();
  /** The number of people who have tapped off at this station per day. */
  private HashMap<LocalDate, Integer> tapsOff = new HashMap<>();
  /** The stationType of this station */
  private String stationType;
  /** Stores associated statistics to this user */
  private HashMap<String, Statistics> statistics;

  /**
   * Create a new instance of transit.system.Station
   *
   * @param name The name of this station
   */
  public Station(String name, String stationType) {
    this.name = name;
    this.stationType = stationType;
    this.setFees();
    statistics.put("Taps In", new Statistics());
    statistics.put("Taps Out", new Statistics());
  }

  @Override
  /** @return whether or not these two stations are logically equivalent */
  public boolean equals(Object o) {
    return o instanceof Station && this.name.equals(((Station) o).name);
  }

  /** @return A string representation of a station */
  public String toString() {
    return this.name;
  }

  /** @return the fare charged by this station at the start of a new trip portion */
  public int getInitialFee(String permission) {
    if (permission == "student") {
      return this.initialStudentFee;
    }
    return this.initialFee;
  }

  /** @return the fare charged by this station when a user travels by it */
  public int getPerStationFee(String permission) {
    if (permission == "student") {
      return perStationStudentFee;
    }
    return perStationFee;
  }

  /** Records the tapping in of a station */
  void record(String statName, Integer quantity) {
    statistics.get(statName).update(quantity);
  }

  /**
   * Sets the fees of this station
   */
  private void setFees() {
    if (stationType.equals("Bus")) {
      this.perStationFee = 0;
      this.initialFee = BUS_INITIAL_FEE;
      this.perStationStudentFee = 0;
      this.initialStudentFee = STUDENT_BUS_INITIAL_FEE;
    } else {
      this.perStationFee = SUBWAY_PERSTATION_FEE;
      this.initialFee = 0;
      this.perStationStudentFee = STUDENT_SUBWAY_PERSTATION_FEE;
      this.initialStudentFee = 0;
    }
  }
}
