import java.util.Date;

/** Represents an object of Trip */
public class Trip {
    Date timeStarted;
    Date timeEnded;
    Station startStation;
    Station endStation;
    float tripFee;


    public Trip(Date startTime, Station startStation){

    }

    void endTrip(Station endStation){

    }

    void addFee(float value){

    }

    boolean isValidRoute (Station endStation) {
        return true;
    }

    Station getStartStation(){
        Station station = new BusStation("hello", "world");
        return station;
    }
}
