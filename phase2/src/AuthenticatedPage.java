import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public abstract class AuthenticatedPage extends Page {
  protected User user;
  protected AuthenticatedPage parentPage;
  protected LoginPage loginPage;

  public AuthenticatedPage(
      Stage primaryStage,
      UserParser userParser,
      CardParser cardParser,
      User user,
      AuthenticatedPage parentPage,
      LoginPage loginPage) {
    super(primaryStage, userParser, cardParser);
    this.user = user;
    this.parentPage = parentPage;
    this.loginPage = loginPage;
    addUserData(primaryStage);
  }

  protected abstract void addUserData(Stage primaryStage);

  abstract void updatePage(Stage primaryStage);

  protected void updateLabel(Label label, String newMessage) {
    label.setText(newMessage);
  }
  protected Scene makeScene(Stage primaryStage) {
    newUserInfoButton(10, 10);

    placeButton("Logout", () -> primaryStage.setScene(loginPage.getScene()), 10, 0);

    placeButton(
            "Change username",
            () -> {
              ChangeNamePage namePage =
                      new ChangeNamePage(
                              primaryStage, this.userParser, this.cardParser, this.user, this, this.loginPage);
              primaryStage.setScene(namePage.getScene());
            },
            0,
            4);
    placeButton(
            "Remove this account!",
            () -> {
              Alert alert =
                      makeConfirmationAlert(
                              "Delete account confirmation",
                              "Confirm:",
                              "Are you sure you want this account to be removed?",
                              () -> {
                                userParser.remove(user);
                                primaryStage.setScene(this.loginPage.getScene());
                              });
            },
            0,
            6);
    return new Scene(grid, 300, 250);
  }

  protected void newUserInfoButton(int col, int row) {
    placeButton(
        "Info",
        () -> {
          Alert alert =
              makeAlert(
                  "User Information",
                  "Your user information:",
                  this.userParser.report(user),
                  Alert.AlertType.INFORMATION);
          alert.showAndWait();
        },
        col,
        row);
  }
}
