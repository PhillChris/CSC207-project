package transit.pages;

import java.util.ArrayList;
import java.time.LocalDate;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import transit.system.AdminUser;
import transit.system.TransitTime;
import transit.system.User;

/** Represents a page displaying all stats regarding users in this TransitSystem */
public class UserStatsPage extends Page {
  private AdminUser admin;
  private ArrayList<Label> userStats = new ArrayList<>();

  public UserStatsPage(Stage primaryStage, AdminUser admin) {
    this.admin = admin;
    makeScene(primaryStage);
  }

  void makeScene(Stage primaryStage) {
    placeLabel("User stats page", 0, 0);
    ChoiceBox<LocalDate> dateOptions = placeDateOptions(0, 1);
    dateOptions.setOnAction(e -> {
      refreshStats(primaryStage, dateOptions.getValue());
    });
    refreshStats(primaryStage, TransitTime.getCurrentDate());
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }
  private void refreshStats(Stage primaryStage, LocalDate selectedDate) {
    int i = 2;
    while (!userStats.isEmpty()) {
      grid.getChildren().remove(userStats.get(0));
      userStats.remove(userStats.get(0));
    }
    for (User u : User.getAllUsersCopy().values()) {
      userStats.add(placeLabel(u.getUserName()
              + " taps on: "
              + u.getTapsIn(selectedDate)
              + ", taps off: "
              + u.getTapsOut(selectedDate),
          0,
          i)
      );
      i++;
    }
    placeButton(
        "Go back",
        () -> primaryStage.setScene(new AdminUserPage(primaryStage, admin).getScene()),
        0,
        i);
  }
}
