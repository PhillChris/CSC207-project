package transit.pages;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import transit.system.Route;

/** Represents a page used to make maintenance changes to this system */
public class RouteCreationPage extends Page {

  /** The stage used by this page */
  private Stage stage;

  public RouteCreationPage(Stage primaryStage) {
    this.grid = new GridPane();
    stage = primaryStage;
    grid.setPadding(new Insets(30, 20, 20, 40));
    grid.setHgap(10);
    grid.setVgap(10);
    makeScene(primaryStage);
  }

  /**
   * Sets the scene of this page
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  void makeScene(Stage primaryStage) {
    /** Set the buttons of this page */
    placeButton("Add subway route", () -> createRoute("String"), 0, 0);
    placeButton("Add bus route", () -> createRoute("Bus"), 0, 2);
    placeLabel("Append to Existing Station:", 0, 4);
    /** Create a button to append to each possible route */
    for (String type : Route.getRoutesCopy().keySet()) {
      for (int i = 0; i < Route.getRoutesCopy().get(type).size(); i++) {
        Route route = Route.getRoutesCopy().get(type).get(i);
        placeButton(route.toString(), () -> appendExistingRoute(route), 0, 6 + 2 * i);
      }
    }

    this.scene = new Scene(grid, 600, 400);
  }

  /** Creates a page to create a new subway route */
  private void createRoute(String type) {
    Route route = new Route(type);
    AppendRoutePage appendPage = new AppendRoutePage(stage, route);
    stage.setScene(appendPage.getScene());
  }

  /** Creates a page to append to an existing station */
  private void appendExistingRoute(Route route) {
    AppendRoutePage appendPage = new AppendRoutePage(stage, route);
    stage.setScene(appendPage.getScene());
  }
}
