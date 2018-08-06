package transit.pages;

import javafx.geometry.HPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.Statistics;
import transit.system.User;

import java.util.HashMap;

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
    makeScene();
  }

  /**
   * Makes the scene in which this UserPage is being displayed
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  protected void makeScene() {
    placeUserButtons();
    addGreeting();

    scene
      .getStylesheets()
      .add(LoginPage.class.getResource("styling/UserPage.css").toExternalForm());
  }

  /** Adds the user-specific data on this page */
  protected void addGreeting() {
    Label greeting = makeLabel(grid, String.format("Hello %s", user.getPersonalInfo().getUserName()), 0, 1);
    greeting.setId("greeting");
  }

  /**
   * Places the buttons on this UserPage
   *
   * @param primaryStage the stage on which this page is served
   */
  private void placeUserButtons() {
    makePopupButton(grid, new CardPage(stage, user), 0, 2);

    makePopupButton(grid, new UserGraphPage(stage, user.getCardCommands().getCardStatistics()), 0, 3);
    super.makeScene();

    makeButton(grid,
        "Get last 3 trips",
        () -> {
          Alert a =
              makeAlert(
                  "Last 3 trips",
                  "Last 3 trips of user " + user,
                  "Nothing right now",
                  AlertType.INFORMATION);
          a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
          a.showAndWait();
        },
        0,
        7);

    if (user.getCardCommands().getPermission().equals("admin")) {
      Button viewToggle = makeButton(grid,
          "Admin view",
          () -> stage.setScene(new AdminUserPage(stage, user).getScene()),
          2,
          7);
      GridPane.setHalignment(viewToggle, HPos.RIGHT);
      GridPane.setHgrow(viewToggle, Priority.ALWAYS);
    }
  }

  /** Creates a popup window containing a monthly expenditure page*/
  private void makeMonthlyExpenditurePage() {
    Stage secondaryStage = new Stage();
    HashMap<String, Statistics> tripStats = user.getCardCommands().getCardStatistics();
    AnalyticsPage graphPage = new UserGraphPage(secondaryStage, tripStats);
    secondaryStage.setTitle("Monthly Expenditure for user " + user);
    secondaryStage.setScene(graphPage.getScene());
    secondaryStage.show();
  }
}
