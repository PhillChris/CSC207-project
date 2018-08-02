package transit.pages;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.User;

/** Represents a page with an associated user in this system, upon having logged in */
public abstract class AuthenticatedPage extends Page {
  /** The user associated with this given authenticated page */
  protected User user;

  /**
   * Abstract constructor: not to be called directly, associating the given user with this
   * authenticated page
   *
   * @param primaryStage the stage on which this authenticated page is served
   * @param user the user associated with this authenticated page
   */
  public AuthenticatedPage(Stage primaryStage, User user) {
    super(primaryStage);
    this.user = user;
  }

  /**
   * Adds the user data in the given authenticated page
   *
   * @param primaryStage the stage on which this authenticated page is served
   */

  /**
   * Makes the core implementation of UserPage, to be overriden by other authenticated pages
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  protected void makeScene(Stage primaryStage) {
    newUserInfoButton(10, 10);
    newLogoutButton(primaryStage, 10, 0);
    newChangeUserButton(primaryStage, 0, 4);
    newRemoveAccountButton(primaryStage, 0, 6);
    addClock();
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  /**
   * A private helper to make a new userInfoButton at the given coordinates
   *
   * @param col the column in the grid where this user info button is displayed
   * @param row the row in the grid where this user info button is displayed
   */
  private void newUserInfoButton(int col, int row) {
    placeButton(
        "Info",
        () -> {
          Alert alert =
              makeAlert(
                  "User Information",
                  "Your user information:",
                  getUserMessage(),
                  Alert.AlertType.INFORMATION);
          alert.showAndWait();
        },
        col,
        row);
  }

  /**
   * A private helper method generating the user information to be displayed
   *
   * @return the user information to be displayed
   */
  private String getUserMessage(){
    String temp = "Username: " + user + System.lineSeparator() + "Permission: " + user.getPermission();
    for (Integer id: this.user.getCardsCopy().keySet()) {
      temp += System.lineSeparator();
      temp += user.getCardsCopy().get(id);
    }
    return temp;
  }

  /**
   * A private helper method to add a logout button at the given coordinates
   *
   * @param primaryStage the stage which this button is being served on, passed for button-action
   * @param col the column in the grid where this logout button is displayed
   * @param row the row in the grid where this logout button is displayed
   */
  public void newLogoutButton(Stage primaryStage, int col, int row) {
    placeButton(
        "Logout", () -> primaryStage.setScene(new LoginPage(primaryStage).getScene()), col, row);
  }

  /**
   * A private helper method to add a change username button at the given coordinates
   *
   * @param primaryStage the stage which this button is being served on, passed for button-action
   * @param col the column in the grid where this change username button is displayed
   * @param row the row in the grid where this change username button is displayed
   */
  private void newChangeUserButton(Stage primaryStage, int col, int row) {
    placeButton(
        "Change username",
        () -> {
          ChangeNamePage namePage = new ChangeNamePage(primaryStage, this.user);
          primaryStage.setScene(namePage.getScene());
        },
        col,
        row);
  }

  /**
   * A private helper method to add a new remove account button at the given coordinates
   *
   * @param primaryStage the stage which this button is being served on, passed for button-action
   * @param col the column in the grid where this remove account button is displayed
   * @param row the row in the grid where this remove account button is displayed
   */
  private void newRemoveAccountButton(Stage primaryStage, int col, int row) {
    placeButton(
        "Remove this account!",
        () ->
            makeConfirmationAlert(
                "Delete account confirmation",
                "Confirm:",
                "Are you sure you want this account to be removed?",
                () -> {
                  user.removeUser();
                  primaryStage.setScene(new LoginPage(primaryStage).getScene());
                }),
        0,
        6);
  }
}
