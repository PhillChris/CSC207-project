package transit.system;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.YearMonth;

public class Database {
  public static final String ROUTE_LOCATION =
          "." + File.separator + "tmp" + File.separator + "routes.ser";
  public static final String USERS_LOCATION =
          "." + File.separator + "tmp" + File.separator + "user.ser";
  public static final String TIME_LOCATION =
          "." + File.separator + "tmp" + File.separator + "time.ser";
  public static final String MONTHLY_REVENUE_LOCATION =
      "." + File.separator + "tmp" + File.separator + "monthlyRevenue.ser";
  public static final String DAILY_REVENUE_LOCATION =
      "." + File.separator + "tmp" + File.separator + "dailyRevenue.ser";
  public static final String DAILY_LOG_LOCATION =
      "." + File.separator + "tmp" + File.separator + "dailyLog.ser";
  public static final String DAILY_NUM_TRIPS_LOCATION =
      "." + File.separator + "tmp" + File.separator + "dailyNumTrips.ser";
  public static final String DATES_LOCATION =
      "." + File.separator + "tmp" + File.separator + "dates.ser";

  public static void writeToDatabase() {
    HashMap<String, ArrayList<Route>> routes = Route.getRoutesCopy();
    writeObject(ROUTE_LOCATION, routes);
    HashMap<String, User> users = User.getAllUsersCopy();
    writeObject(USERS_LOCATION, users);
    LocalDateTime time = TransitTime.getCurrentTime();
    writeObject(TIME_LOCATION, time);
    HashMap<YearMonth, Integer> monthlyRevenue = StatisticsMaker.getMonthlyRevenueCopy();
    writeObject(MONTHLY_REVENUE_LOCATION, monthlyRevenue);
    HashMap<LocalDate, Integer> dailyRevenue = StatisticsMaker.getDailyRevenueCopy();
    writeObject(DAILY_REVENUE_LOCATION, dailyRevenue);
    HashMap<LocalDate, Integer> dailyLog = StatisticsMaker.getDailyLogCopy();
    writeObject(DAILY_LOG_LOCATION, dailyLog);
    HashMap<LocalDate, Integer> dailyNumTrips = StatisticsMaker.getDailyNumTripsCopy();
    writeObject(DAILY_NUM_TRIPS_LOCATION, dailyNumTrips);
    ArrayList<LocalDate> dates = StatisticsMaker.getDatesCopy();
    writeObject(DATES_LOCATION, dates);
  }

  /**
   * Write the given object to the passed file location in serialized format.
   *
   * @param fileLocation the location at which the object will be written.
   * @param toWrite the object to write.
   */
  private static void writeObject(String fileLocation, Object toWrite) {
    try {
      FileOutputStream fileOut = new FileOutputStream(fileLocation);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(toWrite);
    } catch (IOException e) {
      System.out.println("Couldn't find file");
    }
  }

  /**
   * Read all objects stored in the .ser files used to store all data related to the transit system,
   * then store the objects read in their relevant class to be used in the application.
   */
  public static void readFromDatabase() {
    HashMap<String, ArrayList<Route>> routes =
            (HashMap<String, ArrayList<Route>>) Database.readObject(ROUTE_LOCATION);
    if (routes != null) {
      Route.setRoutes(routes);
    }
    HashMap<String, User> users = (HashMap<String, User>) readObject(USERS_LOCATION);
    if (users != null) {
      User.setAllUsers(users);
    }
    HashMap<YearMonth, Integer> monthlyRevenue = (HashMap<YearMonth, Integer>) Database.readObject(MONTHLY_REVENUE_LOCATION);
    if (monthlyRevenue != null) {
      StatisticsMaker.setMonthlyRevenue(monthlyRevenue);
    }
    HashMap<LocalDate, Integer> dailyRevenue = (HashMap<LocalDate, Integer>) Database.readObject(DAILY_REVENUE_LOCATION);
    if (dailyRevenue != null) {
      StatisticsMaker.setDailyRevenue(dailyRevenue);
    }
    HashMap<LocalDate, Integer> dailyLog = (HashMap<LocalDate, Integer>) Database.readObject(DAILY_LOG_LOCATION);
    if (dailyLog != null) {
      StatisticsMaker.setDailyLog(dailyLog);
    }
    HashMap<LocalDate, Integer> dailyNumTrips = (HashMap<LocalDate, Integer>) Database.readObject(DAILY_NUM_TRIPS_LOCATION);
    if (dailyNumTrips != null) {
      StatisticsMaker.setDailyNumTrips(dailyNumTrips);
    }
    ArrayList<LocalDate> dates = (ArrayList<LocalDate>) Database.readObject(DATES_LOCATION);
    if (dates != null) {
      StatisticsMaker.setDates(dates);
    }
  }

  /**
   * Reads an object from the specified location and returns it. Note that this method should be
   * called only when you know what object is stored at the file location or you will be unable to
   * cast the returned Object to something useful.
   *
   * @param fileLocation the file location to read the object from.
   * @return the object read from the file location.
   */
  static Object readObject(String fileLocation) {
    try {
      FileInputStream fileIn = new FileInputStream(fileLocation);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      return in.readObject();
    } catch (IOException e) {
      System.out.println("File not found when deserializing.");
    } catch (ClassNotFoundException h) {
      System.out.println("Wrong class contained in serialization file.");
    }
    return null;
  }
}
