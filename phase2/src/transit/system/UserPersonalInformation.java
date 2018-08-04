package transit.system;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;

/** Records all of the personal records associated to a user */
public class UserPersonalInformation {

    private HashMap<LocalDate, ArrayList<Trip>> tripLog;

  /** Instantiates a new instance of UserPersonalInformation */
  public UserPersonalInformation() {
    tripLog = new HashMap<LocalDate, ArrayList<Trip>>();
  }

}
