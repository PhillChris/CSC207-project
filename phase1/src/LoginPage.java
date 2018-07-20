import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginPage extends Page {
  private Scene scene;
  private UserParser parser;

  public LoginPage(Stage primaryStage) throws IOException {
    this.scene = makeScene(primaryStage);
    this.parser = new UserParser(new BufferedWriter(new FileWriter("output.txt")));
  }

  public Scene getScene() {
    return this.scene;
  }

  private Scene makeScene(Stage primaryStage) {
    GridPane grid = getGrid();

    placeLabel(grid, "Email: ", 0, 1);
    placeLabel(grid, "Password: ", 0, 2);

    TextField email = placeTextField(grid, 1, 1);
    PasswordField password = placePasswordField(grid, 1, 2);

    placeButton(
        "Log In",
        () -> {
          try {
            User user = parser.findUser(email.getText());
            if (checkAuthorization(email, password)) {
              UserPage userPage = new UserPage(primaryStage, this, user, this.parser);
              primaryStage.setScene(userPage.getScene());
            }
            else {
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
          SignUpPage signupPage = new SignUpPage(primaryStage, this, this.parser);
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
