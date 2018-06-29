import java.util.Date;

/** Represents an object of Trip */
public class Trip {
    static final double MAXFEE = 6.0;
    Date timeStarted;
    Date timeEnded;
    Station startStation;
    Station endStation;
    double tripFee;

    /**
     * Construct a new instance of Trip
     * @param startTime Time which the trip is started
     * @param station Station which the trip is started
     */
    public Trip(Date startTime, Station station){
        timeStarted = startTime;
        startStation = station;
        tripFee = station.getInitialFee();
    }


    void endTrip(Station endStation){

    }

    void addFee(double value){

    }

    double getFee(double){
        if (tripFee < MAXFEE){
            return tripFee;
        }
        else{
            return MAXFEE;
        }
    }

    boolean isValidRoute (Station endStation) {
        return true;
    }

    Station getStartStation(){
        Station station = new BusStation("hello", "world");
        return station;
    }
}
