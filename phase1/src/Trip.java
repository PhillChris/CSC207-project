import java.util.Date;

/** Represents an object of Trip */
public class Trip {
  static final double MAXFEE = 6.0;
  static final double MAXTRIPLENGTH = 2.0;
  Date timeStarted;
  Date timeEnded;
  Station startStation;
  Station endStation;
  double tripFee;

  /**
   * Construct a new instance of Trip
   *
   * @param startTime Time which the trip is started
   * @param station Station which the trip is started
   */
  public Trip(Date startTime, Station station) {
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
  void endTrip(Station station, Date endTime) {
    endStation = station;
    timeEnded = endTime;
    tripFee += station.getFinalFee(startStation);
  }

  double getFee() {
    if (tripFee < MAXFEE) {
      return tripFee;
    } else {
      return MAXFEE;
    }
  }

  /**
   * @param newStation new station
   * @param time time for the start of the continuation
   * @return true if continuous trip, false otherwise
   */
  boolean isContinuousTrip(Station newStation, Date time) {
    if (TransitSystem.routes.get(startStation.getRoute()).contains(newStation)
        && timeStarted.getDate() == time.getDate()
        && (time.getHours() - timeStarted.getHours() < MAXTRIPLENGTH)) {
      return true;
    } else {
      return false;
    }
  }

  /** @return The StartStation for this Trip */
  Station getStartStation() {
    return startStation;
  }
}
