import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class UserPage extends AuthenticatedPage {
  private Label userLabel;

  public UserPage(
      Stage primaryStage,
      UserParser userParser,
      CardParser cardParser,
      User user,
      LoginPage parentPage) {
    super(primaryStage, userParser, cardParser, user, null, parentPage);
  }

  protected Scene makeScene(Stage primaryStage) {
    placeButton(
        "Cards",
        () -> {
          CardPage cardPage =
              new CardPage(
                  primaryStage, this.userParser, this.cardParser, this.user, this, this.loginPage);
          primaryStage.setScene(cardPage.getScene());
        },
        0,
        2);
    return super.makeScene(primaryStage);
  }

  protected void addUserData(Stage primaryStage) {
    this.userLabel = placeLabel("Hello " + user.getUserName(), 0, 1);
  }

  void updatePage(Stage primaryStage) {
    updateLabel(this.userLabel, "Hello " + user.getUserName());
  }
}
