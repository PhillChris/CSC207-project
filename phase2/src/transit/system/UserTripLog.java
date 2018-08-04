package transit.system;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import jdk.incubator.http.WebSocket;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observer;

/** This class is designed to track all statistics related to a user */
public class UserTripLog implements Serializable, Statistics<Integer> {
  /** Log of all tap ins for each day */
  private HashMap<LocalDate, Integer> tapInLog;
  /** A log of taps mapping a given date to the number of taps out recorded */
  private ArrayList<Trip> previousTrips;
  /** HashMap linking each month to the total expenditure for that month */
  private StatisticsMaker calculator;

  /** Initializes a new instance of UserTripLog */
  UserTripLog(User user) {
    this.tapInLog = new HashMap<>();
    this.previousTrips = new ArrayList<>();
    this.calculator = new StatisticsMaker();
  }

  /** @return The previousTrips of this user */
  ArrayList<Trip> getPreviousTrips() {
    return previousTrips;
  }

  /** @return A string representation of the last three trips travelled by the user */
  String lastThreeTripsMessage() {
    String message = "Last 3 trips by user ";
    for (int i = 0; i < Math.min(3, previousTrips.size()); i++) {
      Trip t = previousTrips.get(previousTrips.size() - 1 - i);
      message += "\n" + t.toString();
    }
    return message;
  }

  /**
   * @param date A given date in the simulation
   * @return The number of TapIns by the user on that given date
   */
  int totalTapIns(LocalDate date) {
    if (tapInLog.get(date) != null) {
      return tapInLog.get(date);
    }
    return 0;
  }

  /** Records a new Tap In for the current day */
  void recordTapIn() {
    LocalDate timeTapped = TransitTime.getCurrentDate();
    if (tapInLog.get(timeTapped) != null) {
      tapInLog.put(timeTapped, tapInLog.get(timeTapped) + 1);
    } else {
      tapInLog.put(timeTapped, 1);
    }
  }

  @Override
  public HashMap<LocalDate, Integer> generateWeeklyValues() {
    return null;
  }

  @Override
  public HashMap<YearMonth, Integer> generateMonthlyValues() {
    return null;
  }

  @Override
  public void clearLogs() {

  }

  @Override
  public void update(Integer data) {

  }
}
