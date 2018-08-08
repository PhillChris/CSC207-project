package transit.pages;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import transit.system.Statistics;

import java.util.HashMap;

/** A page designed to display analytical information */
public class AnalyticsPage extends Page {
  /** The statistics displayed by this analytics page */
  protected HashMap<String, Statistics> statistics;

  /**
   * The chart to display to the screen
   */
  private LineChart<String, Number> chart;

  /** A factory to construct graphs */
  private GraphFactory graphFactory = new GraphFactory();
  /** The drop down options displayed by this page */
  private HBox dropDown = new HBox();
  /** A combo box if the different statistics options for this page */
  private ComboBox<Statistics> statOptions = new ComboBox<>();
  /** The time options displayed by this page */
  private ComboBox<String> timeOptions = new ComboBox<>();
  /**
   * Initialize a new instance of AnalyticsPage without setting the Stage. This allows for
   * subclasses to extend the scene of analytics page, then set the Stage themselves.
   */
  public AnalyticsPage(Stage stage) {
    super(stage);
    scene = new Scene(grid, 1000, 800);
    scene
        .getStylesheets()
        .add(LoginPage.class.getResource("styling/GeneralStyle.css").toExternalForm());
  }

  /**
   * The default constructor for Analytics Page
   *
   * @param stage The stage used by this page
   * @param statistics The statistics used by this page
   */
  public AnalyticsPage(Stage stage, HashMap<String, Statistics> statistics) {
    super(stage);
    this.statistics = statistics;
    scene = new Scene(grid, 1000, 800);
    makeScene();
    scene
        .getStylesheets()
        .add(LoginPage.class.getResource("styling/GeneralStyle.css").toExternalForm());
    setAndShow("Transit System Simulator");
  }

  /** @return The drop downs of this page */
  public HBox getDropDown() {
    return dropDown;
  }

  /** @param statistics Sets the statistics of the page */
  public void setStatistics(HashMap<String, Statistics> statistics) {
    this.statistics = statistics;
    setupStatOptions();
  }

  /** Construct the scene of this page, making all elements to display to the page. */
  @Override
  void makeScene() {
    // make the stat options drop down
    setupStatOptions();
    // make the time options drop down
    timeOptions.getItems().clear();
    timeOptions.getItems().addAll("Monthly", "Daily");
    timeOptions.getSelectionModel().select(0);
    timeOptions.setOnAction(actionEvent -> setUpStatGraph());

    // place the drop
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
    // set statOptions action to nothing while we change the stats
    statOptions.setOnAction(actionEvent -> {});

    // setup the checkbox the statistics this page has access to
    statOptions.getItems().clear();
    statOptions.getItems().addAll(this.statistics.values());
    statOptions.getSelectionModel().select(0);

    // reset statOptions action
    statOptions.setOnAction(actionEvent -> setUpStatGraph());
  }

  /** Sets up the graph for the user to view */
  protected void setUpStatGraph() {
    grid.getChildren().remove(chart);
    if (timeOptions.getValue().equals("Monthly")) {
      chart = graphFactory.makeYearChart(statOptions.getValue().generateMonthlyValues());
      chart.getYAxis().setLabel(statOptions.toString());
    } else {
      chart = graphFactory.makeWeekChart(statOptions.getValue().generateWeeklyValues());
    }
    chart.getYAxis().setLabel(statOptions.getValue().toString());
    grid.add(chart, 0, 1);
    chart.setPrefWidth(Double.MAX_VALUE);
    GridPane.setVgrow(chart, Priority.ALWAYS);
    GridPane.setColumnSpan(chart, 3);
  }
}
