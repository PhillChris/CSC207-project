package transit.pages;

import transit.system.AnalyticStatistics;
import transit.system.User;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import transit.system.TransitTime;

import java.time.YearMonth;
import java.util.HashMap;

/** Represents a page used to show statistical data to admin users */
public class AdminGraphPage extends GraphPage {

  /**
   * Initialized a new instance of AdminUserPage
   *
   * @param primaryStage The stage for this page to be displayed
   * @param user The user associated with this page
   */
  public AdminGraphPage(Stage primaryStage, User user) {
    super(primaryStage, user);
  }

  /**
   *
   * @return A line chart representing this system's monthly revenue
   */
  @Override
  public LineChart<String, Number> makeChart() {
    LineChart lineChart = super.makeChart();

    lineChart.setTitle(
        String.format("Monthly Revenue (%s)", TransitTime.getCurrentDate().getYear()));
    XYChart.Series series = new XYChart.Series();
    HashMap<YearMonth, Double> monthlyRevenue = AnalyticStatistics.getSystemRevenue().generateMonthlyValues();
    for (YearMonth month : monthlyRevenue.keySet()) {
      if (month.getYear() == TransitTime.getCurrentDate().getYear()) {
        double total = monthlyRevenue.get(month) / 100.0;
        series.getData().add(new XYChart.Data(month.getMonth().toString(), total));
      }
    }

    lineChart.getData().add(series);
    return lineChart;
  }
}
