package transit.pages;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import transit.system.TransitTime;
import transit.system.User;
import java.time.YearMonth;
import java.util.HashMap;

/** Represents a page displaying a User's expenditure trends*/
public class UserGraphPage extends GraphPage {

  /** Constructs a new UserGraphPage
   *
   * @param primaryStage the stage on which this page is being served
   * @param user the user whose information is being displayed
   */
  public UserGraphPage(Stage primaryStage, User user) {
    super(primaryStage, user);
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
    Scene scene  = new Scene(chart,800,600);
    this.scene = scene;
  }

  /** @return this user's monthly expenditure chart */
  public LineChart<String, Number> makeChart() {
    LineChart lineChart = super.makeChart();

    lineChart.setTitle(String.format("Monthly Expenditure for user (%s) in (%s)", user.getUserName(), TransitTime.getCurrentDate().getYear()));
    XYChart.Series series = new XYChart.Series();
    HashMap<YearMonth, Integer> expenditureMonthly = user.getExpenditureMonthly();
    for (YearMonth month : expenditureMonthly.keySet()) {
      if (month.getYear() == TransitTime.getCurrentDate().getYear()) {
        double total = expenditureMonthly.get(month) / 100.0;
        series.getData().add(new XYChart.Data(month.getMonth().toString(), total));
      }
    }

    lineChart.getData().add(series);
    return lineChart;
  }
}