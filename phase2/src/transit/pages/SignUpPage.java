package transit.pages;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import transit.system.MessageTransitException;
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
   * Makes a scene for this SignUpPage
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  protected void makeScene(Stage primaryStage) {
    grid.setPadding(new Insets(20, 20, 20, 40));
    grid.setHgap(10);
    grid.setVgap(10);

    makeSignUpPane(primaryStage);

    addTrain();
    addClock();

    scene = new Scene(grid, 600, 375);
    scene.getStylesheets().add(getClass().getResource("styling/SignUpPage.css").toExternalForm());
  }

  /**
   * Makes the pane for this page
   *
   * @param primaryStage The stage for this page to be viewed
   */
  private void makeSignUpPane(Stage primaryStage) {
    GridPane signUpPane = new GridPane();
    signUpPane.setPadding(new Insets(0, 0, 0, 0));
    signUpPane.setHgap(10);
    signUpPane.setVgap(12);

    placeLabels(signUpPane);
    makeIcons(signUpPane);

    TextField userInput = makeUserInput(signUpPane);
    TextField emailInput = makeEmailInput(signUpPane);
    PasswordField passInput = makePassInput(signUpPane);
    ComboBox<String> userType = makeTypeCombo(signUpPane);

    Label errorMessage = makeErrorMessage(signUpPane);

    makeSeparator(signUpPane);

    makeSignUpButton(signUpPane, userInput, emailInput, passInput, userType, errorMessage);
    makeBackButton(primaryStage, signUpPane);

    grid.add(signUpPane, 0, 1);
  }

  private TextField makeUserInput(GridPane signUpPane) {
    return makeTextField(signUpPane, "Username", 1, 1);
  }

  private TextField makeEmailInput(GridPane signUpPane) {
    return makeTextField(signUpPane, "Email", 1, 2);
  }

  private PasswordField makePassInput(GridPane signUpPane) {
    return makePasswordField(signUpPane, "Password (6+ chars)", 1, 3);
  }

  private ComboBox<String> makeTypeCombo(GridPane signUpPane) {
    String[] choices = {"Standard", "Admin", "Student"};
    ComboBox<String> userType = makeComboBox(signUpPane, choices, 1, 4);
    GridPane.setColumnSpan(userType, 2);
    GridPane.setHalignment(userType, HPos.RIGHT);
    GridPane.setMargin(userType, new Insets(0, 9, 0, 0));
    return userType;
  }
  private void placeLabels(GridPane signUpPane) {
    Label signUp = new Label("Sign up");
    signUp.setId("signUpLabel");
    signUpPane.add(signUp, 0, 0, 2, 1);

    Label userType = new Label("User type:");
    userType.setId("typeLabel");
    signUpPane.add(userType, 0, 4, 2, 1);
  }

  private Label makeErrorMessage(GridPane signUpPane) {
    Label errorMessage = makeLabel(signUpPane, "", 1, 0);
    errorMessage.setId("errorMessage");
    GridPane.setHalignment(errorMessage, HPos.RIGHT);
    return errorMessage;
  }

  private void makeSeparator(GridPane signUpPane) {
    Separator horizontalSeparator = makeSeparator(signUpPane, 0, 5);
    GridPane.setColumnSpan(horizontalSeparator, 2);
  }

  private void makeIcons(GridPane signUpPane) {
    makeImage(signUpPane, "transit/pages/assets/face.png", 0, 1);
    makeImage(signUpPane, "transit/pages/assets/email.png", 0, 2);
    makeImage(signUpPane, "transit/pages/assets/key.png", 0, 3);
  }

  private void makeSignUpButton(
      GridPane signUpPane,
      TextField userInput,
      TextField emailInput,
      PasswordField passInput,
      ComboBox<String> userType,
      Label errorMessage) {
    Button signUpButton = new Button("Sign Up");
    signUpButton.setOnAction(
        (data) -> {
          try {
            add(
                userInput.getText(),
                emailInput.getText(),
                passInput.getText(),
                userType.getValue());
            errorMessage.setText("Account created");
            errorMessage.setTextFill(Color.web("#33AF54"));
          } catch (MessageTransitException e) {
            e.setMessage(errorMessage);
          }
        });
    signUpPane.add(signUpButton, 0, 6, 2, 1);
    GridPane.setMargin(signUpButton, new Insets(2, 0, 0, 0));
    GridPane.setHalignment(signUpButton, HPos.LEFT);
  }

  private void makeBackButton(Stage primaryStage, GridPane signUpPane) {
    Button backButton = new Button("Go Back");
    backButton.setOnAction((data) -> primaryStage.setScene(new LoginPage(primaryStage).getScene()));
    signUpPane.add(backButton, 1, 6, 2, 1);
    GridPane.setHalignment(backButton, HPos.RIGHT);
    GridPane.setMargin(backButton, new Insets(2, 10, 0, 0));
  }

  /**
   * @param username the proposed username of this new user
   * @param email the proposed email of this new user
   * @param password the proposed password of this new user
   * @throws MessageTransitException if there is a problem in constructing a user with these given
   *     parameters
   */
  private void add(String username, String email, String password, String userType)
      throws MessageTransitException {
    switch (userType) {
      case "Admin":
        new User(username, email, password, "admin");
        break;
      case "Student":
        new User(username, email, password, "student");
        break;
      default:
        new User(username, email, password, "user");
        break;
    }
  }
}
