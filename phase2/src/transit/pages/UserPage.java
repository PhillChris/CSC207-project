package transit.pages;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
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
   */
  @Override
  protected void makeScene() {
    // Set grid of grid
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
   */
  private void placeUserButtons() {
    Button cards = factory.makeButton(grid, "Cards", () -> pageCreator.makeCardPage(user.getCardCommands()), 0, 2);
    GridPane.setColumnSpan(cards, 2);
    cards.setMinWidth(320);
    cards.setPadding(new Insets(30, 30, 30, 30));
    GridPane.setMargin(cards, new Insets(30, 0, 0, 0));
    Button info = newUserInfoButton(0, 5);
    info.setMinWidth(cards.getMinWidth() / 2 - 10);
    newLogoutButton(2, 0);
    Button remove = newRemoveAccountButton(1, 5);
    remove.setMinWidth(cards.getMinWidth() / 2 - 10);
    Button name = factory.makeButton(grid, "Change name", () -> pageCreator.makeChangeNamePage(user), 0, 4);
    name.setMinWidth(cards.getMinWidth() / 2 - 10);
    Button pass = factory.makeButton(grid, "Change password", () -> pageCreator.makeChangePasswordPage(user), 1, 4);
    pass.setMinWidth(cards.getMinWidth() / 2 - 10);

    Button stats = factory.makeButton(
        grid,
        "Get Stats",
            () -> pageCreator.makeUserAnalyticsPage(user),
        0,
        3);
    GridPane.setColumnSpan(stats, 2);
    stats.setMinWidth(320);

    if (user.getCardCommands().getPermission().equals("admin")) {
      Button viewToggle =
          factory.makeButton(grid, "Admin view", () -> new AdminUserPage(stage, user), 2, 5);
      GridPane.setHalignment(viewToggle, HPos.RIGHT);
      GridPane.setHgrow(viewToggle, Priority.ALWAYS);
    }
  }

}
