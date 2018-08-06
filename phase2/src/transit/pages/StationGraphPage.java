package transit.pages;

import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import transit.system.Route;
import transit.system.Station;
import transit.system.Statistics;

import java.util.HashMap;

public class StationGraphPage extends AnalyticsPage {

  private ComboBox<String> stationType = new ComboBox<>();
  private ComboBox<Station> stationName = new ComboBox<>();

  /**
   * Initialize a new instance of an AnalyticsPage.
   *
   * @param stage
   * @param statistics
   */
  public StationGraphPage(Stage stage, HashMap<String, Statistics> statistics) {
    super(stage, statistics);
  }

  @Override
  void setLayout() {
    stationType.getItems().addAll(Station.POSSIBLE_TYPES);
    stationType.getSelectionModel().select(0);
    stationType.setOnAction(actionEvent -> refreshstationOptions());
    refreshstationOptions();
    dropDowns.getChildren().addAll(stationType, stationName);
    super.setLayout();


  }

  private void refreshstationOptions() {
    stationName.getItems().clear();
    stationName.getItems().addAll(Route.getAllStationsCopy().get(stationType.getValue()).values());
    stationName.getSelectionModel().select(0);
    setStatistics(stationName.getValue().getStatistics());
    setUpStatGraph();
  }
}
