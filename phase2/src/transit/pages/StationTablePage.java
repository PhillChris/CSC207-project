package transit.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.Route;
import transit.system.Station;
import transit.system.StationRow;
import transit.system.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

/** Represents a page displaying statistics collected about stations */
public class StationTablePage extends TablePage {
  /** The admin user accessing the current StationTablePage */
  private User admin;

  /**
   * Constructs a new StationTablePage
   *
   * @param primaryStage the stage on which this page is being served
   * @param admin the AdminUser accessing the given statistics
   */
  public StationTablePage(Stage primaryStage, User admin) {
    this.admin = admin;
    makeScene(primaryStage);
  }

  /**
   * Makes the scene to be served on the given primaryStage
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  public void makeScene(Stage primaryStage) {
    placeLabel("Station statistics!", 0, 0);
    ChoiceBox<LocalDate> dateOptions = placeDateOptions(0, 1);
    placeLabel("Bus stations:", 0, 2);
    TableView busTable = makeTable(dateOptions.getItems().get(0), 0);
    TableView subwayTable = makeTable(dateOptions.getItems().get(0), 1);
    dateOptions.setOnAction(
        e -> {
          busTable.setItems(FXCollections.observableList(generateData(dateOptions.getValue(), 0)));
          subwayTable.setItems(FXCollections.observableList(generateData(dateOptions.getValue(), 1)));
        });
    grid.add(busTable, 0, 3);
    placeLabel("Subway stations:", 0, 4);
    grid.add(subwayTable, 0, 5);
    placeButton(
        "Go back",
        () -> primaryStage.setScene(new AdminUserPage(primaryStage, admin).getScene()),
        0,
        6);
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  /**
   * Makes the table to be displayed in this StationTablePage
   *
   * @param selectedDate the date which your data is to be retrieved
   * @return the table to be displayed
   */
  @Override
  protected TableView makeTable(LocalDate selectedDate, int tableOption) {
    TableView table = getTable();

    TableColumn nameColumn = makeNewStringColumn("Station Name", "name");
    TableColumn tapsOnColumn = makeNewIntColumn("Taps In", "tapsIn");
    TableColumn tapsOffColumn = makeNewIntColumn("Taps Out", "tapsOut");

    if (tableOption == 0) {
      ArrayList<StationRow> busStationRows = generateData(selectedDate, 0);
      ObservableList<StationRow> busData = FXCollections.observableArrayList(busStationRows);
      table.setItems(busData);
    } else {
      ArrayList<StationRow> subwayStationRows = generateData(selectedDate, 1);
      ObservableList<StationRow> subwayData = FXCollections.observableArrayList(subwayStationRows);
      table.setItems(subwayData);
    }
    table.getColumns().addAll(nameColumn, tapsOnColumn, tapsOffColumn);
    return table;
  }

  /**
   * Fetches station data and creates StationRows to be displayed
   *
   * @param selectedDate the date which your data is to be retrieved
   * @return the data to be shown in the table
   */
  @Override
  protected ArrayList generateData(LocalDate selectedDate, int tableOption) {
    ArrayList<StationRow> tempList = new ArrayList<>();
    if (tableOption == 0) {
      Collection<Station> busStations = Route.getAllStationsCopy().get(Station.POSSIBLE_TYPES[0]).values();
      for (Station busStation : busStations) {
        tempList.add(new StationRow(busStation, selectedDate));
      }
    } else {
      Collection<Station> subwayStations = Route.getAllStationsCopy().get(Station.POSSIBLE_TYPES[1]).values();
      for (Station subwayStation : subwayStations) {
        tempList.add(new StationRow(subwayStation, selectedDate));
      }
    }
    return tempList;
  }
}
