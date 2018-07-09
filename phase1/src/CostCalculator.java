import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CostCalculator {

  /** Contains the expenditure for each method */
  static HashMap<YearMonth, Integer> MonthlyRevenue = new HashMap<>();

  /** Contains the expenditure per each day */
  static HashMap<LocalDate, Integer> DailyRevenue = new HashMap<>();

  /**
   * Return a hashmap mapping a list of trip costs associated with a list of cards to the month/year
   * they were taken.
   *
   * @param cards
   * @return Hashmap with lists of trip costs for a given month/year
   */
  public static void updateUserRevenue(CardHolder user) {
    // Get the current date and month
    LocalDate date = TransitTime.getCurrentDate();
    YearMonth month = YearMonth.of(date.getYear(), date.getMonth());

    List<Card> cards = user.getCards(); // Users cards
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
        if (date.equals(tripStart)){
          dailyCost += trip.getFee();
        }
      }
    }
    // Update User's HashMap
    user.addMonthlyFee(monthlyCost);
    user.addDailyFee(dailyCost);
  }


  /**
   * Return a hashmap mapping the total revenue collected from a list of CardHolders to each day.
   *
   * @param cardHolders
   * @return
   */
  public static void  updateSystemRevenue(
      HashMap<String, CardHolder> cardHolders) {
    int Daily = 0; // Revenue earned today from all CardHolders
    int Monthy = 0; // Revenue earned t
    for (String userName : CardHolder.getAllUsers().keySet()) {
      CardHolder user = CardHolder.getAllUsers().get(userName);
      Daily += user.ExpenditureDaily.get(TransitTime.getCurrentDate());
    }
    DailyRevenue.put(TransitTime.getCurrentDate(), Daily);
  }
  /**
   * Sum the entries of an arraylist containing integers.
   *
   * @param array
   * @return
   */
  public static int sumArrayList(List<Integer> array) {
    int sum = 0;
    for (Integer i : array) {
      sum += i;
    }
    return sum;
  }
}
