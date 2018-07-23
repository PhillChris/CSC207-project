package transit.system;

import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;



public class UserGraphPage extends Page{
  User user;

  public UserGraphPage(Stage primaryStage, User user) {
    super(primaryStage);
    this.scene = makeScene(primaryStage);
    this.user = user;
  }

  public Scene makeScene(Stage stage) {
    stage.setTitle("Transit System Simulator");
    LineChart chart = makeChart(user);
    Scene scene  = new Scene(chart,800,600);

    return scene;
  }

  public LineChart<String, Number> makeChart(User user) {
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Month");

    final LineChart<String,Number> lineChart =
      new LineChart<String,Number>(xAxis,yAxis);

    lineChart.setTitle("Monthly Transit Expenditure (Current Year)");

    XYChart.Series series = new XYChart.Series();
    series.setName("Monthly Total");

//    HashMap<YearMonth, Integer> expenditureMonthly = user.getExpenditureMonthly();
//    for (YearMonth month : expenditureMonthly.keySet()) {
//      if (month.getYear() == TransitTime.getCurrentDate().getYear()) {
//        double total = expenditureMonthly.get(month) / 100.0;
//        series.getData().add(new XYChart.Data(month.getMonth(), total));
//      }
//    }

    series.getData().add(new XYChart.Data("Jan", 23));
    series.getData().add(new XYChart.Data("Feb", 14));
    series.getData().add(new XYChart.Data("Mar", 15));
    series.getData().add(new XYChart.Data("Apr", 24));
    series.getData().add(new XYChart.Data("May", 34));
    series.getData().add(new XYChart.Data("Jun", 36));
    series.getData().add(new XYChart.Data("Jul", 22));
    series.getData().add(new XYChart.Data("Aug", 45));
    series.getData().add(new XYChart.Data("Sep", 43));
    series.getData().add(new XYChart.Data("Oct", 17));
    series.getData().add(new XYChart.Data("Nov", 29));
    series.getData().add(new XYChart.Data("Dec", 25));

    lineChart.getData().add(series);
    return lineChart;
  }

}