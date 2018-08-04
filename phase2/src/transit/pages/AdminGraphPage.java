package transit.pages;

import javafx.scene.Scene;
import transit.system.Statistics;
import transit.system.User;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import transit.system.TransitTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    this.user = user;
    makeScene(primaryStage);
  }

  @Override
  void makeScene(Stage stage) {
    stage.setTitle("Transit System Simulator");
    this.chart = makeSystemRevenueChart();
    this.scene = new Scene(chart, 800, 600);
    stage.setScene(this.scene);
  }

  /**
   *
   * @return A line chart representing this system's monthly revenue
   */
  public LineChart<String, Number> makeSystemRevenueChart() {
    return super.makeWeekChart(Statistics.getSystemRevenue().generateWeeklyValues());
  }
}
