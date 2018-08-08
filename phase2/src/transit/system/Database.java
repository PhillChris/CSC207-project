package transit.system;

import javafx.application.Platform;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A utility class with methods to store objects relating to the persistence of the Transit System
 * and the methods to read those same objects upon running the application.
 */
public class Database {
  /**
   * The location of the serialization file storing all Routes
   */
  public static final String ROUTE_LOCATION =
          "." + File.separator + "tmp" + File.separator + "routes.ser";

  /** The location of the serialization file storing all Users */
  public static final String USERS_LOCATION =
          "." + File.separator + "tmp" + File.separator + "user.ser";
  /**
   * The location of the serialization file storing the TransitTime when the program was last closed
   */
  public static final String TIME_LOCATION =
          "." + File.separator + "tmp" + File.separator + "time.ser";
  /** The location of the serialization file storing system wide Statistics */
  public static final String SYSTEMSTATS_LOCATION =
          "." + File.separator + "tmp" + File.separator + "SystemStats.ser";

  /**
   * A method taking all static objects from classes in the Transit System which need to be
   * reinitialized upon running the application and serializing them to the appropriate file
   * location.
   */
  public static void writeToDatabase() {
    // Save the current time
    LocalDateTime time = TransitTime.getInstance().getCurrentTime();
    writeObject(TIME_LOCATION, time);
    // Save the system's Users
    HashMap<String, User> users = User.getAllUsersCopy();
    writeObject(USERS_LOCATION, users);
    // Save the system's Routes
    HashMap<String, ArrayList<Route>> routes = Route.getRoutesCopy();
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
      Platform.exit();
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
    } catch (IOException | ClassNotFoundException e) {
      LogWriter.getInstance().logFirstTimeStartup();
    }
    return null;
  }
}
