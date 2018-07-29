package transit.pages;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;
import transit.system.Route;
import transit.system.Station;

/** Represents a page used to make maintenance changes to this system */
public class RouteCreationPage extends Page {
  /** A dropDownList of stations */
  private CheckComboBox<Station> dropDownList = new CheckComboBox<>();
  /** A checkbox indicating the type of route */
  private ChoiceBox<String> routeType = new ChoiceBox<>();

  public RouteCreationPage(Stage primaryStage) {
    makeScene(primaryStage);
  }

  /**
   * Sets the scene of this page
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  void makeScene(Stage primaryStage) {
    /** Set the grid of this page */
    grid.setPadding(new Insets(30, 20, 20, 40));
    grid.setHgap(10);
    grid.setVgap(10);
    placeButton("Add subway route", () -> createSubwayRoute(), 0, 0);
    placeButton("Add bus route", () -> createBusRoute(), 0, 2);
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
  private void createSubwayRoute() {}

  /** Creates a page to create a new bus route */
  private void createBusRoute() {}

  /** Creates a page to append to an existing station */
  private void appendExistingRoute(Route route) {}
}
