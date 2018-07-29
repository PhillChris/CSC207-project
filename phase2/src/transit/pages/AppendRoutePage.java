package transit.pages;

import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import transit.system.Route;
import transit.system.Station;

import java.util.ArrayList;

/** Page used to add new stations to a route */
public class AppendRoutePage extends Page {
  /** The route associated with this page */
  private Route route;

  /** A Label designed to represent the route associated with this page */
  private Label routeLabel;

  /** The stage used by this page */
  private Stage stage;

  /** The list of stations added and viewed by the user */
  private ArrayList<String> stationNames;

  /**
   * Initializes a new instance of AppendRoutePage
   *
   * @param stage
   * @param route
   */
  public AppendRoutePage(Stage stage, Route route) {
    this.stage = stage;
    this.route = route;
    stationNames = new ArrayList<>();
    for (Station station : route.getRouteStationsCopy()) {
      stationNames.add(station.toString());
    }
    routeLabel = new Label();
    setRouteLabel();
    makeScene(stage);
  }

  /**
   * Sets the scene of this page
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  void makeScene(Stage primaryStage) {
    /** Set the grid of this page */
    grid.add(routeLabel, 0, 0, 20, 2);
    placeLabel("Enter the name of the new station here", 8, 9);
    TextField textField = placeTextField(10, 10);
    placeButton(
        "Add Station at Start",
        () -> {
          if (textField.getText() != null) {
            if (!stationNames.contains(textField.getText())) {
              stationNames.add(0, textField.getText());
            } else {
              makeAlert(
                      "Station Name In Use",
                      "Station Name In Use:",
                      "This station name is used in this route",
                      AlertType.ERROR)
                  .showAndWait();
            }
          }
          setRouteLabel();
        },
        10,
        15);

    placeButton(
        "Add Station at End",
        () -> {
          if (textField.getText() != null) {
            if (!stationNames.contains(textField.getText())) {
              stationNames.add(textField.getText());
            } else {
              makeAlert(
                      "Station Name In Use",
                      "Station Name In Use:",
                      "This station name is used in this route",
                      AlertType.ERROR)
                  .showAndWait();
            }
          }
          setRouteLabel();
        },
        10,
        16);

    placeButton(
        "Confirm",
        () ->
            makeConfirmationAlert(
                "Confirm Route?",
                "",
                "Would you like to confirm the creation of this route?",
                () -> {
                  this.route.setRouteStations(stationNames);
                  this.route.saveRoute();
                  stage.close();
                }),
        20,
        20);

    this.scene = new Scene(grid, 1000, 1000);
  }

  /** Sets the label describing the associated route */
  private void setRouteLabel() {
    String text = "Route number: " + this.route.getRouteNum() + System.lineSeparator();
    for (String name : stationNames) {
      text += name + " <-> ";
    }
    routeLabel.setText(text);
  }
}
