import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;

public class CostCalculator {

  /** Contains the expenditure for each method */
  static HashMap<YearMonth, Integer> MonthlyRevenue = new HashMap<>();

  /** Contains the expenditure per each day */
  static HashMap<LocalDate, Integer> DailyRevenue = new HashMap<>();

  /** Updates the expenditure and revenue in the entire system */
  public static void update() {
    updateUserRevenue();
    updateSystemRevenue();
  }

  /** @return A HashMap containing the expenditure per each day */
  public static HashMap<LocalDate, Integer> getDailyRevenue() {
    return DailyRevenue;
  }

  /** Updates the Daily and Monthly Expenditure of each User in the System */
  private static void updateUserRevenue() {
    // Get the current date and month
    LocalDate date = TransitTime.getCurrentDate();
    YearMonth month = YearMonth.of(date.getYear(), date.getMonth());

    // Loop through all the Users in the System
    for (String userName : CardHolder.getAllUsers().keySet()) {
      CardHolder user = CardHolder.getAllUsers().get(userName);

      List<Card> cards = user.getCards(); // Users' cards
      int monthlyCost = 0;
      int dailyCost = 0;

      // Loop through all cards
      for (Card card : cards) {
        for (Trip trip : card.getAllTrips()) {
          LocalDateTime tripStart = trip.getTimeStarted();
          YearMonth tripMonthYear = YearMonth.of(tripStart.getYear(), tripStart.getMonth());
          // If the Trip occured in the current month
          if (month.equals(tripMonthYear)) {
            monthlyCost += trip.getFee();
          }
          // If the Trip occured in the current day
          if (date.equals(tripStart)) {
            dailyCost += trip.getFee();
          }
        }
      }
      // Update User's HashMap
      user.addMonthlyFee(monthlyCost);
      user.addDailyFee(dailyCost);
    }
  }

  /** Updates the Daily and Monthly Revenue by the Transit System */
  private static void updateSystemRevenue() {
    // Today's date and month
    LocalDate date = TransitTime.getCurrentDate();
    YearMonth month = YearMonth.of(date.getYear(), date.getMonth());
    int Daily = 0; // Revenue earned today from all CardHolders
    int Monthly = 0; // Revenue earned this month

    // Loop through all the user's
    for (String userName : CardHolder.getAllUsers().keySet()) {
      CardHolder user = CardHolder.getAllUsers().get(userName);
      Daily += user.ExpenditureDaily.get(date);
      Monthly += user.ExpenditureMonthly.get(month);
    }
    DailyRevenue.put(TransitTime.getCurrentDate(), Daily);
    if (MonthlyRevenue.keySet().contains(month)) {
      int currentRevenue = MonthlyRevenue.get(month);
      MonthlyRevenue.put(month, Monthly + currentRevenue);
    } else {
      MonthlyRevenue.put(month, Monthly);
    }
  }
}
