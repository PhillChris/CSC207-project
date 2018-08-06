package transit.pages;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import transit.system.Route;

/** Represents a page used to make maintenance changes to this system */
public class RouteCreationPage extends Page {

  /**
   * Construct a new instance of RouteCreationPage
   *
   * @param primaryStage The stage for this page to be displayed
   */
  public RouteCreationPage() {
    super(new Stage());
    this.grid = new GridPane();
    grid.setPadding(new Insets(30, 20, 20, 40));
    grid.setHgap(10);
    grid.setVgap(10);
    makeScene();
    stage.setTitle("Create/Edit Routes");
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
    /** Set the buttons of this page */
    Button createSubway =
        factory.makeButton(
            grid,
            "Create Subway Route",
            () -> new AppendRoutePage(new Route("Subway")),
            0,
            0);
    Button createBus =
        factory.makeButton(
            grid,
            "Create Bus Route",
            () -> new AppendRoutePage(new Route("Bus")),
            0,
            2);
    factory.makeLabel(grid, "Append to Existing Route:", 0, 4);
    /** Create a button to append to each possible route */
    for (String type : Route.getRoutesCopy().keySet()) {
      for (int i = 0; i < Route.getRoutesCopy().get(type).size(); i++) {
        Route route = Route.getRoutesCopy().get(type).get(i);
        Button routeButton =
            factory.makeButton(
                grid, route.toString(), () -> new AppendRoutePage(route), 0, 6 + 2 * i);
      }
    }
    this.scene = new Scene(grid, 600, 400);
  }
}
