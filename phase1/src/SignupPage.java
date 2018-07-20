import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class SignupPage extends AuthenticationPages {
  public Scene getScene(Stage primaryStage) {
    GridPane grid = new GridPane();

    placeLabel(grid, "New User: ", 1);
    placeLabel(grid, "New Password: ", 2);

    placeAuthenticationFields(grid);



    return new Scene(grid, 300, 250);
  }
}
