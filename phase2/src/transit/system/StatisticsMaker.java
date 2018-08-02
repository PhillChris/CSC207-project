package transit.system;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/** A class for making money calculations and generating daily reports. */
public class StatisticsMaker implements Serializable {
  /** Contains the system revenue per month */
  private static HashMap<YearMonth, Integer> monthlyRevenue = new HashMap<>();
  /** Contains the system revenue per day */
  private static HashMap<LocalDate, Integer> dailyRevenue = new HashMap<>();
  /** Contains the number of stations travelled per day by users */
  private static HashMap<LocalDate, Integer> dailyLog = new HashMap<>();
  /** Contains the number of trips made by users */
  private static HashMap<LocalDate, Integer> dailyNumTrips = new HashMap<>();
  /** Contains the dates in this program*/
  private static ArrayList<LocalDate> dates = new ArrayList<>();

  /** @return The daily report table for all days passed during this simulation. */
  public static String generateReportMessage() {
    // Loop through all days and write each day's revenue
    // The revenue collected for each day
    HashMap<LocalDate, Integer> dailyTotals = dailyRevenue;
    // List of all the dates collected
    List<LocalDate> datesTemp = new ArrayList<LocalDate>(dates);
    datesTemp.sort(Collections.reverseOrder());
    String message = System.lineSeparator(); // The message to be outputted to Daily Reports
    for (LocalDate day : datesTemp) {
      String date = day.toString();
      double revenue = dailyRevenue.get(day) / 100.0;
      int travelled = dailyLog.get(day);
      message +=
          String.format("%s   $%.2f     %s%s", date, revenue, travelled, System.lineSeparator());
    }
    message += "Total stations travelled: " + totalLegLength() + System.lineSeparator();
    message += "Average stations travelled: " + averageLegLength() + System.lineSeparator();
    return message;
  }

  public static HashMap<YearMonth, Integer> getMonthlyRevenueCopy() {
    return new HashMap<>(monthlyRevenue);
  }

  public static HashMap<LocalDate, Integer> getDailyRevenueCopy() {
    return new HashMap<>(dailyRevenue);
  }

  public static HashMap<LocalDate, Integer> getDailyLogCopy() {
    return new HashMap<>(dailyLog);
  }

  public static HashMap<LocalDate, Integer> getDailyNumTripsCopy() {
    return new HashMap<>(dailyNumTrips);
  }

  public static ArrayList<LocalDate> getDatesCopy() {
    return new ArrayList<>(dates);
  }

  public static void setMonthlyRevenue(HashMap<YearMonth, Integer> newMonthlyRevenue) {
    monthlyRevenue = newMonthlyRevenue;
  }

  public static void setDailyRevenue(HashMap<LocalDate, Integer> newDailyRevenue) {
    dailyRevenue = newDailyRevenue;
  }

  public static void setDailyLog(HashMap<LocalDate, Integer> newDailyLog) {
    dailyLog = newDailyLog;
  }

  public static void setDailyNumTrips(HashMap<LocalDate, Integer> newDailyNumTrips) {
    dailyNumTrips = newDailyNumTrips;
  }

  public static void setDates(ArrayList<LocalDate> newDates) {
    dates = newDates;
  }


  /** @return the average number of stations traveled per trip in the given day */
  public static double averageLegLength() {
    if (dailyNumTrips.get(TransitTime.getCurrentDate()) != null) {
      return dailyLog.get(TransitTime.getCurrentDate())
          / dailyNumTrips.get(TransitTime.getCurrentDate());
    }
    return 0.0;
  }

  /** @return the total number of stations traveled in the current day */
  public static double totalLegLength() {
    if (dailyLog.get(TransitTime.getCurrentDate()) != null) {
      return dailyLog.get(TransitTime.getCurrentDate());
    }
    return 0.0;
  }

  /** Updates the expenditure and revenue in the entire system
   *
   * @param fee the fees having been charged for this system for this past trip
   * @param tripLength the length of this trip in stations
   */
  void updateSystemStats(int fee, int tripLength) {
    LocalDate date = TransitTime.getCurrentDate();
    YearMonth month = YearMonth.of(date.getYear(), date.getMonth());

    if (dates.contains(date)) {
      monthlyRevenue.put(YearMonth.from(date), monthlyRevenue.get(YearMonth.from(date)) + fee);
      dailyRevenue.put(date, dailyRevenue.get(date) + fee);
      dailyLog.put(date, dailyLog.get(date) + tripLength);
      dailyNumTrips.put(date, dailyNumTrips.get(date) + 1);

    } else {
      dates.add(date);
      monthlyRevenue.put(YearMonth.from(date), fee);
      dailyRevenue.put(date, fee);
      dailyLog.put(date, tripLength);
      dailyNumTrips.put(date, 1);
    }
  }
}
