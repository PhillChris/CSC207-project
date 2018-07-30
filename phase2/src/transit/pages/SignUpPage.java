package transit.pages;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.AdminUser;
import transit.system.User;

/** Represents a page opened when making a new account in this transit system */
public class SignUpPage extends Page {

  /**
   * Constructs a new SignUpPage
   *
   * @param primaryStage the stage on which this SignUpPage is being served
   */
  public SignUpPage(Stage primaryStage) {
    makeScene(primaryStage);
  }

  /**
   * Makes a scene for
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  protected void makeScene(Stage primaryStage) {
    placeLabels();

    TextField username = placeTextField(1, 1);
    TextField email = placeTextField(1, 2);
    PasswordField password = placePasswordField(1, 3);
    CheckBox adminBox = placeCheckBox("Is Admin?", 1, 4);

    makeButtons(
        primaryStage,
        username,
        email,
        password,
        adminBox);

    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  /**
   * @param username the proposed username of this new user
   * @param email the proposed email of this new user
   * @param password the proposed password of this new user
   * @param isAdmin whether or not this new user is an AdminUser
   * @throws Exception if there is a problem in constructing a user with these given parameters
   */
  void add(String username, String email, String password, boolean isAdmin) throws Exception {
    if (isAdmin) {
      AdminUser admin = new AdminUser(username, email, password);
    } else {
      User user = new User(username, email, password);
    }
  }

  /** Places the labels in this signup page */
  private void placeLabels() {
    placeLabel("User: ", 0, 1);
    placeLabel("New Email: ", 0, 2);
    placeLabel("New Password: ", 0, 3);
  }

  /**
   * Constructs and places the buttons in this SignUp page
   *
   * @param primaryStage the stage on which this SignUp page is being served
   * @param username the username of the user which is being created
   * @param email the email of the user which is being created
   * @param password the password of the user which is being created
   * @param isAdmin whether or not the user being created is an admin
   */
  private void makeButtons(
      Stage primaryStage, TextField username, TextField email, PasswordField password, CheckBox adminBox) {
    placeButton(
        "Go back", () -> primaryStage.setScene(new LoginPage(primaryStage).getScene()), 1, 5);

    placeButton(
        "Make Account!",
        () -> {
          try {
            add(username.getText(), email.getText(), password.getText(), adminBox.isSelected());
            primaryStage.setScene(new LoginPage(primaryStage).getScene());
          } catch (Exception a) {
            Alert alert =
                makeAlert(
                    "Email in use!",
                    "Email in use:",
                    "The email you provided is not valid or"
                        + " is currently in use by another user.",
                    AlertType.WARNING);
            alert.showAndWait();
          }
        },
        1,
        6);
  }
}
