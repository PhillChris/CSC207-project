package transit.pages;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.Route;

/** Represents a page used to make maintenance changes to this system */
public class RouteCreationPage extends Page {

  /**
   * Construct a new instance of RouteCreationPage
   *
   * @param stage The stage for this page to be displayed
   */
  public RouteCreationPage(Stage stage) {
    super(stage);
    makeScene();
    stage.setTitle("Create/Edit Routes");
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Sets the scene of this page
   */
  @Override
  void makeScene() {
    grid.setPadding(new Insets(30, 20, 20, 40));
    grid.setHgap(10);
    grid.setVgap(10);
    /** Set the buttons of this page */
    factory.makeButton(
            grid,
            "Create Subway Route",
            () -> pageCreator.makeAppendRoutePage(new Route("Subway")),
            0,
            0);
    factory.makeButton(
            grid, "Create Bus Route", () -> pageCreator.makeAppendRoutePage(new Route("Bus")), 0, 2);
    factory.makeLabel(grid, "Append to Existing Route:", 0, 4);
    /** Create a button to append to each possible route */
    int j = 0;
    for (String type : Route.getRoutesCopy().keySet()) {
      factory.makeLabel(grid, type + " Routes", j, 5);
      for (int i = 0; i < Route.getRoutesCopy().get(type).size(); i++) {
        Route route = Route.getRoutesCopy().get(type).get(i);
        factory.makeButton(
                grid, route.toString(), () -> pageCreator.makeAppendRoutePage(route), j, 6 + 2 * i);
        factory.makeButton(
            grid, "Remove this route!", () -> route.removeRoute(), j + 1, 6 + 2 * i);
      }
      j += 2;
    }

    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    scene.getStylesheets().add(getClass().getResource("styling/GeneralStyle.css").toExternalForm());
  }
}
