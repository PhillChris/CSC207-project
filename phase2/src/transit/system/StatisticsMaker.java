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

  /** @return The daily report table for all days passed during this simulation. */
  public static String generateReportMessage() {
    // Loop through all days and write each day's revenue
    // The revenue collected for each day
    HashMap<LocalDate, Integer> dailyTotals = dailyRevenue;
    // List of all the dates collected
    List<LocalDate> dates = new ArrayList<LocalDate>(dailyTotals.keySet());
    dates.sort(Collections.reverseOrder());
    String message = System.lineSeparator(); // The message to be outputted to Daily Reports
    for (LocalDate day : dates) {
      String date = day.toString();
      double revenue = dailyRevenue.get(day) / 100.0;
      int travelled = dailyLog.get(day);
      message +=
          String.format("%s   $%.2f     %s%s", date, revenue, travelled, System.lineSeparator());
    }
    return message;
  }

  public static HashMap<YearMonth, Integer> getMonthlyRevenue() {
    return monthlyRevenue;
  }

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

  /** @return a station hash map, with all logically equivalent stations */
  public static HashMap<Station, ArrayList<Integer>> makeStationsMap(String type) {
    HashMap<Station, ArrayList<Integer>> temp = new HashMap<>();
    if (Route.getRoutesCopy().get(type) != null) {
      for (Route route: Route.getRoutesCopy().get(type)) {
        for (Station station: route.getRouteStationsCopy()) {
          ArrayList<Integer> newStats;
          if (findStation(temp, station) != null) {
            newStats = refreshStats(temp, station, findStation(temp, station));
            temp.replace(findStation(temp, station), newStats);
          } else {
            newStats = makeNewStats(station);
            temp.put(station, newStats);
          }

        }
      }
    }
    return temp;
  }

  /**
   * Refreshed the list of statistics kept in stations
   *
   * @param temp the hashmap of station stats being generated
   * @param station the station whose stats are being fetched
   * @param matchedStation the station which matched with this station in temp
   * @return the refreshed list of statistics kept in logically equivalent stations
   */
  private static ArrayList<Integer> refreshStats(HashMap<Station, ArrayList<Integer>> temp, Station station, Station matchedStation) {
    ArrayList<Integer> newStats = new ArrayList<>();
    newStats.add(0,temp.get(matchedStation).get(0) + station.getTapsOn(TransitTime.getCurrentDate()));
    newStats.add(1, temp.get(matchedStation).get(1) + station.getTapsOff(TransitTime.getCurrentDate()));
    return newStats;
  }

  /**
   * Creates a new arrayList of statistics kep track of in stations
   *
   * @param station the station whose stats are being fetched
   * @return the new list of statistics kept in logically equivalent stations
   */
  private static ArrayList<Integer> makeNewStats(Station station) {
    ArrayList<Integer> newStats = new ArrayList<>();
    newStats.add(0, station.getTapsOn(TransitTime.getCurrentDate()));
    newStats.add(1, station.getTapsOff(TransitTime.getCurrentDate()));
    return newStats;
  }

  private static Station findStation(HashMap<Station, ArrayList<Integer>> temp, Station station) {
    for (Station s: temp.keySet()) {
      if (s.equals(station)) {
        return s;
      }
    }
    return null;
  }
}
