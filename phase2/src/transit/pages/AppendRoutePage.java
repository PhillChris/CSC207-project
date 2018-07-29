package transit.pages;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import transit.system.Route;

/** Page used to add new stations to a route */
public class AppendRoutePage extends Page {
  /** The route associated with this page */
  private Route route;

  /**
   * Initializes a new instance of AppendRoutePage
   *
   * @param stage
   * @param route
   */
  public AppendRoutePage(Stage stage, Route route) {
    this.route = route;
    makeScene(stage);
  }

  /**
   * Sets the scene of this page
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  void makeScene(Stage primaryStage) {}

  /** @return A label designed to describe the route associated with this page */
  private Label routeLabel() {
    Label routeLabel = new Label();
    return routeLabel;
  }
}
