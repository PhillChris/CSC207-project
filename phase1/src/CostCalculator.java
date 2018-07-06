import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CostCalculator {

  /**
   * Return a hashmap mapping a list of trip costs associated with a list of cards to the month/year
   * they were taken.
   *
   * @param cards
   * @return Hashmap with lists of trip costs for a given month/year
   */
  public static HashMap<YearMonth, List<Integer>> getTripsByMonth(List<Card> cards) {
    HashMap<YearMonth, List<Integer>> tripsByMonth = new HashMap<YearMonth, List<Integer>>();
    for (Card card : cards) {
      for (Trip trip : card.getAllTrips()) {
        LocalDate tripStart = trip.getTimeStarted();
        YearMonth tripMonthYear = YearMonth.of(tripStart.getYear(), tripStart.getMonth());
        if (!tripsByMonth.containsKey(tripMonthYear)) {
          tripsByMonth.put(tripMonthYear, new ArrayList<Integer>());
        }
        tripsByMonth.get(tripMonthYear).add(trip.getFee());
      }
    }
    return tripsByMonth;
  }

  /**
   * Return a hashmap mapping a list of trip costs associated with a list of cards to the day they
   * were taken.
   *
   * @param cards
   * @return Hashmap with lists of trip costs for a given day
   */
  public static HashMap<LocalDate, List<Integer>> getTripsByDay(List<Card> cards) {
    HashMap<LocalDate, List<Integer>> tripsByDay = new HashMap<LocalDate, List<Integer>>();
    for (Card card : cards) {
      for (Trip trip : card.getAllTrips()) {
        LocalDate tripDate = trip.getTimeStarted();
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
  public static HashMap<LocalDate, Integer> getDailyTotals(
      HashMap<LocalDate, List<Integer>> costMap) {
    HashMap<LocalDate, Integer> monthlyTotal = new HashMap<LocalDate, Integer>();
    for (Map.Entry<LocalDate, List<Integer>> entry : costMap.entrySet()) {
      LocalDate date = entry.getKey();
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
  public static HashMap<LocalDate, Integer> allUsersDailyTotals(List<CardHolder> cardHolders) {
    List<Card> allCards = new ArrayList<Card>();
    for (CardHolder cardHolder : cardHolders) {
      allCards.addAll(cardHolder.getCards());
    }
    HashMap<LocalDate, List<Integer>> allUsersCosts = getTripsByDay(allCards);

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
