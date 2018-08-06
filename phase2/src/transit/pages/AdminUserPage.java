package transit.pages;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
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
   * @param primaryStage the stage which this page is being served on, passed for button-action
   * @param adminUser the AdminUser whose page is to be constructed
   */
  public AdminUserPage(Stage primaryStage, User adminUser) {
    super(primaryStage, adminUser);
    makeScene();
    stage.setScene(this.scene);
    stage.setTitle("Admin Control Panel");
    stage.show();
  }

  /**
   * Makes the scene to be passed to the given stage for display
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
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
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  private void makeSceneButtons() {
    factory.makeLabel(grid, getUserMessage(), 1, 0);
    newUserInfoButton(2, 0);
    newLogoutButton(2, 1);
    newRemoveAccountButton(2, 2);

    factory.makeButton(grid, "System Stats", () -> new AnalyticsPage(Statistics.getSystemStatistics()), 0, 2);
    factory.makeButton(grid, "Update Routes", () -> new RouteCreationPage(), 0, 1);

    Button toggle =
        factory.makeButton(grid, "User view", () -> new UserPage(stage, this.user), 1, 2);
    GridPane.setHalignment(toggle, HPos.RIGHT);
  }
}
