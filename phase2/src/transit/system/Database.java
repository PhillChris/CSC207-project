package transit.system;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
  public static final String routeLocation = "./tmp/routes.ser";

  public static void writeToDatabase() {
    writeRoutes();
  }

  private static void writeRoutes() {
    HashMap<String, ArrayList<Route>> routes = Route.getRoutesCopy();
    try {
      FileOutputStream fileOut = new FileOutputStream(routeLocation);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(routes);
    } catch (IOException e) {
      System.out.println("Couldn't find file");
    }
  }
}
