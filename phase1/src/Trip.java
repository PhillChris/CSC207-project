import java.time.Duration;
import java.time.LocalDate;

/** Represents an object of Trip */
public class Trip {
  static final int MAXFEE = 600;
  static final Duration MAXTRIPLENGTH = Duration.ofMinutes(120);
  LocalDate timeStarted;
  LocalDate timeEnded;
  Station startStation;
  Station endStation;
  int tripFee;

  /**
   * Construct a new instance of Trip
   *
   * @param startTime Time which the trip is started
   * @param station Station which the trip is started
   */
  public Trip(LocalDate startTime, Station station) {
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
  void endTrip(Station station, LocalDate endTime) {
    endStation = station;
    timeEnded = endTime;
    if (!isValidTrip(station, endTime)) {
      tripFee = MAXFEE;
    } else {
      tripFee += station.getFinalFee(startStation);
    }
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
  boolean isValidTrip(Station newStation, LocalDate time) {
    boolean withinTimeLimit =
            Duration.between(timeStarted, time).toMinutes() <= (MAXTRIPLENGTH.toMinutes());
    return (endStation == newStation) && (withinTimeLimit);
  }

  /**
   * Checks if a trip is continuous. If true, then this trip may be continued from the endStation.
   *
   * @param newStation the station that is being checked for being a continuous trip.
   * @param time       the time the newStation is being checked into.
   */
  public boolean isContinuousTrip(Station newStation, LocalDate time) {
    boolean withinTimeLimit =
            Duration.between(timeStarted, time).toMinutes() <= (MAXTRIPLENGTH.toMinutes());
    return (this.endStation.isAssociatedStation(endStation) && withinTimeLimit);
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

  public LocalDate getTimeStarted() {
    return timeStarted;
  }
}
