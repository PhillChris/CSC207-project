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

  public MaintenancePage(Stage primaryStage) {
    makeScene(primaryStage);
  }
  @Override
  void makeScene(Stage primaryStage) {
    placeLabel("Station name: ", 0, 1);
    TextField stationName = placeTextField(1, 1);

    placeLabel("Station type: ", 0, 2);
    ChoiceBox<String> stationTypes = new ChoiceBox<>();
    stationTypes.getItems().addAll("Bus", "Subway");
    stationTypes.getSelectionModel().select(0);

    grid.add(stationTypes, 1, 2);
    placeButton(
            "Make Station", () -> makeNewStation(stationName.getText(), stationTypes.getValue()), 1, 3);

    ChoiceBox<String> routeType = new ChoiceBox();
    routeType.getItems().addAll("Bus", "Subway");
    grid.add(routeType, 0, 5);
    routeType.setOnAction(
            e -> {
              grid.add(addNewRoute(routeType.getValue()), 2, 5);
            });
    routeType.getSelectionModel().select(0);
    this.scene = new Scene(grid, 300, 250);
  }

  private void makeNewStation(String name, String type) {
    try {
      new Station(name, type);
    } catch (InvalidInputException e) {
      makeAlert(
              "Station Construction",
              "Station name",
              "A station of the same type and name already exists.",
              Alert.AlertType.WARNING)
              .showAndWait();
    }
  }

  private CheckComboBox<Station> addNewRoute(String type) {
    ObservableList<Station> stations;
    try {
      stations = FXCollections.observableList(Station.getStations(type));
    } catch (Exception e) {
      stations = FXCollections.observableList(new ArrayList<>());
    }
    CheckComboBox<Station> dropDownList = new CheckComboBox<>(stations);
    placeButton(
            "Make Route",
            () -> new Route(dropDownList.getCheckModel().getCheckedItems()),
            3,
            5);
    return dropDownList;
  }
}
