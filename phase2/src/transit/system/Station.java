package transit.system;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;

/** A class for stations in this simulation */
public class Station implements Serializable {
  /** All acceptable possible route types in this transit system */
  public static final String[] POSSIBLE_TYPES = {"Bus", "Subway"};
  /** The fee charged when starting a bus trip to non-students */
  public static final int BUS_INITIAL_FEE = 200;
  /** The fee charged when starting a bus trip to students */
  public static final int STUDENT_BUS_INITIAL_FEE = 100;
  /** The fee charged when passing each subway station to non-students */
  public static final int SUBWAY_PERSTATION_FEE = 50;
  /** The fee charged when passing each subway station to students */
  public static final int STUDENT_SUBWAY_PERSTATION_FEE = 40;

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

  /**
   * Create a new instance of transit.system.Station
   *
   * @param name The name of this station
   */
  public Station(String name, String stationType) {
    this.name = name;
    this.stationType = stationType;
    this.setFees();
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
    return this.initialStudentFee;
  }

  /** @return the fare charged by this station when a user travels by it */
  public int getPerStationFee(String permission) {
    if (permission == "student") {
      return perStationStudentFee;
    }
    return perStationFee;
  }

  /** Records the tapping in of a station */
  void recordTapIn() {
    LocalDate day = TransitTime.getCurrentDate();
    if (tapsOn.containsKey(day)) {
      tapsOn.put(TransitTime.getCurrentDate(), tapsOn.get(day) + 1);
    } else {
      tapsOn.put(day, 1);
    }
  }

  /** Records the tapping out of a station */
  void recordTapOut() {
    LocalDate date = TransitTime.getCurrentDate();
    if (tapsOff.containsKey(date)) {
      tapsOff.put(date, tapsOff.get(date) + 1);
    } else {
      tapsOff.put(date, 1);
    }
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

  /**
   * @param date A day of the simulation
   * @return The numeber of taps on for this station at date
   */
  public int getTapsOn(LocalDate date) {
    if (tapsOn.get(date) != null) {
      return tapsOn.get(date);
    }
    return 0;
  }

  /**
   * @param date A day of the simulation
   * @return The numeber of taps off for this station at date
   */
  public int getTapsOff(LocalDate date) {
    if (tapsOff.get(date) != null) {
      return tapsOff.get(date);
    }
    return 0;
  }
}
