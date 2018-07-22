import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/** A class keeping track of universal time in the transit system */
public class TransitTime {

  /** The current time of the transit system */
  private static LocalDateTime currentTime = LocalDateTime.now();

  /** Whether the time is currently moving forward */
  private static boolean running = true;

  public static void updateTime() {
    if (running) {
      currentTime = currentTime.plusMinutes(5);
    }
  }

  public static String getCurrentTime(){
    String time = "Current Day: " + currentTime.getMonth().toString() + " " + currentTime.getDayOfMonth() + ", ";
    time += currentTime.getYear() + System.lineSeparator();
    time += "Time: " + currentTime.getHour() + ":" + currentTime.getMinute();
    return time;
  }

  /**
   * Ends a day in the simulation
   *
   * @param emptyList Information given by the user
   */
  static void endDay(List<String> emptyList) {}

  /** @return The current date in the transit system */
  static LocalDate getCurrentDate() {
    return currentTime.toLocalDate();
  }
}
