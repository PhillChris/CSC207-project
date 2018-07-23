package transit.system;

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
      User user,
      AuthenticatedPage parentPage,
      LoginPage loginPage) {
      this.user = user;
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
                  primaryStage, this.user, this, this.loginPage);
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
                    user.removeUser();
                    primaryStage.setScene(this.loginPage.getScene());
                  });
        },
        0,
        6);
    AddClock();
    return new Scene(grid, 300, 250);
  }

  protected void newUserInfoButton(int col, int row) {
    String message = "Username: " + user;
    for (int i = 0; i < user.getCardsCopy().size(); i++) {
      message += System.lineSeparator();
      message += user.getCardsCopy().get(i);
    }
      String finalMessage = message;
      placeButton(
        "Info",
        () -> {
          Alert alert =
              makeAlert(
                  "User Information",
                  "Your user information:",
                      finalMessage,
                  Alert.AlertType.INFORMATION);
          alert.showAndWait();
        },
        col,
        row);
  }
}