package transit.pages;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import transit.system.AdminUser;
import transit.system.User;

import javax.xml.soap.Text;

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
    grid.setPadding(new Insets(20, 20, 20, 40));
    grid.setHgap(10);
    grid.setVgap(10);

    GridPane signupPane = new GridPane();
    signupPane.setPadding(new Insets(0, 0, 0, 0));
    signupPane.setHgap(10);
    signupPane.setVgap(12);

    Label signup = new Label("Sign up");
    signupPane.add(signup, 0, 0, 2, 1);
    signup.setId("signUpLabel");

    ImageView faceIcon = new ImageView(new Image("transit/pages/assets/face.png"));
    signupPane.add(faceIcon, 0, 1);

    ImageView emailIcon = new ImageView(new Image("transit/pages/assets/email.png"));
    signupPane.add(emailIcon, 0, 2);

    ImageView keyIcon = new ImageView(new Image("transit/pages/assets/key.png"));
    signupPane.add(keyIcon, 0, 3);

    TextField userInput = new TextField();
    userInput.setPromptText("Username");
    signupPane.add(userInput, 1, 1);

    TextField emailInput = new TextField();
    emailInput.setPromptText("Email");
    signupPane.add(emailInput, 1, 2);


    PasswordField passInput = new PasswordField();
    passInput.setPromptText("Password");
    signupPane.add(passInput, 1, 3);


    CheckBox adminBox = new CheckBox("Admin?");
    signupPane.add(adminBox, 0, 4, 2, 1);

    Label errorMessage = new Label("");
    errorMessage.setId("errorMessage");
    signupPane.add(errorMessage, 1, 4);
    GridPane.setHalignment(errorMessage, HPos.RIGHT);

    Separator horizontalSeparator = new Separator();
    signupPane.add(horizontalSeparator, 0, 5, 2, 1);

    Button signUpButton = new Button("Sign Up");
    signUpButton.setOnAction((data) -> {
      try {
        add(userInput.getText(), emailInput.getText(), passInput.getText(), adminBox.isSelected());
        errorMessage.setText("Account created");
        errorMessage.setTextFill(Color.web("#33AF54"));
      } catch (Exception ignored) {
        errorMessage.setTextFill(Color.web("red"));
        errorMessage.setText("Email invalid or in use");
      }
    });
    signupPane.add(signUpButton, 0, 6, 2, 1);
    GridPane.setHalignment(signUpButton, HPos.LEFT);
    GridPane.setMargin(signUpButton, new Insets(0, 0, 0, 0));



    Button backButton = new Button("Go Back");
    backButton.setOnAction((data) -> primaryStage.setScene(new LoginPage(primaryStage).getScene()));
    signupPane.add(backButton, 1, 6, 2, 1);
    GridPane.setHalignment(backButton, HPos.RIGHT);
    GridPane.setMargin(backButton, new Insets(0, 0, 0, 0));

    grid.add(signupPane, 0, 1);

    GridPane trainPane = new GridPane();
    ImageView train = new ImageView(new Image("transit/pages/assets/train.png"));
    trainPane.setPadding(new Insets(30, 0, 0, 10));
    trainPane.add(train, 0, 0);
    grid.add(trainPane, 1, 0, 1, 2);

    addClock();
    this.scene = new Scene(grid, 600, 375);
    scene.getStylesheets().add(SignUpPage.class.getResource("styling/SignUpPage.css").toExternalForm());
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
   * @param username the username field of the user which is being created
   * @param email the email field of the user which is being created
   * @param password the password field of the user which is being created
   * @param adminBox the checkbox determining whether or not the user being created is an admin
   */
  private void makeButtons(
      Stage primaryStage, TextField username, TextField email, PasswordField password,
      CheckBox adminBox, Label errorMessage) {
    placeButton(
        "Go back", () -> primaryStage.setScene(new LoginPage(primaryStage).getScene()), 1, 5);

    placeButton(
        "Make Account!",
        () -> {
          try {
            add(username.getText(), email.getText(), password.getText(), adminBox.isSelected());
            primaryStage.setScene(new LoginPage(primaryStage).getScene());
          } catch (Exception a) {
//            Alert alert =
//                makeAlert(
//                    "Email in use!",
//                    "Email in use:",
//                    "The email you provided is not valid or"
//                        + " is currently in use by another user.",
//                    AlertType.WARNING);
//            alert.showAndWait();

          }
        },
        1,
        6);
  }
}
