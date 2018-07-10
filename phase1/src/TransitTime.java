import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class TransitTime {

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
    int currentYear = Integer.parseInt(formatted[0]);
    Month currentMonth = Month.of(Integer.parseInt(formatted[1]));
    int currentDay = Integer.parseInt(formatted[2]);
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
            currentTime.getYear(),
            currentTime.getMonth(),
            currentTime.getDayOfMonth(),
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
    currentTime = currentTime.plusDays(1);
    currentTime =
        LocalDateTime.of(
            currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 0, 0);
    TransitReadWrite.write(
        "Day ended successfully: current day is "
            + currentTime.getMonth().toString()
            + " "
            + +currentTime.getDayOfMonth()
            + ", "
            + currentTime.getYear());
  }

  /** @return The current date in the transit system */
  public static LocalDate getCurrentDate() {
    return currentTime.toLocalDate();
  }
}
