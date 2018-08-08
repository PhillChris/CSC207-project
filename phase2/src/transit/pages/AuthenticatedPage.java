package transit.pages;

import javafx.geometry.HPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
   * Abstract constructor: associating the given user with this authenticated page
   *
   * @param stage the stage on which this authenticated page is served
   * @param user the user associated with this authenticated page
   */
  public AuthenticatedPage(Stage stage, User user) {
    super(stage);
    this.user = user;
    makeScene();
    setAndShow(String.format("%s's Control Panel", user));
  }

  /** Adds the user-specific data on this page */
  protected void addGreeting() {
    Label greeting =
        factory.makeLabel(
            grid, String.format("Hello %s", user.getPersonalInfo().getUserName()), 0, 1);
    greeting.setId("greeting");
  }

  /**
   * A private helper to make a new userInfoButton at the given coordinates
   *
   * @param col the column in the grid where this user info button is displayed
   * @param row the row in the grid where this user info button is displayed
   */
  protected Button newUserInfoButton(int col, int row) {
    Button info =
        factory.makeButton(
            grid,
            "Info",
            () -> {
              Alert alert =
                  factory.makeAlert(
                      "User Information",
                      "Your user information:",
                      getUserMessage(),
                      Alert.AlertType.INFORMATION);
              alert.showAndWait();
            },
            col,
            row);
    return info;
  }

  /**
   * A private helper method generating the user information to be displayed
   *
   * @return the user information to be displayed
   */
  protected String getUserMessage() {
    String temp =
        "Username: "
            + user
            + System.lineSeparator()
            + "Permission: "
            + user.getCardCommands().getPermission();
    return temp;
  }

  /**
   * A private helper method to add a logout button at the given coordinates
   *
   * @param col the column in the grid where this logout button is displayed
   * @param row the row in the grid where this logout button is displayed
   */
  protected void newLogoutButton(int col, int row) {
    Button logout = factory.makeButton(grid, "Logout", () -> logout(), col, row);
    GridPane.setHalignment(logout, HPos.RIGHT);
    GridPane.setHgrow(logout, Priority.ALWAYS);
  }

  /**
   * A private helper method to add a new remove account button at the given coordinates
   *
   * @param col the column in the grid where this remove account button is displayed
   * @param row the row in the grid where this remove account button is displayed
   */
  protected Button newRemoveAccountButton(int col, int row) {
    return factory.makeButton(
        grid,
        "Remove account",
        () ->
            factory.makeConfirmationAlert(
                "Delete account confirmation",
                "Confirm:",
                "Are you sure you want this account to be removed?",
                () -> {
                  user.removeUser();
                  new LoginPage(stage);
                }),
        col,
        row);
  }

  /** Logs the user out */
  protected void logout() {
    new LoginPage(stage);
    LogWriter.getInstance()
        .logInfoMessage(
            AuthenticatedPage.class.getName(),
            "logout",
            "User " + user.getPersonalInfo().getUserName() + " logged out of the transit system");
  }
}
