package transit.system;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

/** Represents an object of transit.system.Trip */
public class Trip {
  /** The maximum duration between the start of two continuous trips */
  private static final Duration MAX_TRIP_LENGTH = Duration.ofMinutes(120);
  /** The time started by this trip */
  private LocalDateTime timeStarted;
  /** The time ended by this trip */
  private LocalDateTime timeEnded;
  /** The stops made along the trip before reaching the final station */
  private ArrayList<Station> priorStops = new ArrayList<>();
  /** The station ended by this trip */
  private Station endStation;
  /** The current fee of this trip */
  private int tripFee;
  /** The maximum fee charged by this trip */
  private int maxFee = 600;
  /** The fee this trip charges itself per station travelled */
  private int perStationFee;
  /** The length of the most recent leg of this trip */
  private int tripLegLength;

  /**
   * Construct a new instance of transit.system.Trip
   *
   * @param startTime Time which this trip is started
   * @param station transit.system.Station which this trip is started
   */
  public Trip(LocalDateTime startTime, Station station) {
    timeStarted = startTime;
    tripFee = station.getInitialFee();
    perStationFee = station.getPerStationFee();
    priorStops.add(station);
    tripLegLength = 0;
  }

  /** @return A string representation of a trip */
  public String toString() {
    return String.format(
        "%s: Trip started at %s (%s) and ended at %s (%s)",
        timeStarted.toLocalDate(),
        priorStops.get(0),
        timeStarted.toLocalTime(),
        endStation,
        timeEnded.toLocalTime());
  }

  /** @return The length of the current leg of this trip */
  int getTripLegLength() {
    return tripLegLength;
  }

  /**
   * End this transit.system.Trip
   *
   * @param station the station where this transit.system.Trip ends
   * @param endTime the station where this transit.system.Trip continuous
   */
  void endTrip(Station station, LocalDateTime endTime) {
    endStation = station;
    timeEnded = endTime;
    updateTripLegLength();
    tripFee += updateFinalFee();
  }

  /** @return The current fee of this trip */
  int getFee() {
    if (tripFee < maxFee) {
      return tripFee;
    } else {
      return maxFee;
    }
  }

  /**
   * Returns whether or not this transit.system.Trip is a valid transit.system.Trip. Note: an unfinished trip is always invalid. A
   * trip must be completed to be valid.
   *
   * @return true if this is a valid trip
   */
  boolean isValidTrip() {
    return (timeEnded != null && endStation != null && tripLegLength != -1);
  }
  /**
   * Checks if a trip continues another trip
   *
   * @param newStation new station
   * @param time time for the start of the continuation
   * @return true if continuous trip, false otherwise
   */
  boolean isContinuousTrip(Station newStation, LocalDateTime time) {
    boolean withinTimeLimit =
        Duration.between(timeStarted, time).toMinutes() <= (MAX_TRIP_LENGTH.toMinutes());
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
    perStationFee = station.getPerStationFee();
  }

  /**
   * Updates and returns the fee of this trip, assuming this trip has ended
   *
   * @return the per-station fare for this ride
   */
  private int updateFinalFee() {
    // If no route contains both stations
    if (tripLegLength == -1) {
      tripFee = this.maxFee;
      return this.maxFee;
    } else {
      return perStationFee * tripLegLength;
    }
  }

  /** A helper which updates the trip leg length */
  private void updateTripLegLength() {
    Integer firstStationIndex = null;
    Integer secondStationIndex = null;
    // Loop through all the routes to find the start and end station
    for (Route route : Route.getRoutesCopy()) {
      if (firstStationIndex == null && secondStationIndex == null) {
        for (int i = 0; i < route.getRouteStationsCopy().size(); i++) {
          Station station = route.getRouteStationsCopy().get(i);
          if (station.equals(priorStops.get(priorStops.size() - 1))) {
            firstStationIndex = i;
          }
          if (station.equals(endStation)) {
            secondStationIndex = i;
          }
        }
      }
      // If one of the two stations was not found in this route
      if (firstStationIndex == null || secondStationIndex == null) {
        firstStationIndex = null;
        secondStationIndex = null;
      }
    }

    // If no route contains both stations
    if (firstStationIndex == null && secondStationIndex == null) {
      tripLegLength = -1;
    } else {
      tripLegLength = Math.abs(secondStationIndex - firstStationIndex);
    }
  }
}
