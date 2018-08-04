package transit.system;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** Records non analytic statistics associated with an object */
public class NonAnalyticStatistics<T> implements Statistics<T>, Serializable {

    private HashMap<LocalDate, List<T>> dailyLogs;

    /**
     * Initializes a new instance of NonAnalyticStatistics
     */
    public NonAnalyticStatistics(){
        dailyLogs = new HashMap<>();
    }

    @Override
    public void update(T data) {
        LocalDate date = TransitTime.getCurrentDate();
        if (!dailyLogs.containsKey(date)){
            dailyLogs.put(date, new ArrayList<>());
        }
        dailyLogs.get(date).add(data);
    }
}
