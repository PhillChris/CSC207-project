package transit.pages;

import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.AdminUser;
import transit.system.StatisticsMaker;
import transit.system.TransitTime;

/** Represents the page seen by an AdminUser when logging into the transit system */
public class AdminUserPage extends Page {
  /** The AdminUser whose page is being displayed */
  private AdminUser adminUser;

  /**
   * Constructs an instance of AdminUserPage
   *
   * @param primaryStage the stage which this page is being served on, passed for button-action
   * @param adminUser the AdminUser whose page is to be constructed
   */
  public AdminUserPage(Stage primaryStage, AdminUser adminUser) {
    super(primaryStage);
    this.adminUser = adminUser;
    makeScene(primaryStage);
  }

  /**
   * Makes the scene to be passed to the given stage for display
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  protected void makeScene(Stage primaryStage) {
    makeSceneButtons(primaryStage);
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  /**
   * Adds all buttons to the AdminUserPage
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  private void makeSceneButtons(Stage primaryStage) {
    placeButton(
        "Monthly Revenue (Current Year)",
        () -> {
          createUserGraphPage();
        },
        0,
        2);

    placeButton("Add/append route", () -> createRoutePage(), 0, 4);
    placeButton(
        "Daily Report",
        () ->
            makeAlert("", "", StatisticsMaker.generateReportMessage(), AlertType.INFORMATION)
                .showAndWait(),
        0,
        5);
    placeButton(
        "Toggle User Panel",
        () -> primaryStage.setScene(new UserPage(primaryStage, this.adminUser).getScene()),
        0,
        6);
    placeButton(
        "Station Statistics",
        () -> primaryStage.setScene(new StationStatsPage(primaryStage, adminUser).getScene()),
        0,
        7);
    placeButton(
        "User Statistics",
        () -> primaryStage.setScene(new UserStatsPage(primaryStage, adminUser).getScene()),
        0,
        8);
    placeButton(
        "Logout", () -> primaryStage.setScene(new LoginPage(primaryStage).getScene()), 0, 9);
  }

  /**
   * Makes the route creation page popup when the appropriate button is pushed
   */
  private void createRoutePage() {
    Stage secondaryStage = new Stage();
    RouteCreationPage routepage = new RouteCreationPage(secondaryStage);
    secondaryStage.setScene(routepage.getScene());
    secondaryStage.setTitle("Route Creation Page");
    secondaryStage.show();
  }

  /** Makes the user graph page popup when the appropriate button is pushed */
  private void createUserGraphPage() {
    Stage secondaryStage = new Stage();
    UserGraphPage graphPage = new UserGraphPage(secondaryStage, this.adminUser);
    secondaryStage.setTitle("Monthly Revenue for " + TransitTime.getCurrentDate().getYear());
    secondaryStage.setScene(graphPage.getScene());
    secondaryStage.show();
  }
}
