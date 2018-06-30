import java.util.HashMap;
import java.util.List;

/** This wraps together all the contents of TransitSystem */
public class TransitSystem {
  private static HashMap<String, List<Station>> routes;
  private static List<Trip> allTrip;
  private static List<CardHolder> allUsers;
  private static int month;
  private static int day;

  public static double getTotalRevenues() {
    return 0;
  }

  public static HashMap<String, List<Station>> getRoutes() {
    return routes;
  }
}
