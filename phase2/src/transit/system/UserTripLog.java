package transit.system;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;

/** Records the previous Trips of a User */
public class UserTripLog extends Statistics<Trip> {

  /** Instantiates a new instance of UserTripLog */
  public UserTripLog() {
    dailyLogs = new HashMap<LocalDate, ArrayList<Trip>>();
  }

  /** Initializes a new instance of UserTapLog */
  UserTripLog(User user) {
    this.dailyLogs = new HashMap<>();
  }

  @Override
  String generateWeeklyValues() {
    return null;
  }

  @Override
  String generateMonthlyValues() {
    return null;
  }
}
