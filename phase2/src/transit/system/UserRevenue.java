package transit.system;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.function.DoubleBinaryOperator;

public class UserRevenue implements Statistics<Double>, Serializable {

    @Override
    public HashMap<LocalDate, Double> generateWeeklyValues() {
        return null;
    }

    @Override
    public HashMap<YearMonth, Double> generateMonthlyValues() {
        return null;
    }

    @Override
    public void clearLogs() {

    }

    @Override
    public void update(Double data) {

    }
}
