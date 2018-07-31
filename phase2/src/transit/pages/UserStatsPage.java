package transit.pages;

import java.util.ArrayList;
import java.time.LocalDate;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import transit.system.AdminUser;
import transit.system.User;

/** Represents a page displaying all stats regarding users in this TransitSystem */
public class UserStatsPage extends Page {
  /** The admin accessing this UserStatsPage*/
  private AdminUser admin;
  /** The stats being displayed on this UserStatsPage*/
  private ArrayList<Label> userStats = new ArrayList<>();

  /**
   * Constructs a new UserStatsPage
   *
   * @param primaryStage the stage on which this UserStatsPage is shown
   * @param admin the adminUser tied to this
   */
  public UserStatsPage(Stage primaryStage, AdminUser admin) {
    this.admin = admin;
    makeScene(primaryStage);
  }

  /**
   * Constructs the scene which this page displays
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  void makeScene(Stage primaryStage) {
    placeLabel("User stats page", 0, 0);
    ChoiceBox<LocalDate> dateOptions = placeDateOptions(0, 1);
    dateOptions.setOnAction(e -> {
      refreshStats(primaryStage, dateOptions.getValue());
    });
    refreshStats(primaryStage, dateOptions.getItems().get(0));
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  /**
   * Refreshes all of the station statistics displayed on this page
   *
   * @param primaryStage the stage on which this page is served
   * @param selectedDate the date selected on this page
   */
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
