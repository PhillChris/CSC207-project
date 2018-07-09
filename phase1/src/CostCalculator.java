import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;

public class CostCalculator {

  /** Contains the expenditure for each method */
  private static HashMap<YearMonth, Integer> MonthlyRevenue = new HashMap<>();

  /** Contains the expenditure per each day */
  private static HashMap<LocalDate, Integer> DailyRevenue = new HashMap<>();

  /** Updates the expenditure and revenue in the entire system */
  public static void updateSystemRevenue(int fee) {
    updateDaily(fee);
    updateMonthly(fee);
  }

  /** Updates the expenditure and revenue in the entire system */
  private static void updateDaily(int fee) {
    LocalDate date = TransitTime.getCurrentDate();
    YearMonth month = YearMonth.of(date.getYear(), date.getMonth());

    if (DailyRevenue.containsKey(date)) {
      DailyRevenue.put(date, DailyRevenue.get(date) + fee);
    } else {
      DailyRevenue.put(date, fee);
    }
  }

  private static void updateMonthly(int fee) {
    LocalDate date = TransitTime.getCurrentDate();
    YearMonth month = YearMonth.of(date.getYear(), date.getMonth());

    if (MonthlyRevenue.containsKey(date)) {
      MonthlyRevenue.put(month, MonthlyRevenue.get(date) + fee);
    } else {
      MonthlyRevenue.put(month, fee);
    }
  }

  /** @return A HashMap containing the expenditure per each day */
  public static HashMap<LocalDate, Integer> getDailyRevenue() {
    return DailyRevenue;
  }
}