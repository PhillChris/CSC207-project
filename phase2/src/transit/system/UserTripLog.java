package transit.system;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;

/** Records the previous Trips of a User */
public class UserTripLog {

    private HashMap<LocalDate, ArrayList<Trip>> tripLog;

  /** Instantiates a new instance of UserTripLog */
  public UserTripLog() {
    tripLog = new HashMap<LocalDate, ArrayList<Trip>>();
  }

}
