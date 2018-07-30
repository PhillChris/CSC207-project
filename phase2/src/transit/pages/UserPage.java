package transit.pages;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.AdminUser;
import transit.system.TransitTime;
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
    addUserData();
    makeScene(primaryStage);
  }

  /**
   * Makes the scene in which this UserPage is being displayed
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  protected void makeScene(Stage primaryStage) {
    placeUserButtons(primaryStage);
  }

  /** Adds the user-specific data on this page */
  protected void addUserData() {
    placeLabel("Hello " + user.getUserName(), 0, 1);
  }

  /**
   * Places the buttons on this UserPage
   *
   * @param primaryStage the stage on which this page is served
   */
  private void placeUserButtons(Stage primaryStage) {
    placeButton(
        "Cards",
        () -> {
          CardPage cardPage = new CardPage(primaryStage, this.user);
          primaryStage.setScene(cardPage.getScene());
        },
        0,
        2);
    placeButton(
        "Monthly Expenditure (Current Year)",
        () -> makeMonthlyExpenditurePage(),
        0,
        3);
    super.makeScene(primaryStage);

    placeButton(
        "Get last 3 trips",
        () -> {
          Alert a =
              makeAlert(
                  "Last 3 trips",
                  "Last 3 trips of user " + user,
                  user.getLastThreeMessage(),
                  AlertType.INFORMATION);
          a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
          a.showAndWait();
        },
        0,
        7);

    placeButton("Pause time", () -> TransitTime.pauseTime(), 0, 15);

    placeButton("Start time", () -> TransitTime.startTime(), 0, 16);

    placeButton("Jump ahead 1 hour", () -> TransitTime.fastForward(), 0, 17);

    if (user instanceof AdminUser) {
      placeButton(
          "Toggle admin panel",
          () -> primaryStage.setScene(new AdminUserPage(primaryStage, (AdminUser) user).getScene()),
          0,
          20);
    }
  }

  /** Creates a popup window containing a monthly expenditure page*/
  private void makeMonthlyExpenditurePage() {
    Stage secondaryStage = new Stage();
    UserGraphPage graphPage = new UserGraphPage(secondaryStage, this.user);
    secondaryStage.setTitle("Monthly Expenditure for user " + user);
    secondaryStage.setScene(graphPage.getScene());
    secondaryStage.show();
  }
}
