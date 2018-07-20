import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SignUpPage extends AuthenticationPage {
  private Scene scene;
  private LoginPage parentPage;

  public SignUpPage(Stage primaryStage, LoginPage parentPage) {
    this.scene = makeScene(primaryStage);
    this.parentPage = parentPage;
  }

  public Scene getScene() {
    return this.scene;
  }

  private Scene makeScene(Stage primaryStage) {
    GridPane grid = new GridPane();

    placeLabel(grid, "New User: ", 1);
    placeLabel(grid, "New Email: ", 2);
    placeLabel(grid, "New Password: ", 3);

    TextField username = placeTextField(grid, 1);
    TextField email = placeTextField(grid, 2);
    PasswordField password = placePasswordField(grid, 3);

    placeButton("Make Account!", () -> {
      try {
        User newUser = new User(username.getText(), email.getText(), password.getText());
        primaryStage.setScene(parentPage.getScene());
      } catch (TransitException a) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Email in use!");
        alert.setHeaderText("Email in use:");
        alert.setContentText("The email you provided is currently in use by another user.");
        alert.showAndWait();
      }
    }, grid, 3);

    return new Scene(grid, 300, 250);
  }
}
