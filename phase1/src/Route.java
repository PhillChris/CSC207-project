import java.util.ArrayList;
import java.util.HashMap;

public abstract class Route {

  private static HashMap<String, ArrayList<Station>> Routes = new HashMap<>();
  protected String routeName;
  protected ArrayList<Station> Stations;

  public abstract boolean checkRoute();

  public String getRouteName() {
    return this.routeName;
  }

  public ArrayList<Station> getStations() {
    return Stations;
  }
}
