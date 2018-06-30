import java.util.ArrayList;
import java.util.HashMap;

/** This wraps together all the contents of TransitSystem */
public class TransitSystem {
  private static HashMap<String, ArrayList<Station>> routes;
  private static ArrayList<Trip> allTrip;
  private static ArrayList<CardHolder> allUsers;
  private static int month;
  private static int day;

  public static double getTotalRevenues() {
    return 0;
  }

  public static HashMap<String, ArrayList<Station>> getRoutes() {
    return routes;
  }
}
