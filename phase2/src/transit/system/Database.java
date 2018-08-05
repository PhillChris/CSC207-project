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
  public static final String SYSTEMSTATS_LOCATION =
          "." + File.separator + "tmp" + File.separator + "SystemStats.ser";

  public static void writeToDatabase() {
    // Save the current time
    LocalDateTime time = TransitTime.getClock().getCurrentTime();
    writeObject(TIME_LOCATION, time);
    // Save the system's Users
    HashMap<String, User> users = User.getAllUsersCopy();
    writeObject(USERS_LOCATION, users);
    // Save the system's Routes
    HashMap<String, ArrayList<Route>>  routes = Route.getRoutesCopy();
    writeObject(ROUTE_LOCATION, routes);
    // Save the System's statistics
    HashMap<String, Statistics> stats = Statistics.getSystemStatistics();
    writeObject(SYSTEMSTATS_LOCATION, stats);
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
