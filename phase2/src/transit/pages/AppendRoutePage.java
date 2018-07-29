package transit.pages;

import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import transit.system.Route;

/** Page used to add new stations to a route */
public class AppendRoutePage extends Page {
  /** The route associated with this page */
  private Route route;

  /** A Label designed to represent the route associated with this page */
  private Label routeLabel;

  /** The stage used by this page */
  private Stage stage;

  /**
   * Initializes a new instance of AppendRoutePage
   *
   * @param stage
   * @param route
   */
  public AppendRoutePage(Stage stage, Route route) {
    this.stage = stage;
    this.route = route;
    routeLabel = new Label(this.route.toString());
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
            if (!this.route.containsName(textField.getText())) {
              this.route.addStationAtStart(textField.getText());
            } else {
              makeAlert(
                  "Station Name In Use",
                  "Station Name In Use:",
                  "This station name is used in this route",
                  AlertType.ERROR).showAndWait();
            }
          }
          routeLabel.setText(this.route.toString());
        },
        10,
        15);

    placeButton(
        "Add Station at End",
        () -> {
          if (textField.getText() != null) {
            if (!this.route.containsName(textField.getText())) {
              this.route.addStationAtEnd(textField.getText());
            } else {
              makeAlert(
                  "Station Name In Use",
                  "Station Name In Use:",
                  "This station name is used in this route",
                  AlertType.ERROR).showAndWait();
            }
          }
          routeLabel.setText(this.route.toString());
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
                  this.route.saveRoute();
                  stage.close();
                }),
        20,
        20);

    this.scene = new Scene(grid, 1000, 1000);
  }
}
