package transit.system;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
  public static final String routeLocation = "./tmp/routes.ser";
  public static final String USERS_LOCATION = "./tmp/user.ser";

  public static void writeToDatabase() {
    HashMap<String, ArrayList<Route>> routes = Route.getRoutesCopy();
    writeObject(routeLocation, routes);
    HashMap<String, User> users = User.getAllUsersCopy();
    writeObject(USERS_LOCATION, users);
  }

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
   * Read all objects stored in the .ser files used to store all data related to the transit system.
   */
  public static void readFromDatabase() {
    readRoutes();
    readUsers();
  }

  /**
   * Read the HashMap of route objects from the .ser file storing them (the file path given by
   * routeLocation).
   */
  private static void readRoutes() {
    try {
      FileInputStream fileIn = new FileInputStream(routeLocation);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      Route.setRoutes((HashMap<String, ArrayList<Route>>) in.readObject());
    } catch (IOException e) {
      System.out.println("File not found when deserializing.");
    } catch (ClassNotFoundException h) {
      System.out.println("Wrong class contained in serialization file.");
    }
  }

  /**
   * Read the HashMap of User objects from the .ser file storing them (the file path given by
   * USERS_LOCATION).
   */
  private static void readUsers() {
    try {
      FileInputStream fileIn = new FileInputStream(USERS_LOCATION);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      User.setAllUsers((HashMap<String, User>) in.readObject());
    } catch (IOException e) {
      System.out.println("File not found when deserializing.");
    } catch (ClassNotFoundException h) {
      System.out.println("Wrong class contained in serialization file.");
    }
  }
}
