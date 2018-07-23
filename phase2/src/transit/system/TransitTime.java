package transit.system;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.time.format.DateTimeFormatter;

/** A class keeping track of universal time in the transit system */
public class TransitTime {

  /** The current time of the transit system */
  private static LocalDateTime currentTime = LocalDateTime.now();

  /** Whether the time is currently moving forward */
  private static boolean running = true;

  public static void updateTime() {
    if (running) {
      currentTime = currentTime.plusMinutes(1);
    }
  }

  public static String getCurrentTime(){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    String time = formatter.format(currentTime.toLocalTime());
    LocalDate date = currentTime.toLocalDate();
    String currentTime = String.format("Current Day: %s %s, %s", date.getMonth(), date.getDayOfMonth(), date.getYear());
    currentTime += System.lineSeparator();
    currentTime += String.format("Time: %s", time);
    return currentTime;
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
