import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CostCalculator {

  /** Contains the expenditure for each method */
  static HashMap<YearMonth, List<Integer>> MonthlyRevenue = new HashMap<>();

  /** Contains the expenditure per each day */
  static HashMap<LocalDateTime, List<Integer>> DailyRevenue = new HashMap<>();

  /**
   * Return a hashmap mapping a list of trip costs associated with a list of cards to the month/year
   * they were taken.
   *
   * @param cards
   * @return Hashmap with lists of trip costs for a given month/year
   */
  public static void updateUserMonthlyCost(CardHolder user) {
    LocalDate date = TransitTime.getCurrentDate();
    YearMonth month = YearMonth.of(date.getYear(), date.getMonth());
    List<Card> cards = user.getCards();
    int monthlyCost = 0;
    // Loop through all cards
    for (Card card : cards) {
      for (Trip trip : card.getAllTrips()) {
        LocalDateTime tripStart = trip.getTimeStarted();
        YearMonth tripMonthYear = YearMonth.of(tripStart.getYear(), tripStart.getMonth());
        if (month.equals(tripMonthYear)) {
          monthlyCost += trip.getFee();
        }
      }
    }
    // Update User's HashMap
    user.addMonthlyFee(monthlyCost);
  }

  /**
   * Return a hashmap mapping a list of trip costs associated with a list of cards to the day they
   * were taken.
   *
   * @param cards
   * @return Hashmap with lists of trip costs for a given day
   */
  public static HashMap<LocalDateTime, List<Integer>> getTripsByDay(List<Card> cards) {
    HashMap<LocalDateTime, List<Integer>> tripsByDay = new HashMap<LocalDateTime, List<Integer>>();
    for (Card card : cards) {
      for (Trip trip : card.getAllTrips()) {
        LocalDateTime tripDate = trip.getTimeStarted();
        if (!tripsByDay.containsKey(tripDate)) {
          tripsByDay.put(tripDate, new ArrayList<Integer>());
        }
        tripsByDay.get(tripDate).add(trip.getFee());
      }
    }
    return tripsByDay;
  }

  /**
   * Return a hashmap mapping the total spent in each month to month.
   *
   * @param costMap
   * @return
   */
  public static HashMap<YearMonth, Integer> getMonthlyTotals(
      HashMap<YearMonth, List<Integer>> costMap) {
    HashMap<YearMonth, Integer> monthlyTotal = new HashMap<YearMonth, Integer>();
    for (Map.Entry<YearMonth, List<Integer>> entry : costMap.entrySet()) {
      YearMonth month = entry.getKey();
      List<Integer> costs = entry.getValue();
      Integer totalCost = sumArrayList(costs);
      monthlyTotal.put(month, totalCost);
    }
    return monthlyTotal;
  }

  /**
   * Return a hashmap mapping the total spent on each day to day
   *
   * @param costMap
   * @return
   */
  public static HashMap<LocalDateTime, Integer> getDailyTotals(
      HashMap<LocalDateTime, List<Integer>> costMap) {
    HashMap<LocalDateTime, Integer> monthlyTotal = new HashMap<LocalDateTime, Integer>();
    for (Map.Entry<LocalDateTime, List<Integer>> entry : costMap.entrySet()) {
      LocalDateTime date = entry.getKey();
      List<Integer> costs = entry.getValue();
      Integer totalCost = sumArrayList(costs);
      monthlyTotal.put(date, totalCost);
    }
    return monthlyTotal;
  }

  /**
   * Return a hashmap mapping the total revenue collected from a list of CardHolders to each day.
   *
   * @param cardHolders
   * @return
   */
  public static HashMap<LocalDateTime, Integer> allUsersDailyTotals(
      HashMap<String, CardHolder> cardHolders) {
    List<Card> allCards = new ArrayList<Card>();
    for (CardHolder cardHolder : cardHolders.values()) {
      allCards.addAll(cardHolder.getCards());
    }
    HashMap<LocalDateTime, List<Integer>> allUsersCosts = getTripsByDay(allCards);

    return getDailyTotals(allUsersCosts);
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
