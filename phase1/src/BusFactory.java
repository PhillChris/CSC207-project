public class BusFactory implements StationFactory {
  @Override
  public Station newStation(String name) {
    return new BusStation(name);
  }
}
