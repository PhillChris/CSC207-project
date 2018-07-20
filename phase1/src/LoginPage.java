import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginPage extends AuthenticationPage {
  private Scene scene;

  public LoginPage(Stage primaryStage) {
    this.scene = makeScene(primaryStage);
  }

  public Scene getScene() {
    return this.scene;
  }

  private Scene makeScene(Stage primaryStage) {
    GridPane grid = new GridPane();

    placeLabel(grid, "Username: ",1);
    placeLabel(grid, "Password: ", 2);

    TextField username = placeTextField(grid, 1);
    PasswordField password =placePasswordField(grid, 2);

    placeButton("Log In",
        () -> {
          if (User.getAuthLogCopy().get(username.getText()) != null) {
            System.out.println("This is a valid user!");
          } else {
            System.out.println("This is not a valid user!");
          }

        },
        grid, 1);

    placeButton("Sign Up",
        () -> {
          SignUpPage signupPage = new SignUpPage(primaryStage, this);
          primaryStage.setScene(signupPage.getScene());
        },
        grid, 2);

    return new Scene(grid, 300, 250);
  }
}
