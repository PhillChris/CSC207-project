package transit.pages;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.AdminUser;
import transit.system.Route;
import transit.system.Station;
import transit.system.TransitTime;

/** Represents a page displaying statistics collected about stations*/
public class StationStatsPage extends Page {

  /** The admin user accessing the current StationStatsPage*/
  private AdminUser admin;

  /**
   * Construcs a new StationStatsPage
   *
   * @param primaryStage the stage on which this page is being served
   * @param admin the AdminUser accessing the given statistics
   */
  public StationStatsPage(Stage primaryStage, AdminUser admin) {
    this.admin = admin;
    makeScene(primaryStage);
  }

  /**
   * Makes the scene to be served on the given primaryStage,=
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  public void makeScene(Stage primaryStage) {
    int i = 1;
    placeLabel("Station statistics!", 0, 0);
    for (String type : Station.POSSIBLE_TYPES) {
      for (Route route: Route.getRoutesCopy().get(type)) {
        for (Station station : route.getRouteStationsCopy()) {
          writeStationStat(station, i);
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

  /**
   * Write the statistics which occur to the given station
   *
   * @param station the station whose information we are now rendering
   * @param i the current index of the loop in StationStatsPage.makeScene
   */
  private void writeStationStat(Station station, int i) {
    placeLabel(
        station.toString()
            + ": "
            + station.getTapsOn(TransitTime.getCurrentDate())
            + " taps on, "
            + station.getTapsOff(TransitTime.getCurrentDate())
            + " taps off.",
        0,
        i,
        "station" + station.toString());
  }
}
