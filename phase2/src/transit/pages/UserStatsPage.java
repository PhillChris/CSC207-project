package transit.pages;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.AdminUser;
import transit.system.TransitTime;
import transit.system.User;

/** Represents a page displaying all stats regarding users in this TransitSystem */
public class UserStatsPage extends Page {
  private AdminUser admin;

  public UserStatsPage(Stage primaryStage, AdminUser admin) {
    this.admin = admin;
    makeScene(primaryStage);
  }

  void makeScene(Stage primaryStage) {
    placeLabel("User stats page", 0, 0);
    int i = 1;
    for (User u : User.getAllUsersCopy().values()) {
      placeLabel(
          u.getUserName()
              + " taps on: "
              + u.getTapsIn(TransitTime.getCurrentDate())
              + ", taps off: "
              + u.getTapsOut(TransitTime.getCurrentDate()),
          0,
          i);
      i++;
    }
    placeButton(
        "Go back",
        () -> primaryStage.setScene(new AdminUserPage(primaryStage, admin).getScene()),
        0,
        i);
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }
}
