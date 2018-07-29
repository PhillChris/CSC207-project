package transit.pages;

import javafx.geometry.Insets;
import javafx.scene.Scene;
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

  /** A textfield designed to take the names of new stations */
  private TextField userEntry;

  /**
   * Initializes a new instance of AppendRoutePage
   *
   * @param stage
   * @param route
   */
  public AppendRoutePage(Stage stage, Route route) {
    this.route = route;
    routeLabel = new Label();
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
    grid.setPadding(new Insets(20, 10, 10, 20));
    grid.setHgap(10);
    grid.setVgap(10);
    grid.add(routeLabel, 0, 0);
    placeLabel("Enter the name of the new station here", 8, 9);
    TextField textField = placeTextField(10, 10);
    placeButton(
        "Add Station",
        () -> {
          this.route.addStation(userEntry.getText());
          routeLabel.setText(this.route.toString());
        },
        10,
        15);

    placeButton(
        "Confirm",
        () -> {
          makeConfirmationAlert(
              "Confirm Route?",
              "",
              "Would you like to confirm the creation of this route?",
              () -> {
                this.route.saveRoute();
              });
        },
        20,
        20);

    this.scene = new Scene(grid, 100, 100);
  }
}
