package transit.system;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
}
