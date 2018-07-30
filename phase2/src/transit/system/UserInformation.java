package transit.system;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** This class is designed to track all statistics related to a user */
public class UserInformation {
  /** The user assoicated with these statistics */
  private User user;
  /** Log of all tap ins for each day */
  private HashMap<LocalDate, Integer> tapInLog;
  /** A log of taps mapping a given date to the number of taps out recorded */
  private HashMap<LocalDate, Integer> tapOutLog;
  /** An ArrayList of this transit.system.User's last three trips */
  private ArrayList<Trip> previousTrips;
  /** HashMap linking each month to the total expenditure for that month */
  private HashMap<YearMonth, Integer> expenditureMonthly = new HashMap<>();
  /** Calculates and sends the daily revenue recieved from this user to the system */
  private StatisticsMaker calculator;

  /** Initializes a new instance of UserInformation */
  UserInformation() {
    tapInLog = new HashMap<>();
    tapOutLog = new HashMap<>();
    previousTrips = new ArrayList<>();
    calculator = new StatisticsMaker();
  }

  /** @return The previousTrips of this user */
  ArrayList<Trip> getPreviousTrips() {
    return previousTrips;
  }

  /** @return The monthly expenditure for this user */
  HashMap<YearMonth, Integer> getMonthlyExpenditure() {
    return expenditureMonthly;
  }

  /** @return A String detailing average expenditure per month of this transit.system.User. */
  String avgMonthlyMessage() {
    String message = "Cost per month for user: " + this.user.getUserName() + System.lineSeparator();
    List<YearMonth> months = new ArrayList<>(this.expenditureMonthly.keySet());
    for (YearMonth month : months) {
      message +=
          month.toString()
              + " : "
              + "$"
              + String.format(
                  "%.2f", expenditureMonthly.get(month) / (month.lengthOfMonth() * 100.0));
      message += System.lineSeparator();
    }
    return message.trim();
  }

  /** @return A string representation of the last three trips travelled by the user */
  String lastThreeTripsMessage() {
    String message = "Last 3 trips by user " + this.user.getUserName() + ":";
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

  /**
   * @param date A given date in the simulation
   * @return The number of TapOuts by the user on that given date
   */
  int totalTapOuts(LocalDate date) {
    if (tapOutLog.get(date) != null) {
      return tapOutLog.get(date);
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

  /** Records a new Tap Out for the current day */
  void recordTapOut() {
    LocalDate timeTapped = TransitTime.getCurrentDate();
    if (tapOutLog.get(timeTapped) != null) {
      tapOutLog.put(timeTapped, tapOutLog.get(timeTapped) + 1);
    } else {
      tapOutLog.put(timeTapped, 1);
    }
  }

  /**
   * Updates the spending history for this transit.system.User
   *
   * @param card The card most recently used by the transit.system.User
   */
  void updateSpendingHistory(Card card) {
    LocalDate date = TransitTime.getCurrentDate();
    YearMonth month = YearMonth.of(date.getYear(), date.getMonth());
    Trip lastTrip = card.getLastTrip();

    // Update user's personal monthly expenditure History
    if (expenditureMonthly.containsKey(month)) {
      expenditureMonthly.put(month, expenditureMonthly.get(month) + lastTrip.getFee());
    } else {
      expenditureMonthly.put(month, lastTrip.getFee());
    }
    // Update system's expenditure history
    calculator.updateSystemStats(lastTrip.getFee(), Math.max(lastTrip.getTripLegLength(), 0));
  }
}
