package transit.pages;

import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import transit.system.TransitTime;
import transit.system.User;

/** Represents a page used to show statistical information */
public class GraphPage extends Page {
  /** The user associated with this page */
  protected User user;

  /**
   * Initializes a new instance of GraphPage
   *
   * @param primaryStage The stage for this page to be displayed
   * @param user The user associated with this page
   */
  public GraphPage(Stage primaryStage, User user) {
    this.user = user;
    makeScene(primaryStage);
  }

  /**
   * Sets the scene for this page
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

  /** @return A line chart containing monthly analytics */
  public LineChart<String, Number> makeChart() {
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Month");

    final LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);

    lineChart.setTitle(
        String.format("Monthly Totals (%s)", TransitTime.getCurrentDate().getYear()));

    XYChart.Series series = new XYChart.Series();
    series.setName("Monthly Total");

    lineChart.getData().add(series);
    return lineChart;
  }
}
