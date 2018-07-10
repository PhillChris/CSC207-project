import java.util.ArrayList;
import java.util.List;

/** Represents an object of Route */
public class Route<T extends Station> {

  /** A list of all of the Routes */
  protected static ArrayList<Route> Routes = new ArrayList<Route>();

  private static int totalRoutes = 0;
  /** The name of this route */
  protected int routeNumber;

  private List<Station> routeStations = new ArrayList<>();

  public Route(List<String> stationNames, StationFactory fact) {
    routeNumber = totalRoutes;
    totalRoutes++;

    for (String s : stationNames) {
      Station station;
      station = fact.newStation(s);
      if (!station.getStations().containsKey(s)) { // there are no stations with the same name.
        Station.addStation(station); // associate stations
      } else {
        station = station.getStations().get(s);
      }
      Routes.add(this);
      getRouteStations().add(station);
    }
  }

  /** @return An arrayList of all RouteNames */
  public static ArrayList<Route> getRoutes() {
    return Routes;
  }

  public List<Station> getRouteStations() {
    return routeStations;
  }

  public Station findStation(int stationID) {
    for (Station station : getRouteStations()) {
      if (station.stationID == stationID) {
        return station;
      }
    }
    return null;
  }

  public int getRouteNumber() {
    return routeNumber;
  }
}
