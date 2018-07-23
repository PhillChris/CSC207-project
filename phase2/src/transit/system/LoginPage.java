package transit.system;

import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.IOException;

public class LoginPage extends Page {
  public LoginPage(Stage primaryStage, BufferedWriter writer) throws IOException {
    super(primaryStage);
    this.scene = makeScene(primaryStage);
  }

  protected Scene makeScene(Stage primaryStage) {
    placeLabel("Email: ", 0, 1);
    placeLabel("Password: ", 0, 2);

    TextField email = placeTextField(1, 1);
    PasswordField password = placePasswordField(1, 2);

    placeButton(
        "Log In",
        () -> {
          try {
            User user;
            if (User.getAllUsersCopy().containsKey(email.getText())) {
              user = User.getAllUsersCopy().get(email.getText());
            } else {
              throw new UserNotFoundException();
            }
            if (checkAuthorization(email, password)) {
              AuthenticatedPage userPage;
              // need to check if we enter admin or normal user
              // might want to come up with a better way of doing this
              if (user instanceof AdminUser) {
                userPage =
                    new AdminUserPage(primaryStage, user, this);
              } else {
                userPage = new UserPage(primaryStage, user, this);
              }
              primaryStage.setScene(userPage.getScene());
            } else {
              throw new InvalidCredentialsException();
            }
          } catch (TransitException a) {
            System.out.println(a.getMessage());
          }
        },
        2,
        1);

    placeButton(
        "Sign Up",
        () -> {
          SignUpPage signupPage =
              new SignUpPage(primaryStage, this);
          primaryStage.setScene(signupPage.getScene());
        },
        2,
        2);

    return new Scene(grid, 500, 250);
  }

  private boolean checkAuthorization(TextField email, TextField password) {
    return User.getAuthLogCopy().get(email.getText()) != null
        && User.getAuthLogCopy().get(email.getText()).equals(password.getText());
  }
}
