import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginPage extends AuthenticationPage implements Page {
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
    GridPane grid = new GridPane();

    placeLabel(grid, "Email: ", 1);
    placeLabel(grid, "Password: ", 2);

    TextField email = placeTextField(grid, 1);
    PasswordField password = placePasswordField(grid, 2);

    placeButton(
        "Log In",
        () -> {
          if (checkAuthorization(email, password)) {
            try {
              User user = parser.findUser(email.getText());
              UserPage userPage = new UserPage(primaryStage, user);
              primaryStage.setScene(userPage.getScene());
            } catch (TransitException a) {}
          } else {
            System.out.println("This login credential is not valid!");
          }
        },
        grid,
        1);

    placeButton(
        "Sign Up",
        () -> {
          SignUpPage signupPage = new SignUpPage(primaryStage, this);
          primaryStage.setScene(signupPage.getScene());
        },
        grid,
        2);

    return new Scene(grid, 300, 250);
  }

  private boolean checkAuthorization(TextField email, TextField password) {
    return User.getAuthLogCopy().get(email.getText()) != null
        && User.getAuthLogCopy().get(email.getText()).equals(password.getText());
  }
}
