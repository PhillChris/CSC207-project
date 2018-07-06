import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;

/** This wraps together all the contents of TransitSystem */
public class TransitSystem {
  private static HashMap<String, ArrayList<Station>> subwayRoutes = new HashMap<>();
  private static HashMap<String, ArrayList<Station>> busRoutes = new HashMap<>();
  private static List<Trip> allTrip;
  private static List<CardHolder> allUsers;
  LocalDate currentTime;

  public static double getTotalRevenues() {
    return 0;
  }

  public static HashMap<String, ArrayList<Station>> getBusRoutes() {
    return busRoutes;
  }

  public static HashMap<String, ArrayList<Station>> getSubwayRoutes() {
    return subwayRoutes;
  }

  public static List<CardHolder> getUsers() { return allUsers; }


  public static void addBusRoute(String name, List<Station> routes){
    busRoutes.put(name, new ArrayList<Station>(routes));

  }
  public static void addSubwayRoute(String name, List<Station> routes){
    subwayRoutes.put(name, new ArrayList<Station>());
  }
}
