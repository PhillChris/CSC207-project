package transit.pages;

import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.AdminUser;
import transit.system.Station;
import transit.system.TransitTime;

public class StationStatsPage extends Page {
  private AdminUser admin;

  public StationStatsPage(Stage primaryStage, AdminUser admin) {
    this.admin = admin;
    makeScene(primaryStage);
  }

  public void makeScene(Stage primaryStage) {
    int i = 1;
    for (String type : Station.POSSIBLE_TYPES) {
      placeLabel("Station statistics!", 0, 0);
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
    placeButton(
        "Go back",
        () -> primaryStage.setScene(new AdminUserPage(primaryStage, admin).getScene()),
        0,
        i);
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }
}
