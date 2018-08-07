package transit.pages;

import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import transit.system.Route;
import transit.system.Station;

public class StationGraphPage extends AnalyticsPage {
  ComboBox<String> stationType = new ComboBox<>();
  ComboBox<Station> stationName = new ComboBox<>();
  /**
   * Initialize a new instance of an AnalyticsPage.
   *
   * @param stage
   */
  public StationGraphPage(Stage stage) {
    super();
    setLayout();
    stage.setScene(scene);
    stage.show();
    stage.setTitle("Transit System Simulator");
  }

  @Override
  void setLayout() {
    stationType.getItems().addAll(Station.POSSIBLE_TYPES);
    stationType.getSelectionModel().select(0);
    stationType.setOnAction(actionEvent -> refreshStationOptions(stationType, stationName));

    refreshStationOptions(stationType, stationName);
    dropDown.getChildren().addAll(stationType, stationName);
    super.setLayout();
  }

  private void refreshStationOptions(ComboBox<String> stationType, ComboBox<Station> stationName) {
    stationName.getItems().clear();
    stationName.getItems().addAll(Route.getAllStationsCopy().get(stationType.getValue()).values());
    stationName.getSelectionModel().select(0);
    stationName.setOnAction(actionEvent -> setStatistics(stationName.getValue().getStatistics()));

    // do this action to load the graph initially
    setStatistics(stationName.getValue().getStatistics());
    setUpStatGraph();
  }
}
