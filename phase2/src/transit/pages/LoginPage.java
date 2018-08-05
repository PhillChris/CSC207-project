package transit.pages;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
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
    grid.setVgap(10);

    makeLoginPane(primaryStage);
    addTrain();

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
    GridPane loginPane = new GridPane();
    loginPane.setHgap(10);
    loginPane.setVgap(12);
    placeLabels(loginPane);
    placeIcons(loginPane);

    TextField emailInput = makeEmailInput(loginPane);
    PasswordField passInput = makePassInput(loginPane);

    Label errorMessage = makeErrorMessage(loginPane);

    placeSignUpButton(primaryStage, loginPane);
    placeLoginButton(primaryStage, loginPane, emailInput, passInput, errorMessage);

    placeSeparator(loginPane);

    grid.add(loginPane, 0, 1);
  }

  /**
   * Makes all icons in the login pane
   *
   * @param loginPane the login pane being constructed in this LoginPage
   */
  private void placeIcons(GridPane loginPane) {
    placeImage(loginPane, "transit/pages/assets/email.png", 0, 1, "emailIcon");
    placeImage(loginPane, "transit/pages/assets/key.png", 0, 2, "keyIcon");
  }

  /**
   * Makes all labels in the login pane
   *
   * @param loginPane the login pane being constructed in this LoginPage
   */
  private void placeLabels(GridPane loginPane) {
    Label login = placeLabel(loginPane, "Login", 0, 0, "loginLabel");
    GridPane.setColumnSpan(login, 2);

    Label noAccount = placeLabel(loginPane, "No account?", 0, 5, "noAccount");
    GridPane.setColumnSpan(noAccount, 2);
    ;
  }

  /** @return the password input field for this LoginPage */
  private PasswordField makePassInput(GridPane loginPane) {
    return placePasswordField(loginPane, "Password", 1, 2, "passInput");
  }

  /** @return the email input field for this LoginPage */
  private TextField makeEmailInput(GridPane loginPane) {
    return placeTextField(loginPane, "Email", 1, 1, "emailInput");
  }

  /**
   * @param loginPane the login pane being constructed in this LoginPage
   * @return a label containing the appropriate error message
   */
  private Label makeErrorMessage(GridPane loginPane) {
    Label errorMessage = placeLabel(loginPane, "", 1, 3, "errorMessage");
    GridPane.setHalignment(errorMessage, HPos.RIGHT);
    return errorMessage;
  }

  /**
   * @param primaryStage the stage on which this page is being served
   * @param loginPane the the login pane being constructed in this LoginPage
   */
  private void placeSignUpButton(Stage primaryStage, GridPane loginPane) {
    Button signUpButton = new Button("Sign Up");
    signUpButton.setId("signUpButton");
    signUpButton.setOnAction(
        (data) -> {
          SignUpPage signupPage = new SignUpPage(primaryStage);
          primaryStage.setScene(signupPage.getScene());
        });
    loginPane.add(signUpButton, 0, 6, 2, 1);
  }

  /**
   * @param emailInput the email input field in this LoginPage
   * @param passInput the password input field in this LoginPage
   * @param errorMessage the error message field on this LoginPage to be modified as needed
   * @param primaryStage the stage on which this LoginPage is being served
   */
  private void placeLoginButton(
      Stage primaryStage,
      GridPane loginPane,
      TextField emailInput,
      TextField passInput,
      Label errorMessage) {
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
              if (user.getPersonalInfo().getPermission().equals("admin")) {
                userPage = new AdminUserPage(primaryStage, user);
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
    loginPane.add(loginButton, 0, 3, 2, 1);
    GridPane.setMargin(loginButton, new Insets(3, 0, 0, 0));
  }

  /**
   * Places a horizontal separator on this page
   *
   * @param loginPane the second pane in this page used for styling
   */
  private void placeSeparator(GridPane loginPane) {
    placeSeparator(loginPane, 0, 4, 2, "horizontalSeparator");
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
}
