package transit.pages;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import transit.system.TransitTime;
import transit.system.User;

import java.time.YearMonth;
import java.util.HashMap;

/** Represents a page displaying a User's expenditure trends */
public class UserGraphPage extends GraphPage {
  /** The user associated with this page */
  private User user;

  /**
   * Constructs a new UserGraphPage
   *
   * @param primaryStage the stage on which this page is being served
   * @param user the user whose information is being displayed
   */
  public UserGraphPage(Stage primaryStage, User user) {
    super(primaryStage);
    this.user = user;
  }

  /**
   * Makes the scene to be displayed on the given stage
   *
   * @param stage The stage for this page to be displayed
   */
  @Override
  public void makeScene(Stage stage) {
    stage.setTitle("Transit System Simulator");
    LineChart chart = makeChart();
    Scene scene = new Scene(chart, 800, 600);
    this.scene = scene;
  }

  /** @return this user's monthly expenditure chart */
  public LineChart<String, Number> makeChart() {
    LineChart lineChart = super.makeYearChart(user.getTripStatistics().get("Expenditure").generateMonthlyValues());

    lineChart.setTitle(
        String.format("Monthly Expenditure", TransitTime.getClock().getCurrentDate().getYear()));
    XYChart.Series series = new XYChart.Series();
    HashMap<YearMonth, Integer> expenditureMonthly = new HashMap<>();
    for (YearMonth month : expenditureMonthly.keySet()) {
      if (month.getYear() == TransitTime.getClock().getCurrentDate().getYear()) {
        double total = expenditureMonthly.get(month) / 100.0;
        series.getData().add(new XYChart.Data(month.getMonth().toString(), total));
      }
    }

    lineChart.getData().add(series);
    return lineChart;
  }
}
