package transit.pages;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import transit.system.Statistics;

import java.util.HashMap;

/** A page designed to display analytical information */
public class AnalyticsPage extends Page {
  /** The statistics displayed by this analytics page */
  protected HashMap<String, Statistics> statistics;

  protected LineChart<String, Number> chart;

  protected Scene scene;
  /**
   * A factory to construct graphs
   */
  protected GraphFactory graphFactory = new GraphFactory();
  /** The drop down options displayed by this page */
  protected VBox dropDown = new VBox();
  /** A combo box if the different statistics options for this page */
  ComboBox<Statistics> statOptions = new ComboBox<>();
  /** The time options displayed by this page */
  ComboBox<String> timeOptions = new ComboBox<>();

  /**
   * Initialize a new instance of AnalyticsPage without setting the Stage. This allows for
   * subclasses to extend the scene of analytics page, then set the Stage themselves.
   */
  public AnalyticsPage(Stage stage) {
    super(stage);
    scene = new Scene(grid, 800, 600);
    scene
            .getStylesheets()
            .add(LoginPage.class.getResource("styling/GeneralStyle.css").toExternalForm());
  }

  public AnalyticsPage(Stage stage, HashMap<String, Statistics> statistics) {
    super(stage);
    this.statistics = statistics;
    scene = new Scene(grid, 800, 600);
    makeScene();
    scene
      .getStylesheets()
      .add(LoginPage.class.getResource("styling/GeneralStyle.css").toExternalForm());
    this.stage.setScene(scene);
    this.stage.show();
    this.stage.setTitle("Transit System Simulator");
  }

  public void setStatistics(HashMap<String, Statistics> statistics) {
    this.statistics = statistics;
    statOptions.getItems().clear();
    statOptions.getItems().addAll(statistics.values());
    setupStatOptions();
  }

  void makeScene() {
    setupStatOptions();
    dropDown.getChildren().addAll(timeOptions, statOptions);
    dropDown.setSpacing(10);
    grid.add(dropDown, 0, 0);
    grid.setHgap(10);
    grid.setPadding(new Insets(10, 10, 10, 10));
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
    grid.add(chart, 0, 1);
    chart.setPrefWidth(Double.MAX_VALUE);
    GridPane.setVgrow(chart, Priority.ALWAYS);
    GridPane.setColumnSpan(chart, 3);
  }
}
