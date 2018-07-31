package transit.pages;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    super(primaryStage);
    makeScene(primaryStage);
  }

  /**
   * Sets the scene for this page
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  protected void makeScene(Stage primaryStage) {
    grid.setPadding(new Insets(20, 20, 20, 40));
    grid.setHgap(10);
    grid.setVgap(12);

    makeLoginPane(primaryStage);
    ImageView train = new ImageView(new Image("transit/pages/assets/train.png"));
    grid.add(train, 2, 0, 1, 8);

    addClock();
    this.scene = new Scene(grid, 600, 375);

    scene
        .getStylesheets()
        .add(LoginPage.class.getResource("styling/LoginPage.css").toExternalForm());
  }

  /**
   * Makes the login GridPane for styling purposes
   *
   * @param primaryStage the stage on which this LoginPage is being represented
   */
  private void makeLoginPane(Stage primaryStage) {
    makeLabels();
    makeIcons();

    TextField emailInput = makeEmailInput();
    PasswordField passInput = makePassInput();

    Label errorMessage = makeErrorMessage();

    makeSignUpButton(primaryStage);
    makeLoginButton(emailInput, passInput, errorMessage, primaryStage);

    Separator horizontalSeparator = new Separator();
    grid.add(horizontalSeparator, 0, 5, 2, 1);
  }

  /**
   * Makes all icons in the scene
   *
   */
  private void makeIcons() {
    ImageView emailIcon = new ImageView(new Image("transit/pages/assets/email.png"));
    grid.add(emailIcon, 0, 2);

    ImageView keyIcon = new ImageView(new Image("transit/pages/assets/key.png"));
    grid.add(keyIcon, 0, 3);
  }

  /**
   * Makes all labels in the scene
   *
   */
  private void makeLabels() {
    Label login = new Label("Login");
    login.setId("loginLabel");
    grid.add(login, 0, 1, 2, 1);

    Label noAccount = new Label("No account?");
    noAccount.setId("noAccount");
    grid.add(noAccount, 0, 6, 2, 1);
  }

  private Label makeErrorMessage() {
    Label errorMessage = new Label("");
    errorMessage.setId("errorMessage");
    grid.add(errorMessage, 1, 4);
    GridPane.setHalignment(errorMessage, HPos.RIGHT);
    return errorMessage;
  }

  private PasswordField makePassInput() {
    PasswordField passInput = new PasswordField();
    passInput.setId("passInput");
    passInput.setPromptText("Password");
    grid.add(passInput, 1, 3);
    return passInput;
  }

  /** @return the email input field for this LoginPage */
  private TextField makeEmailInput() {
    TextField emailInput = new TextField();
    emailInput.setId("emailInput");
    emailInput.setPromptText("Email");
    grid.add(emailInput, 1, 2);
    return emailInput;
  }

  /** @return the SignUp button for this LoginPage */
  private void makeSignUpButton(Stage primaryStage) {
    Button signupButton = new Button("Signup");
    signupButton.setId("signupButton");
    signupButton.setOnAction(
        (data) -> {
          SignUpPage signupPage = new SignUpPage(primaryStage);
          primaryStage.setScene(signupPage.getScene());
        });
    grid.add(signupButton, 0, 7, 2, 1);
  }

  /**
   * @param emailInput the email input field in this LoginPage
   * @param passInput the password input field in this LoginPage
   * @param errorMessage the error message field on this LoginPage to be modified as needed
   * @param primaryStage the stage on which this LoginPage is being served
   * @return the login button on this LoginPage
   */
  private void makeLoginButton(TextField emailInput, TextField passInput, Label errorMessage, Stage primaryStage) {
    Button loginButton = new Button("Login");
    loginButton.setId("loginButton");
    loginButton.setOnAction(
        (data) -> {
          try {
            User user;
            if (User.getAllUsersCopy().containsKey(emailInput.getText())) {
              user = User.getAllUsersCopy().get(emailInput.getText());
            } else {
              errorMessage.setText("User email not found.");
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
              errorMessage.setText("Incorrect password.");
              throw new Exception();
            }
          } catch (Exception ignored) {
          }
        });
    grid.add(loginButton, 0, 4, 2, 1);
  }

  /**
   * Checks the input values provided by the user
   *
   * @param email The entry into the email box
   * @param password The entry into the password box
   * @return true if the given email and password correspond to a specific user, false otherwise
   */
  private boolean checkAuthorization(TextField email, TextField password) {
    return User.getAllUsersCopy().containsKey(email.getText())
        && User.getAllUsersCopy().get(email.getText()).correctAuthentification(password.getText());
  }
}
