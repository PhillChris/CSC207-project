package transit.system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.util.ArrayList;

public class MaintenancePage extends Page {
  private CheckComboBox<Station> dropDownList = new CheckComboBox<>();
  private ChoiceBox<String> routeType = new ChoiceBox<>();

  public MaintenancePage(Stage primaryStage) {
    makeScene(primaryStage);
  }

  @Override
  void makeScene(Stage primaryStage) {
    placeLabel("Make route!", 0, 5);
    routeType.getItems().addAll("Bus", "Subway");
    grid.add(routeType, 0, 6);
    routeType.setOnAction(
            e -> {
              grid.getChildren().remove(this.dropDownList);
              grid.add(addNewRoute(routeType.getValue()), 2, 6);
            });
    routeType.getSelectionModel().select(0);

    makeStationConstruction();
    this.scene = new Scene(grid, 300, 250);
  }

  private void makeStationConstruction() {
    placeLabel("Station name: ", 0, 1);
    TextField stationName = placeTextField(1, 1);

    placeLabel("Station type: ", 0, 2);
    ChoiceBox<String> stationTypes = new ChoiceBox<>();
    stationTypes.getItems().addAll("Bus", "Subway");
    stationTypes.getSelectionModel().select(0);

    grid.add(stationTypes, 1, 2);
    placeButton(
            "Make Station",
            () -> {
              try {
                String stationType = stationTypes.getValue();
                Station newStation = new Station(stationName.getText(), stationType);
                if (routeType.getValue().equals(stationType)) {
                  this.dropDownList.getItems().add(newStation);
                }
              } catch (InvalidInputException e) {
                makeAlert(
                        "Station Construction",
                        "Station name",
                        "A station of the same type and name already exists.",
                        Alert.AlertType.WARNING)
                        .showAndWait();
              }
            },
            1,
            3);
  }

  /**
   * Constructs the UI for making routes from Stations of the given type.
   *
   * @param type the type of Stations to display in the dropDownList.
   * @return
   */
  private CheckComboBox<Station> addNewRoute(String type) {
    ObservableList<Station> stations;
    try {
      stations = FXCollections.observableList(Station.getStations(type));
    } catch (Exception e) {
      stations = FXCollections.observableList(new ArrayList<>());
    }
    ObservableList<Station> oldStations = this.dropDownList.getItems();
    oldStations.remove(0, oldStations.size()); // clear the old stations from view
    // add to the drop down list all the stations of given type.
    this.dropDownList.getItems().addAll(stations);
    // the button that constructs the routes from the stations selected in the dropDownList.
    placeButton(
            "Make Route",
            () -> new Route(dropDownList.getCheckModel().getCheckedItems()),
            3,
            6);
    return dropDownList;
  }
}
