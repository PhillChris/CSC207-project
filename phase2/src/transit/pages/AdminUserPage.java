package transit.pages;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
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
    grid.setPadding(new Insets(20, 20, 20, 20));
    grid.setHgap(10);
    grid.setVgap(10);
    addClock();
    makeSceneButtons(primaryStage);

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
  private void makeSceneButtons(Stage primaryStage) {

    makePopupButton(grid, new SystemGraphPage(primaryStage), 0, 2);
    makePopupButton(grid, new RouteCreationPage(primaryStage), 0, 1);

    Button toggle = makeButton(grid,
        "User view",
        () -> primaryStage.setScene(new UserPage(primaryStage, this.adminUser).getScene()),
        1,
        2);
    GridPane.setHalignment(toggle, HPos.RIGHT);
    Button logout = makeButton(grid,
        "Logout", () -> primaryStage.setScene(new LoginPage(primaryStage).getScene()), 1, 1);
    GridPane.setHalignment(logout, HPos.RIGHT);
  }
}
