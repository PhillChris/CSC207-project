package transit.system;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class UserPage extends AuthenticatedPage {
  private Label userLabel;

  public UserPage(
      Stage primaryStage,
      User user,
      LoginPage parentPage) {
    super(primaryStage, user, null, parentPage);
    this.scene = makeScene(primaryStage);
  }

  protected Scene makeScene(Stage primaryStage) {
    placeButton(
        "Cards",
        () -> {
          CardPage cardPage =
              new CardPage(
                  primaryStage, this.user, this, this.loginPage);
          primaryStage.setScene(cardPage.getScene());
        },
        0,
        2);
    placeButton(
        "Monthly Expenditure (Current Year)",
      () -> {
        UserGraphPage graphPage =
          new UserGraphPage(
            primaryStage, this.user);
        primaryStage.setScene(graphPage.getScene());
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
