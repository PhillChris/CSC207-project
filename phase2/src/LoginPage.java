import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginPage extends Page {
  public LoginPage(Stage primaryStage, BufferedWriter writer) throws IOException {
    super(primaryStage,
        new UserParser(writer), new CardParser(writer));
  }

  protected Scene makeScene(Stage primaryStage) {
    GridPane grid = getGrid();

    placeLabel(grid, "Email: ", 0, 1);
    placeLabel(grid, "Password: ", 0, 2);

    TextField email = placeTextField(grid, 1, 1);
    PasswordField password = placePasswordField(grid, 1, 2);

    placeButton(
        "Log In",
        () -> {
          try {
            User user = userParser.findUser(email.getText());
            if (checkAuthorization(email, password)) {
              UserPage userPage = new UserPage(primaryStage, this, user, this.userParser, this.cardParser);
              primaryStage.setScene(userPage.getScene());
            } else {
              throw new InvalidCredentialsException();
            }
          } catch (TransitException a) {
            System.out.println(a.getMessage());
          }
        },
        grid,
        2,
        1);

    placeButton(
        "Sign Up",
        () -> {
          SignUpPage signupPage = new SignUpPage(primaryStage, this, this.userParser, this.cardParser);
          primaryStage.setScene(signupPage.getScene());
        },
        grid,
        2,
        2);

    return new Scene(grid, 300, 250);
  }

  private boolean checkAuthorization(TextField email, TextField password) {
    return User.getAuthLogCopy().get(email.getText()) != null
        && User.getAuthLogCopy().get(email.getText()).equals(password.getText());
  }
}
