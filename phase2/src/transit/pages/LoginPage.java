package transit.pages;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import transit.system.LogWriter;
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
    stage.setTitle("Login");
    makeScene();
    stage.setScene(scene);
  }

  /**
   * Sets the scene for this page
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  protected void makeScene() {
    grid.setPadding(new Insets(20, 20, 20, 40));
    grid.setHgap(10);
    grid.setVgap(10);

    makeLoginPane();

    addTrain();
    addClock();

    scene = new Scene(grid, 600, 375);
    scene.getStylesheets().add(getClass().getResource("styling/LoginPage.css").toExternalForm());
  }

  /**
   * Makes the login GridPane for styling purposes
   *
   * @param primaryStage the stage on which this LoginPage is being represented
   */
  private void makeLoginPane() {
    GridPane loginPane = new GridPane();
    loginPane.setHgap(10);
    loginPane.setVgap(12);
    makeLabels(loginPane);
    makeIcons(loginPane);

    TextField emailInput = makeEmailInput(loginPane);
    PasswordField passInput = makePassInput(loginPane);

    Label errorMessage = makeErrorMessage(loginPane);

    makeSignUpButton(stage, loginPane);
    makeLoginButton(stage, loginPane, emailInput, passInput, errorMessage);

    makeSeparator(loginPane);

    grid.add(loginPane, 0, 1);
  }

  /**
   * Makes all icons in the login pane
   *
   * @param loginPane the login pane being constructed in this LoginPage
   */
  private void makeIcons(GridPane loginPane) {
    makeImage(loginPane, "transit/pages/assets/email.png", 0, 1);
    makeImage(loginPane, "transit/pages/assets/key.png", 0, 2);
  }

  /**
   * Makes all labels in the login pane
   *
   * @param loginPane the login pane being constructed in this LoginPage
   */
  private void makeLabels(GridPane loginPane) {
    Label login = makeLabel(loginPane, "Login", 0, 0);
    login.setId("loginLabel");
    GridPane.setColumnSpan(login, 2);
    Label noAccount = makeLabel(loginPane, "No account?", 0, 5);
    noAccount.setId("noAccount");
    GridPane.setColumnSpan(noAccount, 2);
    ;
  }

  /** @return the password input field for this LoginPage */
  private PasswordField makePassInput(GridPane loginPane) {
    return makePasswordField(loginPane, "Password", 1, 2);
  }

  /** @return the email input field for this LoginPage */
  private TextField makeEmailInput(GridPane loginPane) {
    return makeTextField(loginPane, "Email", 1, 1);
  }

  /**
   * @param loginPane the login pane being constructed in this LoginPage
   * @return a label containing the appropriate error message
   */
  private Label makeErrorMessage(GridPane loginPane) {
    Label errorMessage = makeLabel(loginPane, "", 1, 3);
    errorMessage.setId("errorMessage");
    GridPane.setHalignment(errorMessage, HPos.RIGHT);
    return errorMessage;
  }

  /**
   * @param primaryStage the stage on which this page is being served
   * @param loginPane the the login pane being constructed in this LoginPage
   */
  private void makeSignUpButton(Stage primaryStage, GridPane loginPane) {
    Button signUpButton = new Button("Sign Up");
    signUpButton.setId("signUpButton");
    signUpButton.setOnAction(
        (data) -> {
          new SignUpPage(primaryStage);
        });
    loginPane.add(signUpButton, 0, 6, 2, 1);
  }

  /**
   * @param emailInput the email input field in this LoginPage
   * @param passInput the password input field in this LoginPage
   * @param errorMessage the error message field on this LoginPage to be modified as needed
   * @param primaryStage the stage on which this LoginPage is being served
   */
  private void makeLoginButton(
      Stage primaryStage,
      GridPane loginPane,
      TextField emailInput,
      TextField passInput,
      Label errorMessage) {
    Button loginButton = new Button("Login");
    loginButton.setId("loginButton");
    loginButton.setOnAction(
        (data) -> parseLoginAttempt(primaryStage, emailInput, passInput, errorMessage));
    loginPane.add(loginButton, 0, 3, 2, 1);
    GridPane.setMargin(loginButton, new Insets(3, 0, 0, 0));
  }

  /**
   * Places a horizontal separator on this page
   *
   * @param loginPane the second pane in this page used for styling
   */
  private void makeSeparator(GridPane loginPane) {
    Separator horizontalSeparator = makeSeparator(loginPane, 0, 4);
    GridPane.setColumnSpan(horizontalSeparator, 2);
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
        && User.getAllUsersCopy()
            .get(email.getText())
            .getPersonalInfo()
            .correctAuthentification(password.getText());
  }

  private void parseLoginAttempt(
      Stage primaryStage, TextField emailInput, TextField passInput, Label errorMessage) {
    {
      try {
        User user;
        if (User.getAllUsersCopy().containsKey(emailInput.getText())) {
          user = User.getAllUsersCopy().get(emailInput.getText());
        } else {
          errorMessage.setText("User email not found.");
          LogWriter.getLogWriter()
              .logWarningMessage(
                  LoginPage.class.getName(),
                  "parseLoginAttempt",
                  "Login attempt failed, user is not registered in the system");
          throw new Exception();
        }
        if (checkAuthorization(emailInput, passInput)) {
          Page userPage;
          if (user.getCardCommands().getPermission().equals("admin")) {
            new AdminUserPage(primaryStage, user);
          } else {
            new UserPage(primaryStage, user);
          };
          LogWriter.getLogWriter()
              .logInfoMessage(
                  LoginPage.class.getName(),
                  "parseLoginAttempt",
                  "Successfully logged in as + " + user.getPersonalInfo().getUserName());
        } else {
          errorMessage.setText("Incorrect password.");
          LogWriter.getLogWriter()
              .logWarningMessage(
                  LoginPage.class.getName(),
                  "parseLoginAttempt",
                  "Login attempt failed, incorrect password");
          throw new Exception();
        }
      } catch (Exception ignored) {
      }
    }
  }
}
