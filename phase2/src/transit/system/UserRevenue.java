package transit.system;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;

public class UserRevenue extends Statistics<Double> implements Serializable {

  /** Instantiates a new instance of UserRevenue */
  public UserRevenue() {
    dailyLogs = new HashMap<LocalDate, ArrayList<Double>>();
  }

  /** @return A String detailing average expenditure per month of this transit.system.User. */
  String avgMonthlyMessage() {
    String message = "Cost per month for user: ";
    return message.trim();
  }

  @Override
  public String generateWeeklyValues() {
    return null;
  }

  @Override
  String generateMonthlyValues() {
    return null;
  }
}
