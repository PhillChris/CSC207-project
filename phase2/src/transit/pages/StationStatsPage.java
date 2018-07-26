package transit.pages;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.Scene;
import transit.system.Station;
import transit.system.TransitTime;

public class StationStatsPage extends Page {
  public StationStatsPage(Stage primaryStage) {
    makeScene(primaryStage);
  }

  public void makeScene(Stage primaryStage) {
    for (String type : Station.POSSIBLE_TYPES) {
      placeLabel("Station statistics!", 0, 0);
      int i = 1;
      if (Station.getStationsCopy(type) != null) {
        for (Station station : Station.getStations(type)) {
          placeButton(
              station.getName(),
              () ->
                  makeAlert(
                          station.getName() + " info",
                          station.getName() + " info:",
                          station.getTapsOn(TransitTime.getCurrentDate())
                              + " taps on, "
                              + station.getTapsOff(TransitTime.getCurrentDate())
                              + " taps off.",
                          AlertType.INFORMATION)
                      .showAndWait(),
              0,
              i);
          i++;
        }
      }
    }
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }
}
