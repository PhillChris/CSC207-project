package transit.pages;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.TransitTime;
import transit.system.User;

/** Represents the page seen by an AdminUser when logging into the transit system */
public class AdminUserPage extends Page {
  /** The AdminUser whose page is being displayed */
  private User adminUser;

  /**
   * Constructs an instance of AdminUserPage
   *
   * @param primaryStage the stage which this page is being served on, passed for button-action
   * @param adminUser the AdminUser whose page is to be constructed
   */
  public AdminUserPage(Stage primaryStage, User adminUser) {
    super(primaryStage);
    this.adminUser = adminUser;
    makeScene(primaryStage);
  }

  /**
   * Makes the scene to be passed to the given stage for display
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  protected void makeScene(Stage primaryStage) {
    makeSceneButtons(primaryStage);
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  /**
   * Adds all buttons to the AdminUserPage
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  private void makeSceneButtons(Stage primaryStage) {

    makePopupButton(grid, new SystemGraphPage(primaryStage), 0, 2);
    makePopupButton(grid, new RouteCreationPage(primaryStage), 0, 4);

    makeButton(grid,
        "User view",
        () -> primaryStage.setScene(new UserPage(primaryStage, this.adminUser).getScene()),
        0,
        6);
    makeButton(grid,
        "Logout", () -> primaryStage.setScene(new LoginPage(primaryStage).getScene()), 0, 9);
  }
}
