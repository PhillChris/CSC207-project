package transit.pages;

import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import transit.system.Statistics;

import java.util.HashMap;

public class AnalyticsPage extends GraphPage {

  HBox dropDowns = new HBox();
  private BorderPane layout = new BorderPane();
  private HashMap<String, Statistics> statistics;
  ComboBox<Statistics> statOptions = new ComboBox<>();
  ComboBox<String> timeOptions = new ComboBox<>();

  /**
   * Initialized a new instance of AdminUserPage
   *
   * @param primaryStage The stage for this page to be displayed
   */
  public AnalyticsPage(Stage primaryStage, HashMap<String, Statistics> statistics) {
    this.statistics = statistics;
    makeScene(primaryStage);
  }

  @Override
  void makeScene(Stage stage) {
    stage.setTitle("Transit System Simulator");
    setupStatOptions();
    dropDowns.getChildren().addAll(timeOptions, statOptions);
    layout.setTop(dropDowns);

    // generate the Graph without having to click first
    setUpStatGraph();
    this.scene = new Scene(layout, 800, 600);
  }

  public void setupStatOptions() {
    // setup the checkbox the statistics this page has access to
    statOptions.getItems().addAll(this.statistics.values());
    statOptions.getSelectionModel().select(0);

    // setup the checkbox for different time intervals
    timeOptions.getItems().addAll("Monthly", "Daily");
    timeOptions.getSelectionModel().select(0);

    statOptions.setOnAction(actionEvent -> setUpStatGraph());
    timeOptions.setOnAction(actionEvent -> setUpStatGraph());

    // generate the Graph without having to click first
    setUpStatGraph();
  }

  private void setUpStatGraph() {
    if (timeOptions.getValue().equals("Monthly")) {
      chart = makeYearChart(statOptions.getValue().generateMonthlyValues());
    } else {
      chart = makeWeekChart(statOptions.getValue().generateWeeklyValues());
    }
    layout.setCenter(chart);
  }
}
