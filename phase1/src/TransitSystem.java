import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** This wraps together all the contents of TransitSystem */
public class TransitSystem {
  private static HashMap<String, ArrayList<Station>> routes;
  private static List<Trip> allTrip;
  private static List<CardHolder> allUsers;
  private static int month;
  private static int day;

  public static double getTotalRevenues() {
    return 0;
  }

  public static HashMap<String, ArrayList<Station>> getRoutes() {
    return routes;
  }

  public static void setRoutes(HashMap<String, ArrayList<Station>> newroutes) {
    routes = newroutes;
  }
}
