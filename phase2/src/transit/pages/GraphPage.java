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

  /** The chart displayed by this page */
  protected LineChart<String, Number> chart;

  /**
   * @param data The data to plot
   * @return A line chart of days to number values
   */
  public LineChart<String, Number> makeWeekChart(HashMap<LocalDate, Integer> data) {
    // Set the chart
    LineChart<String, Number> chart = makeChart("Days", "Week's Total");
    XYChart.Series series = new XYChart.Series();
    series.setName("Daily Totals");
    for (LocalDate date : data.keySet()) {
      series.getData().add(new XYChart.Data(date.toString(), data.get(date)));
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
    LineChart<String, Number> chart = makeChart("Days", "Month's Total");
    XYChart.Series series = new XYChart.Series();
    series.setName("Monthly Totals");
    for (YearMonth month : data.keySet()) {
      series.getData().add(new XYChart.Data(month.getMonth(), data.get(month)));
    }
    chart.getData().add(series);
    return chart;
  }

  /** @return An empty chart */
  private LineChart<String, Number> makeChart(String xAxixName, String title) {
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel(xAxixName);

    final LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);

    lineChart.setTitle(String.format(title, TransitTime.getClock().getCurrentDate().getYear()));;

    return lineChart;
  }
}
