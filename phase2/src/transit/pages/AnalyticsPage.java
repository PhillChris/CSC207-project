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
    layout.setTop(dropDowns);
    this.scene = new Scene(layout, 800, 600);
  }

  public void setupStatOptions() {
    // setup the checkbox the statistics this page has access to
    ComboBox<Statistics> statOptions = new ComboBox<>();
    statOptions.getItems().addAll(this.statistics.values());
    statOptions.getSelectionModel().select(0);

    // setup the checkbox for different time intervals
    ComboBox<String> timeOptions = new ComboBox<>();
    timeOptions.getItems().addAll("Monthly", "Daily");
    timeOptions.getSelectionModel().select(0);

    statOptions.setOnAction(actionEvent -> setUpStatGraph(timeOptions, statOptions));
    timeOptions.setOnAction(actionEvent -> setUpStatGraph(timeOptions, statOptions));
    dropDowns.getChildren().addAll(timeOptions, statOptions);

    // generate the Graph without having to click first
    setUpStatGraph(timeOptions, statOptions);
  }

  private void setUpStatGraph(ComboBox<String> timeOptions, ComboBox<Statistics> statOptions) {
    if (timeOptions.getValue().equals("Monthly")) {
      chart = makeYearChart(statOptions.getValue().generateMonthlyValues());
    } else {
      chart = makeWeekChart(statOptions.getValue().generateWeeklyValues());
    }
    layout.setCenter(chart);
  }
}
