package transit.system;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;

public class UserRevenue extends Statistics<Double> implements Serializable {

  @Override
  public HashMap<LocalDate, Double> generateWeeklyValues() {
    return new HashMap<>();
  }

  @Override
  public void clearLogs() {}

  /** @return A String detailing average expenditure per month of this transit.system.User. */
  String avgMonthlyMessage() {
    String message = "Cost per month for user: ";
    return message.trim();
  }
}
