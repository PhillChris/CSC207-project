package transit.pages;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.Statistics;
import transit.system.User;

/** Represents the page seen by an AdminUser when logging into the transit system */
public class AdminUserPage extends AuthenticatedPage {

  /**
   * Constructs an instance of AdminUserPage
   *
   * @param stage the stage which this page is being served on, passed for button-action
   * @param adminUser the AdminUser whose page is to be constructed
   */
  public AdminUserPage(Stage stage, User adminUser) {
    super(stage, adminUser);
    stage.setScene(this.scene);
    stage.setTitle("Admin Control Panel");
    stage.show();
  }

  /**
   * Makes the scene to be passed to the given stage for display
   *
   */
  @Override
  protected void makeScene() {
    grid.setPadding(new Insets(30, 30, 30, 30));
    grid.setHgap(10);
    grid.setVgap(10);
    factory.addClock(grid);
    makeSceneButtons();

    scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    scene
        .getStylesheets()
        .add(LoginPage.class.getResource("styling/UserPage.css").toExternalForm());
  }

  /**
   * Adds all buttons to the AdminUserPage
   */
  private void makeSceneButtons() {
    newLogoutButton(1, 0);

    Button system = factory.makeButton(grid, "System Stats", () -> pageCreator.makeAnalyticsPage(Statistics.getSystemStatistics()), 0, 1);
    system.setMinWidth(150);
    Button station = factory.makeButton(grid, "Station Stats", () -> pageCreator.makeStationGraphPage(), 1, 1);
    station.setMinWidth(150);
    GridPane.setHalignment(station, HPos.RIGHT);
    Button update = factory.makeButton(grid, "Update Routes", () -> pageCreator.makeRouteCreationPage(), 0, 2);
    update.setMinWidth(150);
    Button toggle =
        factory.makeButton(grid, "User view", () -> pageCreator.makeUserPage(user), 1, 2);
    GridPane.setHalignment(toggle, HPos.RIGHT);
    toggle.setMinWidth(150);
  }
}
