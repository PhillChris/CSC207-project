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
  private GridPane loginPane;
  private TextField emailInput;
  private TextField passInput;
  private Label errorMessage;

  /**
   * Initialized a new instance of LoginPace
   *
   * @param stage The stage for this page to be displayed
   */
  public LoginPage(Stage stage) {
    super(stage);
    this.stage.setTitle("Login Page");
    makeScene();
    this.stage.setScene(scene);
  }

  /** Sets the scene for this page */
  @Override
  protected void makeScene() {
    grid.setPadding(new Insets(20, 20, 20, 40));
    grid.setHgap(10);
    grid.setVgap(10);

    makeLoginPane();

    factory.addTrain(grid);
    factory.addClock(grid);

    scene = new Scene(grid, 600, 375);
    scene.getStylesheets().add(getClass().getResource("styling/LoginPage.css").toExternalForm());
  }

  /** Makes the login GridPane for styling purposes */
  private void makeLoginPane() {
    this.loginPane = new GridPane();
    loginPane.setHgap(10);
    loginPane.setVgap(12);
    makeLabels();
    makeIcons();

    this.emailInput = makeEmailInput();
    this.passInput = makePassInput();

    this.errorMessage = makeErrorMessage();

    makeSignUpButton();
    makeLoginButton();

    makeSeparator();

    grid.add(loginPane, 0, 1);
  }

  /** Makes all icons in the login pane */
  private void makeIcons() {
    factory.makeImage(loginPane, "transit/pages/assets/email.png", 0, 1);
    factory.makeImage(loginPane, "transit/pages/assets/key.png", 0, 2);
  }

  /** Makes all labels in the login pane */
  private void makeLabels() {
    Label login = factory.makeLabel(loginPane, "Login", 0, 0);
    login.setId("loginLabel");
    GridPane.setColumnSpan(login, 2);
    Label noAccount = factory.makeLabel(loginPane, "No account?", 0, 5);
    noAccount.setId("noAccount");
    GridPane.setColumnSpan(noAccount, 2);
    ;
  }

  /** @return the password input field for this LoginPage */
  private PasswordField makePassInput() {
    return factory.makePasswordField(loginPane, "Password", 1, 2);
  }

  /** @return the email input field for this LoginPage */
  private TextField makeEmailInput() {
    return factory.makeTextField(loginPane, "Email", 1, 1);
  }

  /** @return a label containing the appropriate error message */
  private Label makeErrorMessage() {
    Label errorMessage = factory.makeLabel(loginPane, "", 1, 3);
    errorMessage.setId("errorMessage");
    GridPane.setHalignment(errorMessage, HPos.RIGHT);
    return errorMessage;
  }

  private void makeSignUpButton() {
    Button signUpButton = new Button("Sign Up");
    signUpButton.setId("signUpButton");
    signUpButton.setOnAction((data) -> pageCreator.makeSignUpPage());
    loginPane.add(signUpButton, 0, 6, 2, 1);
  }

  /** Makes the login button for this stage */
  private void makeLoginButton() {
    Button loginButton = new Button("Login");
    loginButton.setId("loginButton");
    loginButton.setOnAction((data) -> parseLoginAttempt());
    loginPane.add(loginButton, 0, 3, 2, 1);
    GridPane.setMargin(loginButton, new Insets(3, 0, 0, 0));
  }

  /** Places a horizontal separator on this page */
  private void makeSeparator() {
    Separator horizontalSeparator = factory.makeSeparator(loginPane, 0, 4);
    GridPane.setColumnSpan(horizontalSeparator, 2);
  }

  /**
   * Checks the input values provided by the user
   *
   * @return true if the given email and password correspond to a specific user, false otherwise
   */
  private boolean checkAuthorization() {
    return User.getAllUsersCopy().containsKey(emailInput.getText())
        && User.getAllUsersCopy()
            .get(emailInput.getText())
            .getPersonalInfo()
            .correctAuthentication(passInput.getText());
  }

  /** Parses a login attempt */
  private void parseLoginAttempt() {
    {
      try {
        User user;
        if (User.getAllUsersCopy().containsKey(emailInput.getText())) {
          user = User.getAllUsersCopy().get(emailInput.getText());
        } else {
          errorMessage.setText("User email not found.");
          LogWriter.getLogWriter().logUserNotFound();
          throw new Exception();
        }
        if (checkAuthorization()) {
          if (user.getCardCommands().getPermission().equals("admin")) {
            pageCreator.makeAdminUserPage(user);
          } else {
            pageCreator.makeUserPage(user);
          }
          LogWriter.getLogWriter().logUserLogin(user);
        } else {
          errorMessage.setText("Incorrect password.");
          LogWriter.getLogWriter().logInvalidAuth();
          throw new Exception();
        }
      } catch (Exception ignored) {
      }
    }
  }
}
