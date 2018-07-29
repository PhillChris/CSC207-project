package transit.pages;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.layout.Region;
import transit.system.*;

public class AdminUserPage extends Page {
  private AdminUser adminUser;

  public AdminUserPage(Stage primaryStage, AdminUser adminUser) {
    this.adminUser = adminUser;
    makeScene(primaryStage);
  }

  @Override
  protected void makeScene(Stage primaryStage) {
    placeButton(
        "Monthly Revenue (Current Year)",
        () -> {
          Stage secondaryStage = new Stage();
          UserGraphPage graphPage = new UserGraphPage(secondaryStage, this.adminUser);
          secondaryStage.setTitle("Monthly Revenue for " + TransitTime.getCurrentDate().getYear());
          secondaryStage.setScene(graphPage.getScene());
          secondaryStage.show();
        },
        0,
        2);

    placeButton(
        "Add/append route",
        () -> {
          createRoutePage();
        },
        0,
        4);
    placeButton(
        "Daily Report",
        () -> {
          Alert alert = makeAlert("", "", CostCalculator.generateReportMessage(), AlertType.INFORMATION);
          alert.showAndWait();
        },
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
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  public void addUserData(Stage primaryStage) {}

  private void createRoutePage(){
      Stage secondaryStage = new Stage();
      RouteCreationPage routepage = new RouteCreationPage(secondaryStage);
      secondaryStage.setScene(routepage.getScene());
      secondaryStage.setTitle("Route Creation Page");
      secondaryStage.show();
  }
}
