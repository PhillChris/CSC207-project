/** Factory for producing Subway Stations */
public class SubwayFactory implements StationFactory {
  @Override
  public Station newStation(String name) {
    return new SubwayStation(name);
  }
}
