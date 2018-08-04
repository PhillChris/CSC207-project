package transit.system;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import static javafx.scene.paint.Color.BLACK;

/** A class keeping track of universal timeLabel in the transit system */
public class TransitTime implements Serializable {
  /** The clock used by the transit system */
  private static TransitTime clock = setSystemClock();
  /** A label designed to represent to current timeLabel of this simulation */
  private Label timeLabel;
  /** Whether the timeLabel is currently moving forward */
  private boolean running = true;
  /** The timeLabel of this TransitTime */
  private LocalDateTime currentTime;

  /** Initialize a new instance of TransitTime */
  private TransitTime(LocalDateTime time) {
    currentTime = time;
    timeLabel = new Label();
    updateTimeLabel();
  }

  private static TransitTime setSystemClock() {
    LocalDateTime time = (LocalDateTime) Database.readObject(Database.TIME_LOCATION);
    if (time != null) {
      return new TransitTime(time);
    } else {
      return new TransitTime(LocalDateTime.now());
    }
  }

  /** @return The clock used by the system */
  public static TransitTime getClock() {
    return clock;
  }

  /** @return The current timeLabel of the simulation */
  public LocalDateTime getCurrentTime() {
    return TransitTime.clock.currentTime;
  }

  /** @return The current date in the transit system */
  public LocalDate getCurrentDate() {
    return clock.currentTime.toLocalDate();
  }

  /** @return The current month in the transit system */
  public YearMonth getCurrentMonth() {
    YearMonth month = YearMonth.of(clock.currentTime.getYear(), clock.currentTime.getMonth());
    return month;
  }

  /** @return A label describing the current timeLabel of the simulation */
  public Label getTimeLabel() {
    return clock.timeLabel;
  }

  /** Pauses the simulation timeLabel */
  public void pauseTime() {
    clock.running = false;
  }

  /** Restarts the simulation timeLabel */
  public void startTime() {
    clock.running = true;
  }

  /** Moves the simulation timeLabel forward one hour */
  public void skipHours() {
    clock.currentTime = clock.currentTime.plusMinutes(60);
  }

  /** Moves the simulation timeLabel forward one day */
  public void skipDay() {
    clock.currentTime = clock.currentTime.plusDays(1);
  }

  /** Moves the simulation timeLabel forward one month */
  public void skipMonth() {
    clock.currentTime = clock.currentTime.plusMonths(1);
  }

  /** @return Whether the system's clock is running */
  public boolean isRunning() {
    return clock.running;
  }

  /** @return A string formatting of the current simulation timeLabel */
  public String getCurrentTimeString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    String time = formatter.format(clock.currentTime.toLocalTime());
    LocalDate date = clock.currentTime.toLocalDate();
    String currentTime =
        String.format(
            "Current Day: %s %s, %s", date.getMonth(), date.getDayOfMonth(), date.getYear());
    currentTime += System.lineSeparator();
    currentTime += String.format("Time: %s", time);
    return currentTime;
  }

  /** Updates the timeLabel label as timeLabel progresses */
  private void updateTimeLabel() {
    Timeline timeline = new Timeline();
    timeline.setCycleCount(Timeline.INDEFINITE);

    KeyFrame frame =
        new KeyFrame(
            Duration.seconds(1),
            new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent event) {
                updateTime();
                timeLabel.setTextFill(BLACK);
                timeLabel.setText(getCurrentTimeString());
              }
            });
    timeline.getKeyFrames().add(frame);
    timeline.playFromStart();
  }

  /** Increments the timeLabel of the simulation */
  private void updateTime() {
    if (running) {
      currentTime = currentTime.plusMinutes(1);
    }
  }
}
