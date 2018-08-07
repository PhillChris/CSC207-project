package transit.pages;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import transit.system.TransitTime;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;

/** Represents a page used to show statistical information */
public class GraphFactory {

  /**
   * @param data The data to plot
   * @return A line chart of days to number values
   */
  public LineChart<String, Number> makeWeekChart(HashMap<LocalDate, Integer> data) {
    // Set the chart
    LineChart<String, Number> chart = makeChart("Days", "Daily Total");
    XYChart.Series series = new XYChart.Series();
    series.setName("Daily Totals");
    LocalDate date = TransitTime.getClock().getCurrentDate();
    for (int i = 0; i < data.keySet().size(); i++) {
      series.getData().add(0, new XYChart.Data(date.toString(), data.get(date)));
      date = date.minusDays(1);
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
    LineChart<String, Number> chart = makeChart("Months", "Months Total");
    XYChart.Series series = new XYChart.Series();
    series.setName("Monthly Totals");
    YearMonth month = TransitTime.getClock().getCurrentMonth();
    for (int i = 0; i < data.keySet().size(); i++) {
      series.getData().add(0, new XYChart.Data(month.toString(), data.get(month)));
      month = month.minusMonths(1);
    }
    chart.getData().add(series);
    return chart;
  }

  /** @return An empty chart */
  private LineChart<String, Number> makeChart(String xAxisName, String title) {
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel(xAxisName);

    final LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);

    lineChart.setTitle(String.format(title, TransitTime.getClock().getCurrentDate().getYear()));

    return lineChart;
  }
}
