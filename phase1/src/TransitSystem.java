import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;

/** This wraps together all the contents of TransitSystem */
public class TransitSystem {
  private static HashMap<String, ArrayList<Station>> subwayRoutes;
  private static HashMap<String, ArrayList<Station>> busRoutes;
  private static List<Trip> allTrip;
  private static List<CardHolder> allUsers;
  private static int month;
  private static int day;
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

  public static List<CardHolder> getUsers() {return allUsers; }

  public static void setBusRoutes(HashMap<String, ArrayList<Station>> newroutes) {
    busRoutes = newroutes;
  }

  public static void setSubwayRoutes(HashMap<String, ArrayList<Station>> newroutes) {
    subwayRoutes = newroutes;
  }

  public static void addBusRoute(String name, ArrayList<String> routes){
    subwayRoutes.put(name, new ArrayList<Station>());

  }
  public static void addSubwayRoute(String name, ArrayList<String> routes){
    subwayRoutes.put(name, new ArrayList<Station>());

  }

  public static void tap(ArrayList<String> cardInfo){

  }

  public static void addUser(ArrayList<String> userInfo){

  }

  public static void addCard(ArrayList<String> cardInfo){

  }

  public static void removeCard(ArrayList<String> cardInfo){

  }

  public static void reportTheft(ArrayList<String> userInfo){

  }

  public static void addFunds(ArrayList<String> userInfo){

  }

  public static void endDay(){

  }

  public static void monthlyExpenditue(){

  }
}
