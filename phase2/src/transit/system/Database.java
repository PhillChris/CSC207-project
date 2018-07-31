package transit.system;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
  public static final String routeLocation =
          "." + File.separator + "tmp" + File.separator + "routes.ser";
  public static final String USERS_LOCATION =
          "." + File.separator + "tmp" + File.separator + "user.ser";
  public static final String TIME_LOCATION =
          "." + File.separator + "tmp" + File.separator + "time.ser";

  public static void writeToDatabase() {
    HashMap<String, ArrayList<Route>> routes = Route.getRoutesCopy();
    writeObject(routeLocation, routes);
    HashMap<String, User> users = User.getAllUsersCopy();
    writeObject(USERS_LOCATION, users);
    LocalDateTime time = TransitTime.getCurrentTime();
    writeObject(TIME_LOCATION, time);
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
            (HashMap<String, ArrayList<Route>>) Database.readObject(routeLocation);
    if (routes != null) {
      Route.setRoutes(routes);
    }
    HashMap<String, User> users = (HashMap<String, User>) readObject(USERS_LOCATION);
    if (users != null) {
      User.setAllUsers(users);
    }
    LocalDateTime applicationTime = (LocalDateTime) readObject(TIME_LOCATION);
    if (applicationTime != null) {
      TransitTime.setCurrentTime(applicationTime);
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
  public static Object readObject(String fileLocation) {
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
