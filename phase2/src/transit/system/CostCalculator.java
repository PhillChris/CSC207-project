package transit.system;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/** A class for making money calculations and generating daily reports. */
public class CostCalculator implements Serializable {

  private static HashMap<YearMonth, Integer> monthlyRevenue = new HashMap<>();
  /** Contains the expenditure per each day */
  private static HashMap<LocalDate, Integer> dailyRevenue = new HashMap<>();
  /** Contains the number of stations travelled per day by users */
  private static HashMap<LocalDate, Integer> dailyLog = new HashMap<>();

  /** Updates the expenditure and revenue in the entire system */
  void updateSystemRevenue(int fee, int tripLength) {
    LocalDate date = TransitTime.getCurrentDate();
    YearMonth month = YearMonth.of(date.getYear(), date.getMonth());

    if (dailyRevenue.containsKey(date)) {
      dailyRevenue.put(date, dailyRevenue.get(date) + fee);
      dailyLog.put(date, dailyLog.get(date) + tripLength);

    } else {
      dailyRevenue.put(date, fee);
      dailyLog.put(date, tripLength);
    }

    if (monthlyRevenue.containsKey(month)) {
      monthlyRevenue.put(month, monthlyRevenue.get(month) + fee);
    } else {
      monthlyRevenue.put(month, fee);
    }
  }

  /**
   * @return The hashmap of monthy revenue for the system
   */
  public static HashMap<YearMonth, Integer> getMonthlyRevenue() {
    return monthlyRevenue;
  }
}
