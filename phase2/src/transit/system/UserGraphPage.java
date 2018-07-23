package transit.system;

import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.time.YearMonth;
import java.util.HashMap;



public class UserGraphPage extends GraphPage{

  public UserGraphPage(Stage primaryStage, User user) {
    super(primaryStage, user);
  }

  public Scene makeScene(Stage stage) {
    stage.setTitle("Transit System Simulator");
    LineChart chart = makeChart();
    Scene scene  = new Scene(chart,800,600);
    return scene;
  }

  public LineChart<String, Number> makeChart() {
    LineChart lineChart = super.makeChart();

    lineChart.setTitle(String.format("Monthly Revenue (%s)", TransitTime.getCurrentDate().getYear()));
    XYChart.Series series = new XYChart.Series();
    HashMap<YearMonth, Integer> expenditureMonthly = user.getExpenditureMonthly();
    for (YearMonth month : expenditureMonthly.keySet()) {
      if (month.getYear() == TransitTime.getCurrentDate().getYear()) {
        double total = expenditureMonthly.get(month) / 100.0;
        series.getData().add(new XYChart.Data(month.getMonth(), total));
      }
    }

    lineChart.getData().add(series);
    return lineChart;
  }
}