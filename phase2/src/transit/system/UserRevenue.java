package transit.system;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;

public class UserRevenue extends Statistics implements Serializable {

  /** Instantiates a new instance of UserRevenue */
  public UserRevenue() {
    dailyLogs = new HashMap<LocalDate, Double>();
  }

}
