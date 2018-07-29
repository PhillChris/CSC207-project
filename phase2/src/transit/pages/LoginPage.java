package transit.pages;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import transit.system.AdminUser;
import transit.system.User;

/** Represents a login page for this system */
public class LoginPage extends Page {

  /**
   * Initialized a new instance of LoginPace
   *
   * @param primaryStage The stage for this page to be displayed
   */
  public LoginPage(Stage primaryStage) {
    makeScene(primaryStage);
  }

  /**
   * Sets the scene for this page
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  protected void makeScene(Stage primaryStage) {
    grid.setPadding(new Insets(30, 20, 20, 40));
    grid.setHgap(10);
    grid.setVgap(10);

    makeLoginPane(primaryStage);
    GridPane trainPane = new GridPane();
    ImageView train = new ImageView(new Image("transit/pages/assets/train.png"));
    trainPane.add(train, 0, 0);
    grid.add(trainPane, 1, 1);

    addClock();
    this.scene = new Scene(grid, 625, 400);

    scene
        .getStylesheets()
        .add(LoginPage.class.getResource("styling/LoginPage.css").toExternalForm());
  }

  protected void makeLoginPane(Stage primaryStage) {
    GridPane loginPane = new GridPane();
    loginPane.setHgap(10);
    loginPane.setVgap(12);
    makeLabels(loginPane);
    makeIcons(loginPane);

    TextField emailInput = makeEmailInput();
    loginPane.add(emailInput, 1, 1);
    PasswordField passInput = makePassInput();
    loginPane.add(passInput, 1, 2);

    Button signupButton = makeSignUpButton(primaryStage);
    loginPane.add(signupButton, 0, 6, 2, 1);
    Button loginButton = makeLoginButton(emailInput, passInput, primaryStage);
    loginPane.add(loginButton, 0, 3, 2, 1);

    Separator horizontalSeparator = new Separator();
    loginPane.add(horizontalSeparator, 0, 4, 2, 1);
    grid.add(loginPane, 0, 1);
  }

  protected void makeIcons(GridPane loginPane) {
    ImageView emailIcon = new ImageView(new Image("transit/pages/assets/email.png"));
    emailIcon.setId("emailIcon");
    loginPane.add(emailIcon, 0, 1);

    ImageView lockIcon = new ImageView(new Image("transit/pages/assets/lock.png"));
    lockIcon.setId("lockIcon");
    loginPane.add(lockIcon, 0, 2);
  }

  protected void makeLabels(GridPane loginPane) {
    Label login = new Label("Login");
    login.setId("loginLabel");
    loginPane.add(login, 0, 0, 2, 1);

    Label noAccount = new Label("No account?");
    noAccount.setId("noAccount");
    loginPane.add(noAccount, 0, 5, 2, 1);
  }

  protected PasswordField makePassInput() {
    PasswordField passInput = new PasswordField();
    passInput.setId("passInput");
    passInput.setPromptText("Password");
    return passInput;
  }

  protected TextField makeEmailInput() {
    TextField emailInput = new TextField();
    emailInput.setId("emailInput");
    emailInput.setPromptText("Email");
    return emailInput;
  }

  protected Button makeSignUpButton(Stage primaryStage) {
    Button signupButton = new Button("Signup");
    signupButton.setId("signupButton");
    signupButton.setOnAction(
        (data) -> {
          SignUpPage signupPage = new SignUpPage(primaryStage);
          primaryStage.setScene(signupPage.getScene());
        });
    return signupButton;
  }

  protected Button makeLoginButton(TextField emailInput, TextField passInput, Stage primaryStage) {
    Button loginButton = new Button("Login");
    loginButton.setId("loginButton");
    loginButton.setOnAction(
        (data) -> {
          try {
            User user;
            if (User.getAllUsersCopy().containsKey(emailInput.getText())) {
              user = User.getAllUsersCopy().get(emailInput.getText());
            } else {
              throw new Exception();
            }
            if (checkAuthorization(emailInput, passInput)) {
              Page userPage;
              if (user instanceof AdminUser) {
                userPage = new AdminUserPage(primaryStage, (AdminUser) user);
              } else {
                userPage = new UserPage(primaryStage, user);
              }
              primaryStage.setScene(userPage.getScene());
            } else {
              throw new Exception();
            }
          } catch (Exception ignored) {
            makeAlert(
                "Invalid credentials",
                "Invalid credentials:",
                "The given credentials don't match a user in the transit system",
                AlertType.ERROR).showAndWait();
          }
        });
    return loginButton;
  }

  /**
   * Checks the input values provided by the user
   *
   * @param email The entry into the email box
   * @param password The entry into the password box
   * @return true if the given email and password correspond to a specific user, false otherwise
   */
  private boolean checkAuthorization(TextField email, TextField password) {
    return User.getAuthLogCopy().get(email.getText()) != null
        && User.getAuthLogCopy().get(email.getText()).equals(password.getText());
  }
}
