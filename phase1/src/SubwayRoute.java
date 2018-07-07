import java.util.ArrayList;
import java.util.HashMap;

public class SubwayRoute extends Route {


    private static ArrayList<SubwayRoute> subwayRoutes;
    private ArrayList<Station> stations;

    public SubwayRoute(String name, ArrayList<String> stationNames){
        super(name);
        ArrayList<Station> stations = new ArrayList<>();

        // Add stations to this Route
        for (String s: stationNames){
            SubwayStation station = new SubwayStation(s, name);
            stations.add(station);
        }
        // Add itself to the ArrayLists of stations
        subwayRoutes.add(this);
        Routes.add(this);
    }


    public static boolean checkRoute(ArrayList<String> stationNames) {
        return false;
    }

    public ArrayList<String> getStations(){
        ArrayList<String> stationNames = new ArrayList<>();
        for (Station s: stations){
            stationNames.add(s.name);
        }
        return stationNames;
    }
}
