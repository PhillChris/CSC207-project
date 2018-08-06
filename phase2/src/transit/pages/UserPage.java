package transit.pages;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.User;

/** Represents a page displayed when a user logs in in this transit system */
public class UserPage extends AuthenticatedPage {

  /**
   * Constructs a new UserPage in this simulation
   *
   * @param primaryStage the stage on which this page is being served
   * @param user the user whose page is being displayed
   */
  public UserPage(Stage primaryStage, User user) {
    super(primaryStage, user);
  }

  /**
   * Makes the scene in which this UserPage is being displayed
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  protected void makeScene() {
    // Set layout of grid
    grid.setPadding(new Insets(20, 20, 20, 40));
    grid.setHgap(10);
    grid.setVgap(10);
    // Add content to scene
    factory.addClock(grid);
    placeUserButtons();
    addGreeting();
    scene = new Scene(grid, 600, 375);
    // Style the scene
    scene
        .getStylesheets()
        .add(LoginPage.class.getResource("styling/UserPage.css").toExternalForm());
  }

  /**
   * Places the buttons on this UserPage
   *
   * @param primaryStage the stage on which this page is served
   */
  private void placeUserButtons() {
    newUserInfoButton(1, 0);
    newLogoutButton(2, 0);
    newRemoveAccountButton(0, 6);
    factory.makeButton(grid, "Change name", () -> new ChangeNamePage(stage, user), 0, 4);
    factory.makeButton(grid, "Change password", () -> new ChangePasswordPage(user), 0, 5);
    factory.makeButton(grid, "Cards", () -> new CardPage(new Stage(), user.getCardCommands()), 0, 2);

    factory.makeButton(
        grid,
        "Get Stats",
        () -> new AnalyticsPage(user.getCardCommands().getCardStatistics()),
        0,
        3);

    factory.makeButton(
        grid,
        "Get last 3 trips",
        () -> {
          Alert a =
              factory.makeAlert(
                  "Last 3 trips",
                  "Last 3 trips of user " + user,
//                  "Nothing right now",
                      user.getCardCommands().lastThreeTripsString(),
                  AlertType.INFORMATION);
          a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
          a.showAndWait();
        },
        0,
        7);

    if (user.getCardCommands().getPermission().equals("admin")) {
      Button viewToggle =
          factory.makeButton(grid, "Admin view", () -> new AdminUserPage(stage, user), 2, 7);
      GridPane.setHalignment(viewToggle, HPos.RIGHT);
      GridPane.setHgrow(viewToggle, Priority.ALWAYS);
    }
  }

}
