import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginPage extends AuthenticationPages {
  public Scene getScene(Stage primaryStage) {
    GridPane grid = new GridPane();

    placeLabel(grid, "Username: ",1);
    placeLabel(grid, "Password: ", 2);

    placeAuthenticationFields(grid);

    placeButton("Log In",
        () -> System.out.println("You've logged in!"),
        grid, 1);

    placeButton("Sign Up",
        () -> {
          SignupPage signupPage = new SignupPage();
          primaryStage.setScene(signupPage.getScene(primaryStage));
        },
        grid, 2);

    return new Scene(grid, 300, 250);
  }
}
