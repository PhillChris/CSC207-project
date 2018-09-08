package transit.pages;

import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import transit.system.Route;
import transit.system.Station;
import transit.system.Statistics;

import java.util.HashMap;

public class StationAnalyticsPage extends AnalyticsPage {

  /**
   * The dropdown menu containing the type of station selected
   */
  private ComboBox<String> stationType = new ComboBox<>();
  /** The dropdown menu containing the station selected */
  private ComboBox<Station> stationName = new ComboBox<>();

  /**
   * Initializes a new instance of a StationAnalyticsPage.
   *
   * @param stage the stage which the StationAnalyticsPage is being served on
   */
  public StationAnalyticsPage(Stage stage) {
    super(stage);
    // add the two drop downs specific to this page
    makeScene();
    // set the statistics stored in this graph page
    setStationStatistics();

    // add the two drop downs general to all AnalyticsPages
    super.makeScene();
    setAndShow("Transit System Simulator");
  }

  @Override
  /** Makes the scene containing the content of this StationAnalyticsPage*/
  void makeScene() {
    // configure the given station type dropdown
    stationType.getItems().addAll(Station.POSSIBLE_TYPES);
    stationType.getSelectionModel().select(0);
    stationType.setOnAction(actionEvent -> refreshStationOptions());

    // configure the stations dropdown
    refreshStationOptions();

    // add these dropdowns to the analytics page dropdowns
    getDropDown().getChildren().addAll(stationType, stationName);
  }

  /** Refreshes the list of stations */
  private void refreshStationOptions() {
    // clear stationName's action before changing Station options
    stationName.setOnAction(actionEvent -> {
    });

    // add all stations of the selected type
    stationName.getItems().clear();
    stationName.getItems().addAll(Route.getAllStationsCopy().get(stationType.getValue()).values());
    stationName.getSelectionModel().select(0);

    // reset action of stationName
    stationName.setOnAction(actionEvent -> setStatistics(stationName.getValue().getStatistics()));

    // do this action to load the graph initially, if there are stations to be accessed
    if (!stationName.getItems().isEmpty()) {
      setStatistics(stationName.getValue().getStatistics());
    }
  }

  /** Sets the appropriate statistics map for the given station */
  private void setStationStatistics() {
    // Set the stats for this page to be the given station's or a dummy stat if there are no
    // stations
    if (!stationName.getItems().isEmpty()) {
      this.statistics = stationName.getValue().getStatistics();
    } else {
      this.statistics = new HashMap<>();
      for (String stat : Station.STATION_STATS) {
        statistics.put(stat, new Statistics(stat));
      }
    }
  }
}
