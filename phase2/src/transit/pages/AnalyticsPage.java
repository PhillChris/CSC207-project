package transit.pages;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import transit.system.Statistics;

import java.util.HashMap;

public class AnalyticsPage {

  /** The chart displayed by this page */
  protected LineChart<String, Number> chart;
  /** The layout of this page */
  protected BorderPane layout = new BorderPane();
  /** The statistics displayed by this analytics page */
  protected HashMap<String, Statistics> statistics;
  /** A factory to construct graphs */
  protected GraphFactory graphFactory = new GraphFactory();
  /** The drop down options displayed by this page */
  HBox dropDowns = new HBox();
  /** A combo box if the different statistics options for this page */
  ComboBox<Statistics> statOptions = new ComboBox<>();
  /** The time options displayed by this page */
  ComboBox<String> timeOptions = new ComboBox<>();

  /**
   * Initialize a new instance of an AnalyticsPage.
   *
   * @param primaryStage The stage for this page to be displayed
   */
  public AnalyticsPage(HashMap<String, Statistics> statistics) {
    Stage stage = new Stage();
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
