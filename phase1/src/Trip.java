import java.time.Duration;
import java.time.LocalDateTime;

/** Represents an object of Trip */
public class Trip {
  static final int MAXFEE = 600;
  static final Duration MAXTRIPLENGTH = Duration.ofMinutes(120);
  LocalDateTime timeStarted;
  LocalDateTime timeEnded;
  Station startStation;
  Station endStation;
  int tripFee;

  /**
   * Construct a new instance of Trip
   *
   * @param startTime Time which the trip is started
   * @param station Station which the trip is started
   */
  public Trip(LocalDateTime startTime, Station station) {
    timeStarted = startTime;
    startStation = station;
    tripFee = station.getInitialFee();
  }

  /**
   * End this Trip
   *
   * @param station the station where this Trip ends
   * @param endTime the station where this Trip continuous
   */
  void endTrip(Station station, LocalDateTime endTime) {
    endStation = station;
    timeEnded = endTime;
    tripFee += station.getFinalFee(startStation);
  }

  int getFee() {
    if (tripFee < MAXFEE) {
      return tripFee;
    } else {
      return MAXFEE;
    }
  }

  /**
   * Checks if a trip is valid, provided it ends at newStation.
   *
   * @param newStation new station
   * @param time time for the start of the continuation
   * @return true if continuous trip, false otherwise
   */
  public boolean isContinuousTrip(Station newStation, LocalDateTime time) {
    boolean withinTimeLimit =
        Duration.between(timeStarted, time).toMinutes() <= (MAXTRIPLENGTH.toMinutes());
    return (this.endStation.isAssociatedStation(endStation) && withinTimeLimit);
    // TODO: switch endStation argument to newStation
  }

  /**
   * Set the endStation and timeEnded to none when the trip is being continued. Continue this trip
   * from station.
   *
   * @param station the station that the trip is being continued from.
   */
  void continueTrip(Station station) {
    endStation = null;
    timeEnded = null;
    tripFee += station.getInitialFee();
  }

  /** @return The StartStation for this Trip */
  Station getStartStation() {
    return startStation;
  }

  public LocalDateTime getTimeStarted() {
    return timeStarted;
  }
}
