package transit.pages;

import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import transit.system.Route;
import transit.system.Station;
import transit.system.Statistics;

import java.util.HashMap;

public class StationGraphPage extends AnalyticsPage {
  ComboBox<String> stationType = new ComboBox<>();
  ComboBox<Station> stationName = new ComboBox<>();
  /**
   * Initialize a new instance of an AnalyticsPage.
   *
   * @param stage
   */
  public StationGraphPage(Stage stage) {
    super(stage);
    makeScene();
    setStationStatistics();
    super.makeScene();
    stage.setScene(scene);
    stage.show();
    stage.setTitle("Transit System Simulator");
  }

  @Override
  void makeScene() {
    stationType.getItems().addAll(Station.POSSIBLE_TYPES);
    stationType.getSelectionModel().select(0);
    stationType.setOnAction(actionEvent -> refreshStationOptions());

    refreshStationOptions();
    dropDown.getChildren().addAll(stationType, stationName);
  }

  private void refreshStationOptions() {
    stationName.getItems().clear();
    stationName.getItems().addAll(Route.getAllStationsCopy().get(stationType.getValue()).values());
    stationName.getSelectionModel().select(0);
    stationName.setOnAction(actionEvent -> setStatistics(stationName.getValue().getStatistics()));

    // do this action to load the graph initially, if there are stations to be accessed
    if (!stationName.getItems().isEmpty()) {
      setStatistics(stationName.getValue().getStatistics());
    }
  }

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
