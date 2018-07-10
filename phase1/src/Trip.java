import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

/** Represents an object of Trip */
public class Trip {
  /** The maximum duration between the start of two continuous trips */
  static final Duration MAXTRIPLENGTH = Duration.ofMinutes(120);
  /** The time started by this trip */
  LocalDateTime timeStarted;
  /** The time ended by this trip */
  LocalDateTime timeEnded;
  /** The stops made along the trip before reaching the final station */
  ArrayList<Station> priorStops = new ArrayList<>();
  /** The station ended by this trip */
  Station endStation;
  /** The current fee of this trip */
  int tripFee;
  /** The maximum fee charged by this trip */
  private int maxFee = 600;
  /** The fee this trip charges itself per station travelled */
  private int perStationFee;
  /** Whether this trip is valid */
  private boolean isValidTrip = true;

  /**
   * Construct a new instance of Trip
   *
   * @param startTime Time which this trip is started
   * @param station Station which this trip is started
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

  /** @return The current fee of this trip */
  int getFee() {
    if (tripFee < maxFee) {
      return tripFee;
    } else {
      return maxFee;
    }
  }

  /** @return Whether this is a valid trip */
  public boolean isValidTrip() {
    return isValidTrip;
  }
  /**
   * Checks if a trip continues another trip
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
    priorStops.add(station);
    endStation = null;
    timeEnded = null;
    maxFee -= tripFee;
    tripFee = station.getInitialFee();
    perStationFee = station.perStationFee;
  }

  /** @return The StartStation for this Trip */
  Station getStartStation() {
    return priorStops.get(0);
  }

  /** @return The start time for this Trip */
  public LocalDateTime getTimeStarted() {
    return timeStarted;
  }

  /**
   * Updates and returns the fee of this trip, assuming this trip has ended
   *
   * @return the per-station fare for this ride
   */
  public int getFinalFee() {
    Integer firstStation = null;
    Integer secondStation = null;

    // Loop through all the routes to find the start and end station
    for (Route<Station> route : Route.getRoutes()) {
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
      // If one of the two stations was not found in this route
      if (firstStation == null || secondStation == null) {
        firstStation = null;
        secondStation = null;
      }
    }
    // If no route contains both stations
    if (firstStation == null && secondStation == null) {
      isValidTrip = false;
      return this.maxFee;
    } else {
      return perStationFee * (Math.abs(secondStation - firstStation));
    }
  }

  /** @return A string representation of a trip */
  public String toString() {
    return "Trip started at " + priorStops.get(0) +  " and ended at " + endStation;
  }
}
