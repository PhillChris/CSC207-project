package transit.pages;

import javafx.scene.control.ComboBox;
import transit.system.Route;
import transit.system.Station;

public class StationGraphPage extends GraphPage {

  private ComboBox<String> stationType = new ComboBox<>();
  private ComboBox<Station> stationName = new ComboBox<>();

  protected void setLayout() {
    stationType = new ComboBox<>();
    stationType.getItems().addAll(Station.POSSIBLE_TYPES);
    stationType.getSelectionModel().select(0);
    stationType.setOnAction(e -> setStationComboBox(stationType.getValue().toString()));
    dropDowns.getChildren().addAll(stationType, stationName);
  }

  private void setStationComboBox(String type) {
    stationName.getItems().clear();
    stationName.getItems().addAll(Route.getAllStationsCopy().get(type).values());
    stationName.setOnAction(
        actionEvent -> {
          statOptions.getItems().clear();
          statOptions.getItems().addAll(stationName.getValue().getStatistics().values());
        });
  }

  /** Sets up the graph for the user to view */
  protected void setUpStatGraph() {
      Station station = Route.getAllStationsCopy().get(stationType.getValue());
    if (timeOptions.getValue().equals("Monthly")) {
      chart = graphFactory.makeYearChart(statOptions.getValue().generateMonthlyValues());
    } else {
      chart = graphFactory.makeWeekChart(statOptions.getValue().generateWeeklyValues());
    }
    layout.setCenter(chart);
  }
}
