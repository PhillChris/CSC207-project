/** Interface for station factories */
public interface StationFactory {
  /**
   * Returns a new station of some type
   *
   * @return a newly constructed station
   */
  Station newStation(String name);
}
