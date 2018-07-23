package transit.system;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class UserPage extends AuthenticatedPage {
  public UserPage(
      Stage primaryStage,
      User user) {
    super(primaryStage, user);
    this.scene = makeScene(primaryStage);
  }

  protected Scene makeScene(Stage primaryStage) {
    placeButton(
        "Cards",
        () -> {
          CardPage cardPage =
              new CardPage(
                  primaryStage, this.user);
          primaryStage.setScene(cardPage.getScene());
        },
        0,
        2);
    placeButton(
        "Monthly Expenditure (Current Year)",
      () -> {
        Stage secondaryStage = new Stage();
        UserGraphPage graphPage =
          new UserGraphPage(
            secondaryStage, this.user);
        secondaryStage.setTitle("Monthly Expenditure for user " + user);
        secondaryStage.setScene(graphPage.getScene());
        secondaryStage.show();
      },
      0,
      3);
    return super.makeScene(primaryStage);
  }

  protected void addUserData(Stage primaryStage) {
    placeLabel("Hello " + user.getUserName(), 0, 1);
  }
}
