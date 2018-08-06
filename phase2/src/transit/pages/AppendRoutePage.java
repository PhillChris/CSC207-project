package transit.pages;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import transit.system.Route;
import transit.system.Station;

/** Page used to add new stations to a route */
public class AppendRoutePage extends Page {
  /** The route associated with this page */
  private Route route;

  /** A Label designed to represent the route associated with this page */
  private Label routeLabel;

  /** The list of stations added and viewed by the user */
  private ArrayList<String> stationNames;

  /**
   * Initializes a new instance of AppendRoutePage
   *
   * @param stage
   * @param route
   */
  public AppendRoutePage(Route route) {
    super(new Stage());
    this.route = route;
    stationNames = new ArrayList<>();
    for (Station station : route.getRouteStationsCopy()) {
      stationNames.add(station.toString());
    }
    routeLabel = new Label();
    setRouteLabel();
    makeScene();
    stage.setTitle(String.format("Append %s Route", route.getRouteType()));
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Sets the scene of this page
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  void makeScene() {
    /** Set the grid of this page */
    grid.add(routeLabel, 0, 0, 20, 2);
    factory.makeLabel(grid, "Enter the name of the new station here", 8, 9);
    makeSceneButtons();
    this.scene = new Scene(grid, 1000, 200);
  }

  /** Constructs all buttons in this scene */
  private void makeSceneButtons() {
    TextField textField = factory.makeTextField(grid, "", 10, 10);

    factory.makeButton(grid, "Add Station at Start", () -> {
      addStationAtStart(textField);
      textField.clear();
    }, 10, 15);

    factory.makeButton(grid, "Add Station at End", () -> {
      addStationAtEnd(textField);
      textField.clear();
    }, 10, 16);

    factory.makeButton(grid,
        "Confirm",
        () ->
        {
          factory.makeConfirmationAlert(
            "Confirm Route?",
            "",
            "Would you like to confirm the creation of this route?",
            () -> {
              this.route.setRouteStations(stationNames);
              this.route.saveRoute();
              stage.close();
            });
        },
        20,
        20);
  }

  /**
   * Adds a station at the start of the current route plan
   *
   * @param textField the textfield containing the name of the station to be added at the beginning
   *     of the route
   */
  private void addStationAtStart(TextField textField) {
    if (textField.getText() != null) {
      if (!stationNames.contains(textField.getText())) {
        stationNames.add(0, textField.getText());
      } else {
        factory.makeAlert(
                "Station Name In Use",
                "Station Name In Use:",
                "This station name is used in this route",
                AlertType.ERROR)
            .showAndWait();
      }
    }
    setRouteLabel();
  }

  /**
   * Adds a station at the end of the current route plan
   *
   * @param textField the textfield containing the name of the station to be added at the beginning
   *     of the route
   */
  private void addStationAtEnd(TextField textField) {
    if (textField.getText() != null) {
      if (!stationNames.contains(textField.getText())) {
        stationNames.add(textField.getText());
      } else {
        factory.makeAlert(
                "Station Name In Use",
                "Station Name In Use:",
                "This station name is used in this route",
                AlertType.ERROR)
            .showAndWait();
      }
    }
    setRouteLabel();
  }

  /** Sets the label describing the associated route */
  private void setRouteLabel() {
    String text = "Route number: " + this.route.getRouteNum() + System.lineSeparator();
    for (int i = 0; i < stationNames.size(); i++) {
      text += stationNames.get(i);
      if (i < stationNames.size() - 1) {
        text += " <-> ";
      }
    }
    routeLabel.setText(text);
  }
}
