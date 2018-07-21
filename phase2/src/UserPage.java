import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class UserPage extends AuthenticatedPage {
  public UserPage(
      Stage primaryStage,
      UserParser userParser,
      CardParser cardParser,
      User user,
      LoginPage parentPage) {
    super(primaryStage, userParser, cardParser, user, parentPage);
  }

  protected Scene makeScene(Stage primaryStage) {
    placeButton(
        "Cards",
        () -> {
          CardPage cardPage =
              new CardPage(primaryStage, this.userParser, this.cardParser, this.user, this);
          primaryStage.setScene(cardPage.getScene());
        },
        0,
        1);
    placeButton(
        "Info",
        () -> {
          Alert alert =
              makeAlert(
                  "User Information",
                  "Your user information:",
                  this.userParser.report(user),
                  AlertType.INFORMATION);
          alert.showAndWait();
        },
        0,
        2);
    placeButton(
        "Change username",
        () -> {
          ChangeNamePage namePage =
              new ChangeNamePage(primaryStage, this.userParser, this.cardParser, this.user, this);
          primaryStage.setScene(namePage.getScene());
        },
        0,
        3);

    placeButton("Logout", () -> primaryStage.setScene(parentPage.getScene()), 0, 4);
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
        0,
        5);
    return new Scene(grid, 300, 250);
  }

  protected Scene makeUserScene(Stage primaryStage) {
    return this.scene;
  }
}
