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

public class AdminUserPage extends AuthenticatedPage {

  public AdminUserPage(Stage primaryStage, User user) {
    super(primaryStage, user);
    makeScene(primaryStage);
  }

  @Override
  protected void makeScene(Stage primaryStage) {
    placeLabel("Station name: ", 0, 1);
    TextField stationName = placeTextField(1, 1);

    placeLabel("Station type: ", 0, 2);
    ChoiceBox<String> stationTypes = new ChoiceBox<>();
    stationTypes.getItems().addAll("Bus", "Subway");
    stationTypes.getSelectionModel().select(0);

    grid.add(stationTypes, 1, 2);
    placeButton(
        "Make Station", () -> makeNewStation(stationName.getText(), stationTypes.getValue()), 1, 3);

    placeButton(
            "Monthly Revenue (Current Year)",
            () -> {
              Stage secondaryStage = new Stage();
              UserGraphPage graphPage = new UserGraphPage(secondaryStage, this.user);
              secondaryStage.setTitle("Monthly Revenue for " + TransitTime.getCurrentDate().getYear());
              secondaryStage.setScene(graphPage.getScene());
              secondaryStage.show();
            },
            0,
            2);

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

  public void addUserData(Stage primaryStage) {}

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
            () -> {
              new Route(dropDownList.getCheckModel().getCheckedItems());
            },
            3,
            5);
    return dropDownList;
  }
}
