import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class SignupPage extends AuthenticationPage {
  private Scene scene;
  private LoginPage parentPage;

  public SignupPage(Stage primaryStage, LoginPage parentPage) {
    this.scene = makeScene(primaryStage);
    this.parentPage = parentPage;
  }

  public Scene getScene() {
    return this.scene;
  }

  private Scene makeScene(Stage primaryStage) {
    GridPane grid = new GridPane();

    placeLabel(grid, "New User: ", 1);
    placeLabel(grid, "New Password: ", 2);

    placeAuthenticationFields(grid);

    placeButton("Make Account!", () -> {
      primaryStage.setScene(parentPage.getScene());
    }, grid, 2);

    return new Scene(grid, 300, 250);
  }
}
