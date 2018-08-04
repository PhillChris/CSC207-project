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
    this.user = user;
    makeScene(primaryStage);
  }

  /**
   * Makes the scene to be displayed on the given stage
   *
   * @param stage The stage for this page to be displayed
   */
  @Override
  public void makeScene(Stage stage) {
    stage.setTitle("Transit System Simulator");
    this.chart = makeMonthlyChart();
    Scene scene = new Scene(chart, 800, 600);
    this.scene = scene;
    stage.setScene(this.scene);
  }

  /** @return this user's monthly expenditure chart */
  public LineChart<String, Number> makeMonthlyChart() {
    return super.makeYearChart(user.getTripStatistics().get("Expenditure").generateMonthlyValues());
  }
}
