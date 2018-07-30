package transit.pages;

import javafx.scene.control.ChoiceBox;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import transit.system.AdminUser;
import transit.system.Station;
import transit.system.StatisticsMaker;
import transit.system.TransitTime;

/** Represents a page displaying statistics collected about stations */
public class StationStatsPage extends Page {
  /** The admin user accessing the current StationStatsPage */
  private AdminUser admin;
  /** An arraylist of the current labels in the system */
  private ArrayList<Label> stationLabels = new ArrayList<>();

  /**
   * Constructs a new StationStatsPage
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
    placeLabel("Station statistics!", 0, 0);
    ChoiceBox<LocalDate> dateOptions = placeDateOptions(0, 1);
    dateOptions.setOnAction(e -> {
      refreshStats(primaryStage, dateOptions.getValue());
    });
    refreshStats(primaryStage, dateOptions.getItems().get(0));
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  /**
   * Refreshes all of the station statistics displayed on this page
   *
   * @param primaryStage the stage on which this page is served
   * @param selectedDate the date selected on this page
   */
  private void refreshStats(Stage primaryStage, LocalDate selectedDate) {
    while (!stationLabels.isEmpty()) {
      grid.getChildren().remove(stationLabels.get(0));
      stationLabels.remove(stationLabels.get(0));
    }
    HashMap<Station, ArrayList<Integer>> busStationStats = StatisticsMaker.makeStationsMap("Bus", selectedDate);
    HashMap<Station, ArrayList<Integer>> subwayStationStats =
        StatisticsMaker.makeStationsMap("Subway", selectedDate);
    int i = 2;
    placeLabel("Bus Stations: ", 0, i);
    i++;
    for (Station station : busStationStats.keySet()) {
      stationLabels.add(placeLabel(
          "Station "
              + station
              + ": "
              + busStationStats.get(station).get(0)
              + " taps on and "
              + busStationStats.get(station).get(1)
              + " taps off.",
          0,
          i));
      i++;
    }
    placeLabel("Subway Stations:", 0, i);
    i++;
    for (Station station : subwayStationStats.keySet()) {
      stationLabels.add(placeLabel(
          "Station "
              + station
              + ": "
              + subwayStationStats.get(station).get(0)
              + " taps on and "
              + subwayStationStats.get(station).get(1)
              + " taps off.",
          0,
          i));
      i++;
    }
    placeButton(
        "Go back",
        () -> primaryStage.setScene(new AdminUserPage(primaryStage, admin).getScene()),
        0,
        i);
  }
}
