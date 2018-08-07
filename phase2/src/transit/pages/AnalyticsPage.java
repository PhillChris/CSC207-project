package transit.pages;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import transit.system.Statistics;

import java.util.HashMap;

/** A page designed to display analytical information */
public class AnalyticsPage {
  /** The statistics displayed by this analytics page */
  protected HashMap<String, Statistics> statistics;
  protected LineChart<String, Number> chart;

  protected Scene scene;

  protected GridPane layout = new GridPane();
  /**
   * A factory to construct graphs
   */
  protected GraphFactory graphFactory = new GraphFactory();
  /**
   * The drop down options displayed by this page
   */
  protected VBox dropDown = new VBox();
  /**
   * A combo box if the different statistics options for this page
   */
  ComboBox<Statistics> statOptions = new ComboBox<>();
  /**
   * The time options displayed by this page
   */
  ComboBox<String> timeOptions = new ComboBox<>();

  public AnalyticsPage() {
  }
  /**
   * Initialize a new instance of an AnalyticsPage.
   *
   * @param stage The stage for this page to be displayed
   */
  public AnalyticsPage(Stage stage, HashMap<String, Statistics> statistics) {
    this.statistics = statistics;
    scene = new Scene(layout, 800, 600);
    setLayout();
    scene
      .getStylesheets()
      .add(LoginPage.class.getResource("styling/GraphPage.css").toExternalForm());
    stage.setScene(scene);
    stage.show();
    stage.setTitle("Transit System Simulator");
  }

  public void setStatistics(HashMap<String, Statistics> statistics) {
    this.statistics = statistics;
    statOptions.getItems().clear();
    statOptions.getItems().addAll(statistics.values());
    setupStatOptions();
  }

  void setLayout() {
    setupStatOptions();
    dropDown.getChildren().addAll(timeOptions, statOptions);
    dropDown.setSpacing(10);
    layout.add(dropDown, 0, 0);
    layout.setHgap(10);
    layout.setPadding(new Insets(10, 10, 10, 10));
    // generate the Graph without having to click first
    setUpStatGraph();
  }

  /** Creates the drop down boxes to switch between statistics and different time frames. */
  public void setupStatOptions() {
    // setup the checkbox the statistics this page has access to
    statOptions.getItems().clear();
    statOptions.getItems().addAll(this.statistics.values());
    statOptions.getSelectionModel().select(0);

    timeOptions.getItems().clear();
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
    layout.add(chart, 0, 1);
    chart.setPrefWidth(Double.MAX_VALUE);
    GridPane.setVgrow(chart, Priority.ALWAYS);
    GridPane.setColumnSpan(chart, 3);
  }
}
