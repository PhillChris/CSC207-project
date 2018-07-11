import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

/** A class keeping track of universal time in the transit system */
public class TransitTime {

  /** The current time of the transit system */
  private static LocalDateTime currentTime;

  /** The parser currently in use for this transit system */
  private static ObjectParser currentParser;

  /**
   * Initializes the date in the transit system. Sets system time to midnight of the first date by
   * default.
   *
   * @param initialDate the initial date of the transit system simulation
   */
  static void initDate(String initialDate, ObjectParser parser) {
    String[] formatted = initialDate.split("-");
    int currentYear = Integer.parseInt(formatted[0]);
    Month currentMonth = Month.of(Integer.parseInt(formatted[1]));
    int currentDay = Integer.parseInt(formatted[2]);
    currentTime = LocalDateTime.of(currentYear, currentMonth, currentDay, 0, 0);
    CostCalculator calculator = new CostCalculator();
    calculator.updateSystemRevenue(0, 0);
    currentParser = parser;
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
    currentParser.write(
        "Day ended successfully: current day is "
            + currentTime.getMonth()
            + " "
            + +currentTime.getDayOfMonth()
            + ", "
            + currentTime.getYear());

    CostCalculator calculator = new CostCalculator();
    calculator.updateSystemRevenue(0, 0);
  }

  /** @return The current date in the transit system */
  static LocalDate getCurrentDate() {
    return currentTime.toLocalDate();
  }
}
