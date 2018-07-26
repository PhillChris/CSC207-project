package transit.pages;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import transit.system.*;

public class LoginPage extends Page {

  public LoginPage(Stage primaryStage) {
    makeScene(primaryStage);
  }

  protected void makeScene(Stage primaryStage) {
    //TODO: Clean this all up
    grid.setPadding(new Insets(30, 20, 20, 40));
    grid.setVgap(20);
    GridPane loginPane = new GridPane();
    loginPane.setHgap(10);
    loginPane.setVgap(12);
    Label login = new Label("Login");
    login.setId("loginLabel");
    loginPane.add(login, 0, 0, 2, 1);

    ImageView emailIcon = new ImageView(new Image("transit/system/assets/email.png"));
    emailIcon.setId("emailIcon");
    loginPane.add(emailIcon, 0, 1);

    TextField emailInput = new TextField();
    emailInput.setId("emailInput");
    emailInput.setPromptText("Email");
    loginPane.add(emailInput, 1, 1);

    ImageView lockIcon = new ImageView(new Image("transit/system/assets/lock.png"));
    lockIcon.setId("lockIcon");
    loginPane.add(lockIcon, 0, 2);

    PasswordField passInput = new PasswordField();
    passInput.setId("passInput");
    passInput.setPromptText("Password");
    loginPane.add(passInput, 1, 2);

    Button loginButton = new Button("Login");
    loginButton.setId("loginButton");
    loginButton.setOnAction((data) -> {
          try {
            User user;
            if (User.getAllUsersCopy().containsKey(emailInput.getText())) {
              user = User.getAllUsersCopy().get(emailInput.getText());
            } else {
              throw new UserNotFoundException();
            }
            if (checkAuthorization(emailInput, passInput)) {
              Page userPage;
              if (user instanceof AdminUser) {
                userPage =
                    new AdminUserPage(primaryStage, (AdminUser) user);
              } else {
                userPage = new UserPage(primaryStage, user);
              }
              primaryStage.setScene(userPage.getScene());
            } else {
              throw new InvalidCredentialsException();

            }
          } catch (TransitException a) {
            System.out.println(a.getMessage());
          }
        });
    loginPane.add(loginButton, 0, 3, 2, 1);

    Separator horizontalSeparator = new Separator();
    loginPane.add(horizontalSeparator, 0, 4, 2, 1);

    Label noAccount = new Label("No account?");
    noAccount.setId("noAccount");
    loginPane.add(noAccount, 0, 5, 2, 1);

    Button signupButton = new Button("Signup");
    loginButton.setId("signupButton");
    signupButton.setOnAction((data) -> {SignUpPage signupPage =
              new SignUpPage(primaryStage);
          primaryStage.setScene(signupPage.getScene());
        });
    loginPane.add(signupButton, 0, 6, 2, 1);

    grid.add(loginPane, 0, 1);

    GridPane trainPane = new GridPane();
    trainPane.setPadding(new Insets(0, 0, 0, 10));
    ImageView train = new ImageView(new Image("transit/system/assets/train.png"));
    trainPane.add(train, 0, 0);
    grid.add(trainPane, 1, 1);

    addClock();
    this.scene = new Scene(grid, 625, 400);
    grid.setHgap(10);
    grid.setVgap(10);
    //scene.getStylesheets().add
      //(LoginPage.class.getResource("styling/LoginPage.css").toExternalForm());
  }

  private boolean checkAuthorization(TextField email, TextField password) {
    return User.getAuthLogCopy().get(email.getText()) != null
        && User.getAuthLogCopy().get(email.getText()).equals(password.getText());
  }
}
