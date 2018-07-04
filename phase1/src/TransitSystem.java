import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** This wraps together all the contents of TransitSystem */
public class TransitSystem {
  private static HashMap<String, ArrayList<Station>> subwayRoutes;
  private static HashMap<String, ArrayList<Station>> busRoutes;
  private static List<Trip> allTrip;
  private static List<CardHolder> allUsers;
  private static int month;
  private static int day;

  public static double getTotalRevenues() {
    return 0;
  }

  public static HashMap<String, ArrayList<Station>> getBusRoutes() {
    return busRoutes;
  }

  public static HashMap<String, ArrayList<Station>> getSubwayRoutes() {
    return subwayRoutes;
  }

  public static void setBusRoutes(HashMap<String, ArrayList<Station>> newroutes) {
    busRoutes = newroutes;
  }

  public static void setSubwayRoutes(HashMap<String, ArrayList<Station>> newroutes) {
    subwayRoutes = newroutes;
  }
}
