import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

/** Represents an object of Trip */
public class Trip {
  static final int MAXFEE = 600;
  static final Duration MAXTRIPLENGTH = Duration.ofMinutes(120);
  LocalDateTime timeStarted;
  LocalDateTime timeEnded;
  ArrayList<Station> priorStops = new ArrayList<>();
  Station endStation;
  int tripFee;
  private int perStationFee;
  private boolean isValidTrip = true;

  /**
   * Construct a new instance of Trip
   *
   * @param startTime Time which the trip is started
   * @param station Station which the trip is started
   */
  public Trip(LocalDateTime startTime, Station station) {
    timeStarted = startTime;
    tripFee = station.getInitialFee();
    perStationFee = station.perStationFee;
    priorStops.add(station);
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
    tripFee += getFinalFee();
  }

  int getFee() {
    if (tripFee < MAXFEE) {
      return tripFee;
    } else {
      return MAXFEE;
    }
  }

  public boolean isValidTrip() {
    return isValidTrip;
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
    return (this.endStation.isAssociatedStation(newStation) && withinTimeLimit);
  }

  /**
   * Set the endStation and timeEnded to none when the trip is being continued. Continue this trip
   * from station.
   *
   * @param station the station that the trip is being continued from.
   */
  void continueTrip(Station station) {
    priorStops.add(endStation);
    endStation = null;
    timeEnded = null;
    tripFee += station.getInitialFee();
    perStationFee = station.perStationFee;
  }

  /** @return The StartStation for this Trip */
  Station getStartStation() {
    return priorStops.get(0);
  }

  public LocalDateTime getTimeStarted() {
    return timeStarted;
  }

  /**
   * Called at the end of a ride. NOTE: we can move this to subclasses, and use more specific Route
   * lists to get better runtime. This seems like it sacrifices extensibility though
   *
   * @return the per-station fare for this ride
   */
  public int getFinalFee() {
    Integer firstStation = null;
    Integer secondStation = null;
    for (Route route : Route.getRoutes()) {
      if (firstStation == null && secondStation == null) {
        for (int i = 0; i < route.getRouteStations().size(); i++) {
          Station station = route.getRouteStations().get(i);
          if (station.equals(priorStops.get(priorStops.size() - 1))) {
            firstStation = i;
          }
          if (station.equals(endStation)) {
            secondStation = i;
          }
        }
      }
      if (firstStation == null || secondStation == null) {
        firstStation = null;
        secondStation = null;
      }
    }
    if (firstStation == null && secondStation == null) {
      isValidTrip = false;
      return Trip.MAXFEE;
    } else {
      return perStationFee * (Math.abs(secondStation - firstStation));
    }
  }
}
