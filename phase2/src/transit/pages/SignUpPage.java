package transit.pages;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import transit.system.LogWriter;
import transit.system.MessageTransitException;
import transit.system.User;

/** Represents a page opened when making a new account in this transit system */
public class SignUpPage extends Page {
  private GridPane signUpPane;
  private TextField userInput;
  private TextField emailInput;
  private PasswordField passInput;
  private ComboBox<String> userType;
  private Label errorMessage;

  /**
   * Constructs a new SignUpPage
   *
   * @param stage the stage on which this SignUpPage is being served
   */
  public SignUpPage(Stage stage) {
    super(stage);
    this.stage.setTitle("Sign Up Page");
    makeScene();
    this.stage.setScene(scene);
  }

  /** Makes a scene for this SignUpPage */
  @Override
  protected void makeScene() {
    // Set margins and borders
    grid.setPadding(new Insets(20, 20, 20, 40));
    grid.setHgap(10);
    grid.setVgap(10);

    makeSignUpPane();

    // Add the train pictures and the clock to the given page
    factory.addTrain(grid);
    factory.addClock(grid);

    // Sets the given scene and assigns CSS to it
    scene = new Scene(grid, 600, 375);
    scene.getStylesheets().add(getClass().getResource("styling/SignUpPage.css").toExternalForm());
  }

  /** Makes the pane for this page */
  private void makeSignUpPane() {
    // Configures the signUp grid
    this.signUpPane = new GridPane();
    signUpPane.setPadding(new Insets(0, 0, 0, 0));
    signUpPane.setHgap(10);
    signUpPane.setVgap(12);

    // Places the labels and icons on the sign up page
    placeLabels();
    makeIcons();

    // Initializes the attributes of this page
    this.userInput = factory.makeTextField(signUpPane, "Username", 1, 1);
    ;
    this.emailInput = factory.makeTextField(signUpPane, "Email", 1, 2);
    ;
    this.passInput = factory.makePasswordField(signUpPane, "Password (6+ chars)", 1, 3);
    ;
    this.userType = makeTypeCombo();
    this.errorMessage = makeErrorMessage();

    // Makes the buttons on the page
    makeSeparator();
    makeSignUpButton();
    makeBackButton();

    // Add the signup pane to the larger grid
    grid.add(signUpPane, 0, 1);
  }

  /**
   * Makes the type combo box for the
   *
   * @return the type combo box
   */
  private ComboBox<String> makeTypeCombo() {
    String[] choices = {"Standard", "Admin", "Student"}; // possible account types
    ComboBox<String> userType = factory.makeComboBox(signUpPane, choices, 1, 4);

    // sets the dimensions of the combo box
    GridPane.setColumnSpan(userType, 2);
    GridPane.setHalignment(userType, HPos.RIGHT);
    GridPane.setMargin(userType, new Insets(0, 9, 0, 0));

    return userType;
  }

  /**
   * Make and place the sign up labels
   */
  private void placeLabels() {
    // Make and place the signup label
    Label signUp = new Label("Sign up");
    signUp.setId("signUpLabel");
    signUpPane.add(signUp, 0, 0, 2, 1);

    // Make and place the user type label
    Label userType = new Label("User type:");
    userType.setId("typeLabel");
    signUpPane.add(userType, 0, 4, 2, 1);
  }

  /**
   * Makes the error message label
   *
   * @return the error message label
   */
  private Label makeErrorMessage() {
    Label errorMessage = factory.makeLabel(signUpPane, "", 1, 0);
    errorMessage.setId("errorMessage");
    GridPane.setHalignment(errorMessage, HPos.RIGHT);
    return errorMessage;
  }

  /** Make a vertical line separator */
  private void makeSeparator() {
    Separator horizontalSeparator = factory.makeSeparator(signUpPane, 0, 5);
    GridPane.setColumnSpan(horizontalSeparator, 2);
  }

  /** Make icons next to text fields */
  private void makeIcons() {
    factory.makeImage(signUpPane, "transit/pages/assets/face.png", 0, 1);
    factory.makeImage(signUpPane, "transit/pages/assets/email.png", 0, 2);
    factory.makeImage(signUpPane, "transit/pages/assets/key.png", 0, 3);
  }

  /** Make the button creating a new account */
  private void makeSignUpButton() {
    // Make and configure the signup button
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
            LogWriter.getLogWriter().logCorrectSignup();
          } catch (MessageTransitException e) {
            e.setMessage(errorMessage);
            LogWriter.getLogWriter().logIncorrectSignup(e);
          }
        });
    signUpPane.add(signUpButton, 0, 6, 2, 1);

    // Set margins and alignment of button
    GridPane.setMargin(signUpButton, new Insets(2, 0, 0, 0));
    GridPane.setHalignment(signUpButton, HPos.LEFT);
  }

  private void makeBackButton() {
    // Configure back button
    Button backButton = new Button("Go Back");
    backButton.setOnAction((data) -> pageCreator.makeLoginPage());
    signUpPane.add(backButton, 1, 6, 2, 1);

    // Set alignment
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
