package transit.pages;

import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import transit.system.Station;
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
    setDropdowns();

    this.chart = makeSystemRevenueChart();
    this.grid.add(this.chart, 1, 1, 3, 1);
    this.scene = new Scene(this.grid, 800, 600);
  }

  /**
   *
   * @return A line chart representing this system's monthly revenue
   */
  public LineChart<String, Number> makeSystemRevenueChart() {
    return super.makeWeekChart(Statistics.getSystemRevenue().generateWeeklyValues());
  }

  /** Makes and places the dropdown menus */
  private void setDropdowns() {
    ComboBox<String> weeklyOrMonthly = new ComboBox<>();
    weeklyOrMonthly.getItems().setAll("Weekly", "Monthly");
    ComboBox<String> statsType = new ComboBox<>();
    statsType.getItems().setAll("Revenue", "Stations travelled");
    ComboBox<String> itemChoice = new ComboBox<>();
    itemChoice.getItems().setAll();
    this.grid.add(weeklyOrMonthly, 0, 0);
    this.grid.add(statsType, 1, 0);
    this.grid.add(itemChoice, 2, 0);
  }
}
