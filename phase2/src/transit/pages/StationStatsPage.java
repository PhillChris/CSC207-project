package transit.pages;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.AdminUser;
import transit.system.Station;
import transit.system.StatisticsMaker;
import transit.system.TransitTime;

/** Represents a page displaying statistics collected about stations */
public class StationStatsPage extends Page {

  /** The admin user accessing the current StationStatsPage */
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
    HashMap<Station, ArrayList<Integer>> busStationStats = StatisticsMaker.makeStationsMap("Bus");
    HashMap<Station, ArrayList<Integer>> subwayStationStats =
        StatisticsMaker.makeStationsMap("Subway");
    placeLabel("Station statistics!", 0, 0);
    placeDateOptions(0, 1);
    placeLabel("Bus Stations: ", 0, 2);
    int i = 3;
    for (Station station : busStationStats.keySet()) {
      placeLabel(
          "Station "
              + station
              + ": "
              + busStationStats.get(station).get(0)
              + " taps on and "
              + busStationStats.get(station).get(1)
              + " taps off.",
          0,
          i);
      i++;
    }
    placeLabel("Subway Stations:", 0, i);
    i++;
    for (Station station : subwayStationStats.keySet()) {
      placeLabel(
          "Station "
              + station
              + ": "
              + subwayStationStats.get(station).get(0)
              + " taps on and "
              + subwayStationStats.get(station).get(1)
              + " taps off.",
          0,
          i);
      i++;
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
