import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;

/** This wraps together all the contents of TransitSystem */
public class TransitSystem {
  private static HashMap<String, List<Station>> subwayRoutes;
  private static HashMap<String, List<Station>> busRoutes;
  private static List<Trip> allTrip;
  private static List<CardHolder> allUsers;
  LocalDate currentTime;

  public static double getTotalRevenues() {
    return 0;
  }

  public static HashMap<String, List<Station>> getBusRoutes() {
    return busRoutes;
  }

  public static HashMap<String, List<Station>> getSubwayRoutes() {
    return subwayRoutes;
  }

  public static List<CardHolder> getUsers() {return allUsers; }

  public static void setBusRoutes(HashMap<String, List<Station>> newroutes) {
    busRoutes = newroutes;
  }

  public static void setSubwayRoutes(HashMap<String, List<Station>> newroutes) {
    subwayRoutes = newroutes;
  }

  public static void addBusRoute(String name, List<String> routes){
    subwayRoutes.put(name, new ArrayList<Station>());

  }
  public static void addSubwayRoute(String name, List<String> routes){
    subwayRoutes.put(name, new ArrayList<Station>());

  }
}
