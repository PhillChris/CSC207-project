import java.util.Optional;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UserPage extends Page {
  private LoginPage parentPage;
  private User user;

  public UserPage(
      Stage primaryStage,
      LoginPage parentPage,
      User user,
      UserParser userParser,
      CardParser cardParser) {
    super(primaryStage, userParser, cardParser);
    this.parentPage = parentPage;
    this.user = user;
  }

  protected Scene makeScene(Stage primaryStage) {
    GridPane grid = getGrid();
    placeButton("Cards", () -> System.out.println("Here is a list of my cards!"), grid, 0, 1);

    placeButton(
        "Change username",
        () -> {
          ChangeNamePage namePage =
              new ChangeNamePage(primaryStage, this.userParser, this.cardParser, this.user, this);
          primaryStage.setScene(namePage.getScene());
        },
        grid,
        0, 2);

    placeButton("Logout", () -> primaryStage.setScene(parentPage.getScene()), grid, 0, 3);
    placeButton(
        "Remove this account!",
        () -> {
          Alert alert =
              makeConfirmationAlert(
                  "Delete account confirmation",
                  "Confirm:",
                  "Are you sure you want this account to be removed?",
                  AlertType.CONFIRMATION,
                  () -> {
                    userParser.remove(user);
                    primaryStage.setScene(parentPage.getScene());
                  });
        },
        grid,
        0, 4);
    return new Scene(grid, 300, 250);
  }
}
