package transit.pages;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import transit.system.Statistics;
import java.util.HashMap;

/** A page designed to display analytical information */
public class AnalyticsPage extends GraphPage {
  /** The statistics displayed by this analytics page */
  protected HashMap<String, Statistics> statistics;;

  /**
   * Initialize a new instance of an AnalyticsPage.
   *
   * @param primaryStage The stage for this page to be displayed
   */
  public AnalyticsPage(Stage stage, HashMap<String, Statistics> statistics) {
    this.statistics = statistics;
    setLayout();
    Scene scene = new Scene(layout, 800, 600);
    stage.setScene(scene);
    stage.show();
    stage.setTitle("Transit System Simulator");
  }

  void setLayout() {
    setupStatOptions();
    dropDowns.getChildren().addAll(timeOptions, statOptions);
    layout.setTop(dropDowns);

    // generate the Graph without having to click first
    setUpStatGraph();
  }

  /** Creates the drop down boxes to switch between statistics and different time frames. */
  public void setupStatOptions() {
    // setup the checkbox the statistics this page has access to
    statOptions.getItems().addAll(this.statistics.values());
    statOptions.getSelectionModel().select(0);

    // setup the checkbox for different time intervals
    timeOptions.getItems().addAll("Monthly", "Daily");
    timeOptions.getSelectionModel().select(0);

    statOptions.setOnAction(actionEvent -> setUpStatGraph());
    timeOptions.setOnAction(actionEvent -> setUpStatGraph());
  }

  /** Sets up the graph for the user to view */
  protected void setUpStatGraph() {
    if (timeOptions.getValue().equals("Monthly")) {
      chart = graphFactory.makeYearChart(statOptions.getValue().generateMonthlyValues());
    } else {
      chart = graphFactory.makeWeekChart(statOptions.getValue().generateWeeklyValues());
    }
    layout.setCenter(chart);
  }
}
