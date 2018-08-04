package transit.pages;

import javafx.scene.Scene;
import transit.system.Statistics;
import transit.system.User;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import transit.system.TransitTime;

import java.time.YearMonth;
import java.util.HashMap;

/** Represents a page used to show statistical data to admin users */
public class AdminGraphPage extends GraphPage {

  private User user;

  /**
   * Initialized a new instance of AdminUserPage
   *
   * @param primaryStage The stage for this page to be displayed
   * @param user The user associated with this page
   */
  public AdminGraphPage(Stage primaryStage, User user) {
    super(primaryStage);
    this.user = user;
  }

  @Override
  void makeScene(Stage stage) {
    stage.setTitle("Transit System Simulator");
    this.chart = makeSystemRevenueChart();
    Scene scene = new Scene(chart, 800, 600);
    this.scene = scene;
  }

  /**
   *
   * @return A line chart representing this system's monthly revenue
   */
  public LineChart<String, Number> makeSystemRevenueChart() {
    LineChart lineChart = super.makeWeekChart(Statistics.getSystemRevenue().generateWeeklyValues());

    lineChart.setTitle(
        String.format("Monthly Revenue (%s)", TransitTime.getClock().getCurrentDate().getYear()));
    XYChart.Series series = new XYChart.Series();
    HashMap<YearMonth, Integer> monthlyRevenue = Statistics.getSystemRevenue().generateMonthlyValues();
    for (YearMonth month : monthlyRevenue.keySet()) {
      if (month.getYear() == TransitTime.getClock().getCurrentDate().getYear()) {
        double total = monthlyRevenue.get(month) / 100.0;
        series.getData().add(new XYChart.Data(month.getMonth().toString(), total));
      }
    }

    lineChart.getData().add(series);
    return lineChart;
  }
}
