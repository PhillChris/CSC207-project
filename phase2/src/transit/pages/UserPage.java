package transit.pages;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
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
    addGreeting();

    scene
      .getStylesheets()
      .add(LoginPage.class.getResource("styling/UserPage.css").toExternalForm());
  }

  /** Adds the user-specific data on this page */
  protected void addGreeting() {
    placeLabel(grid, String.format("Hello %s", user.getUserName()), 0, 1, "greeting");
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
          Stage newStage = new Stage();
          CardPage cardPage = new CardPage(newStage, this.user);
          newStage.setScene(cardPage.getScene());
          newStage.show();
        },
        0,
        2);
    placeButton(
        "Monthly Expenditure (Current Year)",
      this::makeMonthlyExpenditurePage,
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
                  "Nothing right now",
                  AlertType.INFORMATION);
          a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
          a.showAndWait();
        },
        0,
        7);

    if (user.getPermission().equals("admin")) {
      placeButton(
          "Toggle admin panel",
          () -> primaryStage.setScene(new AdminUserPage(primaryStage, user).getScene()),
          0,
          8);
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
