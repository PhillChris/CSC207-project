import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import java.util.Optional;

public class UserPage extends Page {
  private Scene scene;
  private LoginPage parentPage;
  private User user;
  private UserParser parser;

  public UserPage(Stage primaryStage, LoginPage parentPage, User user, UserParser parser) {
    this.scene = makeScene(primaryStage);
    this.parentPage = parentPage;
    this.user = user;
    this.parser = parser;
  }

  public Scene getScene() {
    return this.scene;
  }

  private Scene makeScene(Stage primaryStage) {
    GridPane grid = getGrid();
    placeButton("Cards",
        () -> System.out.println("Here is a list of my cards!"),
        grid,
        0, 0);
    placeButton("Logout",
        () -> primaryStage.setScene(parentPage.getScene()),
        grid,
        0, 1);
    placeButton("Remove this account!",
        () -> {
          Alert alert = makeAlert("Delete account confirmation",
              "Confirm:",
              "Are you sure you want this account to be removed?",
              AlertType.CONFIRMATION);

          ButtonType confirm = new ButtonType("Proceed");
          ButtonType reject = new ButtonType("Cancel");
          alert.getButtonTypes().setAll(confirm, reject);

          Optional<ButtonType> result = alert.showAndWait();

          // If the user chose to destroy the given account
          if (result.get() == confirm) {
            parser.remove(user);
            primaryStage.setScene(parentPage.getScene());
          }
        },
        grid,
        0, 2);
    return new Scene(grid, 300, 250);
  }
}
