package transit.pages;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import transit.system.LogWriter;
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
    grid.setPadding(new Insets(20, 20, 20, 40));
    grid.setHgap(10);
    grid.setVgap(10);

    newUserInfoButton(1, 0);
    newLogoutButton(primaryStage, 2, 0);
    newChangeUserButton(primaryStage, 0, 4);
    newRemoveAccountButton(primaryStage, 0, 6);
    newChangePassButton(primaryStage, 0, 5);
    addClock();
    this.scene = new Scene(grid, 600, 375);
  }

  /**
   * A private helper to make a new userInfoButton at the given coordinates
   *
   * @param col the column in the grid where this user info button is displayed
   * @param row the row in the grid where this user info button is displayed
   */
  private void newUserInfoButton(int col, int row) {
    Button info = makeButton(grid,
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
    GridPane.setHalignment(info, HPos.RIGHT);
    GridPane.setHgrow(info, Priority.ALWAYS);
  }

  /**
   * A private helper method generating the user information to be displayed
   *
   * @return the user information to be displayed
   */
  private String getUserMessage() {
    String temp =
        "Username: "
            + user
            + System.lineSeparator()
            + "Permission: "
            + user.getCardCommands().getPermission();
    for (Integer id : this.user.getCardCommands().getCardsCopy().keySet()) {
      temp += System.lineSeparator();
      temp += user.getCardCommands().getCardsCopy().get(id);
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
  public void newLogoutButton(Stage primaryStage, int col, int row) { Button logout = makeButton(grid,
        "Logout", () -> primaryStage.setScene(new LoginPage(primaryStage).getScene()), col, row);
    GridPane.setHalignment(logout, HPos.RIGHT);
    GridPane.setHgrow(logout, Priority.ALWAYS);
  }

  /**
   * A private helper method to add a change username button at the given coordinates
   *
   * @param primaryStage the stage which this button is being served on, passed for button-action
   * @param col the column in the grid where this change username button is displayed
   * @param row the row in the grid where this change username button is displayed
   */
  private void newChangeUserButton(Stage primaryStage, int col, int row) {
    makeButton(grid,
        "Change username",
        () -> {
          ChangeNamePage namePage = new ChangeNamePage(primaryStage, this.user);
          Stage nameWindow = new Stage();
          nameWindow.setTitle("Change username");
          nameWindow.setScene(namePage.getScene());
          nameWindow.show();
        },
        col,
        row);
  }

  private void newChangePassButton(Stage primaryStage, int col, int row) {
    makeButton(grid,
      "Change password",
      () -> {
        ChangePasswordPage passwordPage = new ChangePasswordPage(primaryStage, user);
        Stage passWindow = new Stage();
        passWindow.setTitle("Change password");
        passWindow.setScene(passwordPage.getScene());
        passWindow.show();
      },
      col,
      row
    );
  }

  /**
   * A private helper method to add a new remove account button at the given coordinates
   *
   * @param primaryStage the stage which this button is being served on, passed for button-action
   * @param col the column in the grid where this remove account button is displayed
   * @param row the row in the grid where this remove account button is displayed
   */
  private void newRemoveAccountButton(Stage primaryStage, int col, int row) {
    makeButton(grid,
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

  private void logout(Stage primaryStage) {
    primaryStage.setScene(new LoginPage(primaryStage).getScene());
    LogWriter.getLogWriter()
        .logInfoMessage(
            AuthenticatedPage.class.getName(),
            "logout",
            "User " + user.getPersonalInfo().getUserName() + " logged out of the transit system");
  }
}
