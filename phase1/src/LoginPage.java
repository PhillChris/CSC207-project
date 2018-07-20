import javafx.scene.Scene;
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

    placeAuthenticationFields(grid);

    placeButton("Log In",
        () -> System.out.println("You've logged in!"),
        grid, 1);

    placeButton("Sign Up",
        () -> {
          SignupPage signupPage = new SignupPage(primaryStage, this);
          primaryStage.setScene(signupPage.getScene());
        },
        grid, 2);

    return new Scene(grid, 300, 250);
  }
}
