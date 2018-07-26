package transit.pages;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.AdminUser;
import transit.system.TransitTime;
import transit.system.User;

public class UserPage extends AuthenticatedPage {
  public UserPage(Stage primaryStage, User user) {
    super(primaryStage, user);
    addUserData(primaryStage);
    makeScene(primaryStage);
  }

  protected void makeScene(Stage primaryStage) {
    placeButton(
        "Cards",
        () -> {
          CardPage cardPage = new CardPage(primaryStage, this.user);
          primaryStage.setScene(cardPage.getScene());
        },
        0,
        2);
    placeButton(
        "Monthly Expenditure (Current Year)",
        () -> {
          Stage secondaryStage = new Stage();
          UserGraphPage graphPage = new UserGraphPage(secondaryStage, this.user);
          secondaryStage.setTitle("Monthly Expenditure for user " + user);
          secondaryStage.setScene(graphPage.getScene());
          secondaryStage.show();
        },
        0,
        3);
    super.makeScene(primaryStage);

    placeButton(
        "Get last 3 trips",
        () -> {
          Alert a =
              makeAlert(
                  "Last 3 trips",
                  "Last 3 trips of user " + user,
                  user.getLastThreeMessage(),
                  AlertType.INFORMATION);
          a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
          a.showAndWait();
        },
        0,
        7);

    placeButton("Pause time", () -> TransitTime.pauseTime(), 0, 15);

    placeButton("Start time", () -> TransitTime.startTime(), 0, 16);

    placeButton("Jump ahead 1 hour", () -> TransitTime.fastForward(), 0, 17);

    if (user instanceof AdminUser) {
      placeButton(
          "Toggle admin panel",
          () -> primaryStage.setScene(new AdminUserPage(primaryStage, (AdminUser) user).getScene()),
          0,
          20);
    }
  }

  protected void addUserData(Stage primaryStage) {
    placeLabel("Hello " + user.getUserName(), 0, 1);
  }
}
