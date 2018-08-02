package transit.system;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

/** Represents an object of transit.system.Trip */
public class Trip implements Serializable {
  /** The maximum duration between the start of two continuous trips */
  protected static final Duration MAX_TRIP_LENGTH = Duration.ofMinutes(120);
  /** The time started by this trip */
  protected LocalDateTime timeStarted;
  /** The time ended by this trip */
  protected LocalDateTime timeEnded;
  /** The stops made along the trip before reaching the final station */
  protected ArrayList<Station> priorStops = new ArrayList<>();
  /** The station ended by this trip */
  protected Station endStation;
  /** The current fee of this trip */
  protected int tripFee;
  /** The maximum fee charged by this trip */
  protected int maxFee = 600;
  /** The fee this trip charges itself per station travelled */
  protected int perStationFee;
  /** The length of the most recent leg of this trip */
  protected int tripLegLength;

  /**
   * Construct a new instance of transit.system.Trip
   *
   * @param station transit.system.Station which this trip is started
   */
  public Trip(Station station, String permission) {
    timeStarted = TransitTime.getCurrentTime();
    tripFee = station.getInitialFee(permission);
    perStationFee = station.getPerStationFee(permission);
    priorStops.add(station);
    tripLegLength = 0;
  }

  /** @return The length of the current leg of this trip */
  int getTripLegLength() {
    return tripLegLength;
  }

  /**
   * End this transit.system.Trip
   *
   * @param station the station where this transit.system.Trip ends
   */
  void endTrip(Station station) {
    endStation = station;
    timeEnded = TransitTime.getCurrentTime();
    updateTripLegLength();
    tripFee += updateFinalFee();
  }

  /** @return The current fee of this trip */
  public int getFee() {
    if (tripFee < maxFee) {
      return tripFee;
    } else {
      return maxFee;
    }
  }

  /** @return the station at which this Trip started*/
  public String getStartStation() {
    return priorStops.get(0).toString();
  }

  /** @return A string representation of a trip */
  public String toString() {
    String endTime;
    String finalStation;
    if (timeEnded != null && endStation != null) {
      endTime = timeEnded.toString();
      finalStation = endStation.toString();
    } else {
      endTime = "Not Ended";
      finalStation = "Null";
    }
    return String.format(
        "Trip started at %s (%s) and ended at %s (%s)",
        timeStarted.toString(), priorStops.get(0), endTime, finalStation);
  }

  /**
   * Returns whether or not this transit.system.Trip is a valid transit.system.Trip. Note: an
   * unfinished trip is always invalid. A trip must be completed to be valid.
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
   * @return true if continuous trip, false otherwise
   */
  boolean isContinuousTrip(Station newStation) {
    LocalDateTime time = TransitTime.getCurrentTime();
    boolean withinTimeLimit =
        Duration.between(timeStarted, time).toMinutes() <= (MAX_TRIP_LENGTH.toMinutes());
    return (this.endStation.equals(newStation) && withinTimeLimit);
  }

  /**
   * Set the endStation and timeEnded to none when the trip is being continued. Continue this trip
   * from station.
   *
   * @param station the station that the trip is being continued from.
   */
  void continueTrip(Station station, String permission) {
    priorStops.add(endStation);
    priorStops.add(station);
    endStation = null;
    timeEnded = null;
    maxFee = Math.max(0, maxFee - tripFee);
    tripFee = station.getInitialFee(permission);
    perStationFee = station.getPerStationFee(permission);
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

  /** A helper which updates the trip leg length. A trip length of -1 describes an invalid trip */
  private void updateTripLegLength() {
    // The indices of the start and stop station
    Integer firstStationIndex = null;
    Integer secondStationIndex = null;

    // Loop through all the routes
    for (String type : Station.POSSIBLE_TYPES) {
      if (Route.getRoutesCopy().get(type) != null) {
        for (Route route : Route.getRoutesCopy().get(type)) {
          // Do not check routes if the start and end station have already been found
          if (firstStationIndex == null && secondStationIndex == null) {
            // Loop through all the stations in a given route
            for (int i = 0; i < route.getRouteStationsCopy().size(); i++) {
              Station station = route.getRouteStationsCopy().get(i);
              // Check equality in start station for this leg
              if (station.equals(priorStops.get(priorStops.size() - 1))) {
                firstStationIndex = i;
              }
              // Check for equality in the end station for this leg
              if (station.equals(endStation)) {
                secondStationIndex = i;
              }
            }
          }
        }

        // If one of the two stations was not found in this route, set both indices to null
        if (firstStationIndex == null || secondStationIndex == null) {
          firstStationIndex = null;
          secondStationIndex = null;
        }
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
