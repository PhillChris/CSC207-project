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
import java.util.List;

import static javafx.scene.paint.Color.BLACK;

/** A class keeping track of universal time in the transit system */
public class TransitTime {

  protected static Label time = new Label();
  /** The current time of the transit system */
  private static LocalDateTime currentTime = LocalDateTime.now();
  /** Whether the time is currently moving forward */
  private static boolean running = true;

  public static Label getTimeLabel(){
    return time;
  }

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

  public static LocalDateTime getCurrentTime() {
    return TransitTime.currentTime;
  }

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

  public static void pauseTime() {
    running = false;
  }

  public static void startTime() {
    running = true;
  }

  public static void fastForward() {
    currentTime = currentTime.plusMinutes(60);
  }

  private static void updateTime() {
    if (running) {
      currentTime = currentTime.plusMinutes(1);
    }
  }

  /**
   * Ends a day in the simulation
   *
   * @param emptyList Information given by the user
   */
  static void endDay(List<String> emptyList) {}

  /** @return The current date in the transit system */
  public static LocalDate getCurrentDate() {
    return currentTime.toLocalDate();
  }
}
