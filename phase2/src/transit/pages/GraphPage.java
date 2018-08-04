package transit.pages;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import transit.system.TransitTime;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;

/** Represents a page used to show statistical information */
public abstract class GraphPage extends Page {

  /**
   * Initializes a new instance of GraphPage
   *
   * @param primaryStage The stage for this page to be displayed
   * @param user The user associated with this page
   */
  public GraphPage(Stage primaryStage) {
    makeScene(primaryStage);
  }

  /** @return A line chart containing monthly analytics */
  private LineChart<String, Number> makeChart(String xAxixName, String title) {
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel(xAxixName);

    final LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);

    lineChart.setTitle(String.format(title, TransitTime.getClock().getCurrentDate().getYear()));

    XYChart.Series series = new XYChart.Series();
    series.setName(title);

    lineChart.getData().add(series);
    return lineChart;
  }

  /**
   * @param data The data to plot
   * @return A line chart of days to number values
   */
  public LineChart<String, Number> makeWeekChart(HashMap<LocalDate, Integer> data) {
    // Set the chart
    LineChart<String, Number> chart = makeChart("Days", "Week's Total");
    XYChart.Series series = new XYChart.Series();
    HashMap<LocalDate, Integer> expenditureMonthly = data;
    for (LocalDate date : data.keySet()) {
      series.getData().add(new XYChart.Data(date.getDayOfMonth(), data.get(date)));
    }
    chart.getData().add(series);
    return chart;
  }

  /**
   * @param data The data to plot
   * @return A line chart of days to number values
   */
  public LineChart<String, Number> makeYearChart(HashMap<YearMonth, Integer> data) {
    // Set the chart
    LineChart<String, Number> chart = makeChart("Days", "Week's Total");
    XYChart.Series series = new XYChart.Series();
    HashMap<YearMonth, Integer> expenditureMonthly = data;
    for (YearMonth month : data.keySet()) {
      series.getData().add(new XYChart.Data(month.getMonth(), data.get(month)));
    }
    chart.getData().add(series);
    return chart;
  }
}
