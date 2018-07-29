package transit.system;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;

/** This class is designed to track all statistics related to a user */
public class UserInformation {
  /** The user assoicated with these statistics */
  User user;
    private HashMap<LocalDate, Integer> tapInLog;
    /** A log of taps mapping a given date to the number of taps out recorded */
    private HashMap<LocalDate, Integer> tapOutLog;
    /** An ArrayList of this transit.system.User's last three trips */
    private ArrayList<Trip> previousTrips;
    /** HashMap linking each month to the total expenditure for that month */
    private HashMap<YearMonth, Integer> expenditureMonthly;
    /** An ArrayList of this transit.system.User's cards */


    UserInformation(){
        tapInLog = new HashMap<>();
        tapOutLog = new HashMap<>();
        previousTrips = new ArrayList<>();
    }


}
