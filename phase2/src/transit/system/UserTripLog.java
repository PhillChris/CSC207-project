package transit.system;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * Records the previous Trips of a User
 */
public class UserTripLog extends Statistics<Trip>{

    /** Initializes a new instance of UserTapLog */
    UserTripLog(User user) {
        this.dailyLogs = new HashMap<>();
        this.monthlyLogs = new HashMap<>();

    }


    @Override
    void update(Trip data) {

    }
}
