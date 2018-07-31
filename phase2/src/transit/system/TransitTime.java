package transit.system;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javafx.scene.paint.Color.BLACK;

/** A class keeping track of universal time in the transit system */
public class TransitTime {

  /** A label designed to represent to current time of this simulation */
  private static Label time = new Label();
  /** The current time of the transit system */
  private static LocalDateTime currentTime = LocalDateTime.now();
  /** Whether the time is currently moving forward */
  private static boolean running = true;

  /** @return The current time of the simulation */
  public static LocalDateTime getCurrentTime() {
    return TransitTime.currentTime;
  }

  /**
   * Set the currentTime to the given parameter
   *
   * @param time the time to set the currentTime to.
   */
  static void setCurrentTime(LocalDateTime time) {
    TransitTime.currentTime = time;
  }

  /** @return The current date in the transit system */
  public static LocalDate getCurrentDate() {
    return currentTime.toLocalDate();
  }

  /** @return A label describing the current time of the simulation */
  public static Label getTimeLabel() {
    return time;
  }

  /** Pauses the simulation time */
  public static void pauseTime() {
    running = false;
  }

  /** Restarts the simulation time */
  public static void startTime() {
    running = true;
  }

  /** Moves the simulation time forward one hour */
  public static void fastForward() {
    currentTime = currentTime.plusMinutes(60);
  }

  /** Moves the simulation time forward one day */
  public static void skipDay() {
    currentTime = currentTime.plusDays(1);
  }

  /** Moves the simulation time forward one month */
  public static void skipMonth() {
    currentTime = currentTime.plusMonths(1);
  }

  /** Updates the time label as time progresses */
  protected static void updateTimeLabel() {
    Timeline timeline = new Timeline();
    timeline.setCycleCount(Timeline.INDEFINITE);

    KeyFrame frame =
        new KeyFrame(
            Duration.seconds(1),
            new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent event) {
                TransitTime.updateTime();
                time.setTextFill(BLACK);
                time.setText(TransitTime.getCurrentTimeString());
              }
            });
    timeline.getKeyFrames().add(frame);
    timeline.playFromStart();
  }

  /** Increments the time of the simulation */
  private static void updateTime() {
    if (running) {
      currentTime = currentTime.plusMinutes(1);
    }
  }

  /** @return A string formatting of the current simulation time */
  public static String getCurrentTimeString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    String time = formatter.format(currentTime.toLocalTime());
    LocalDate date = currentTime.toLocalDate();
    String currentTime =
        String.format(
            "Current Day: %s %s, %s", date.getMonth(), date.getDayOfMonth(), date.getYear());
    currentTime += System.lineSeparator();
    currentTime += String.format("Time: %s", time);
    return currentTime;
  }
}
