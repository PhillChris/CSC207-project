import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class TransitTime {
  /** The current year of this simulation */
  static int currentYear;

  /** The current month of this simulation */
  static Month currentMonth;

  /** The current day of this simulation */
  static int currentDay;

  /** The current time of the simulation */
  static LocalDateTime currentTime;

  /**
   * Initializes the date in the transit system. Sets system time to midnight of the first date by
   * default.
   *
   * @param initialDate the initial date of the transit system simulation
   */
  static void initDate(String initialDate) {
    String[] formatted = initialDate.split("-");
    currentYear = Integer.parseInt(formatted[0]);
    currentMonth = Month.of(Integer.parseInt(formatted[1]));
    currentDay = Integer.parseInt(formatted[2]);
    currentTime = LocalDateTime.of(currentYear, currentMonth, currentDay, 0, 0);
  }

  /**
   * Reads, check and updates the time of the simulation
   *
   * @param time The time inputted by the user
   * @return The updated time or null if invalid input was given
   */
  static LocalDateTime getTime(String time) throws TimeException {
    String[] formatted = time.split(":");
    LocalDateTime newTime =
        LocalDateTime.of(
            currentYear,
            currentMonth,
            currentDay,
            Integer.parseInt(formatted[0]),
            Integer.parseInt(formatted[1]));

    if (currentTime == null) {
      currentTime = newTime;
      return currentTime;
    } else {
      if (currentTime.isAfter(newTime)) {
        throw new TimeException();
      } else {
        currentTime = newTime;
        return currentTime;
      }
    }
  }

  /**
   * Ends a day in the simulation
   *
   * @param emptyList Information given by the user
   */
  static void endDay(List<String> emptyList) {
    if (currentDay < currentMonth.length(false)) {
      currentDay++;
    } else {
      currentDay = 1;
      if (currentMonth.getValue() < 12) {
        currentMonth = Month.of(currentMonth.getValue() + 1);
      } else {
        currentMonth = Month.of(1);
        currentYear++;
      }
    }
    TransitReadWrite.write(
        "Day ended successfully: current day is "
            + currentMonth.toString()
            + " "
            + +currentDay
            + ", "
            + currentYear);
  }

  public static LocalDate getCurrentDate() {
    return currentTime.toLocalDate();
  }
}
