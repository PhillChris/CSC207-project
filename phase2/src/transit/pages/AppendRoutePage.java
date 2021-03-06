package transit.pages;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
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
   * @param stage The stage used by this page
   * @param route The route associated with page
   */
  public AppendRoutePage(Stage stage, Route route) {
    super(stage);
    this.route = route;
    stationNames = new ArrayList<>();
    for (Station station : route.getRouteStationsCopy()) {
      stationNames.add(station.toString());
    }
    routeLabel = new Label();
    setRouteLabel();
    makeScene();
    stage.setMaxWidth(1000);
    stage.setTitle(String.format("Create/Append %s Route", route.getRouteType()));
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Sets the scene of this page
   */
  @Override
  void makeScene() {
    // set the grid of this page
    grid.setPadding(new Insets(20, 20, 20, 20));
    grid.setHgap(10);
    grid.setVgap(10);
    grid.add(routeLabel, 0, 0);
    factory.makeLabel(grid, "New station name:", 0, 1);
    makeSceneButtons();
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    scene.getStylesheets().add(getClass().getResource("styling/GeneralStyle.css").toExternalForm());
  }

  /** Constructs all buttons in this scene */
  private void makeSceneButtons() {
    TextField textField = factory.makeTextField(grid, "", 0, 2);

    factory.makeButton(
        grid,
        "Add Station to Start",
        () -> {
          addStationAtStart(textField);
          textField.clear();
        },
        0,
        3);

    factory.makeButton(
        grid,
        "Add Station to End",
        () -> {
          addStationAtEnd(textField);
          textField.clear();
        },
        0,
        4);

    factory.makeButton(
        grid,
        "Confirm",
        () -> makeRouteConfirmation(),
        1,
        0);
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
        makeNameInUseAlert();
      }
    }
    setRouteLabel();
    stage.sizeToScene();
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
        factory
            .makeAlert(
                "Station Name In Use",
                "Station Name In Use:",
                "This station name is used in this route",
                AlertType.ERROR)
            .showAndWait();
      }
    }
    setRouteLabel();
    stage.sizeToScene();
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

  /**
   * Make the route confirmation alert
   */
  private void makeRouteConfirmation() {
    factory.makeConfirmationAlert(
        "Confirm Route?",
        "",
        "Would you like to confirm the creation of this route?",
        () -> {
          this.route.setRouteStations(stationNames);
          this.route.saveRoute();
          stage.close();
        });
  }

  /** Make the station name in use alert*/
  private void makeNameInUseAlert() {
    factory
        .makeAlert(
            "Station Name In Use",
            "Station Name In Use:",
            "This station name is used in this route",
            AlertType.ERROR)
        .showAndWait();
  }
}
