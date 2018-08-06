package transit.pages;

import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import transit.system.Statistics;
import transit.system.TransitTime;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;

/** Represents a page used to show statistical information */
public abstract class GraphPage extends Page {

  HBox dropDowns = new HBox();
  ComboBox<Statistics> statOptions = new ComboBox<>();
  ComboBox<String> timeOptions = new ComboBox<>();
  protected BorderPane layout = new BorderPane();

  protected HashMap<String, Statistics> statistics;

  public GraphPage(HashMap<String, Statistics> statistics){
    super(new Stage(), true);
    this.statistics = statistics;
    stage = new Stage();
    makeScene();
    stage.setScene(scene);
    stage.show();

  }

  abstract void makeScene();

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
    LineChart<String, Number> chart = makeChart("Months", "Month's Total");
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
  private LineChart<String, Number> makeChart(String xAxixName, String title) {
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel(xAxixName);

    final LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);

    lineChart.setTitle(String.format(title, TransitTime.getClock().getCurrentDate().getYear()));
    ;

    return lineChart;
  }
}
