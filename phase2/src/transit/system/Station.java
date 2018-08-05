package transit.system;

import java.io.Serializable;
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
  /** The fees associated with this station */
  private HashMap<String, Integer> fees = new HashMap<>();
  /** The name of this station */
  private String name;
  /** The stationType of this station */
  private String stationType;
  /** Stores associated statistics to this user */
  private HashMap<String, Statistics> statistics = new HashMap<>();

  /**
   * Create a new instance of transit.system.Station
   *
   * @param name The name of this station
   */
  public Station(String name, String stationType) {
    this.name = name;
    this.stationType = stationType;
    this.setFees();
    statistics.put("Tap In", new Statistics());
    statistics.put("Tap Out", new Statistics());
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
      return fees.get("initialStudentFee");
    }
    return fees.get("initialFee");
  }

  /** @return the fare charged by this station when a user travels by it */
  public int getPerStationFee(String permission) {
    if (permission == "student") {
      return fees.get("perStationStudentFee");
    }
    return fees.get("perStationFee");
  }

  /** Records the tapping in of a station */
  void record(String statName, Integer quantity) {
    statistics.get(statName).update(quantity);
  }

  /** Sets the fees of this station */
  private void setFees() {
    if (stationType.equals("Bus")) {
      fees.put("perStationFee", 0);
      fees.put("initialFee", BUS_INITIAL_FEE);
      fees.put("perStationStudentFee", 0);
      fees.put("initialStudentFee", STUDENT_BUS_INITIAL_FEE);
    } else {
      fees.put("perStationFee", SUBWAY_PERSTATION_FEE);
      fees.put("initialFee", 0);
      fees.put("perStationStudentFee", STUDENT_SUBWAY_PERSTATION_FEE);
      fees.put("initialStudentFee", 0);
    }
  }
}
