package transit.system;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

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
        "Maintenance",
        () -> {
          Stage secondaryStage = new Stage();
          MaintenancePage maintenancePage = new MaintenancePage(secondaryStage);
          secondaryStage.setTitle("Transit System Maintenance");
          secondaryStage.setScene(maintenancePage.getScene());
          secondaryStage.show();
        },
        0,
        4);
    placeButton(
        "Daily Report",
        () -> {
          Alert alert = makeAlert("", "", this.adminUser.dailyReports(), AlertType.INFORMATION);
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
        "Logout", () -> primaryStage.setScene(new LoginPage(primaryStage).getScene()), 0, 7);
    this.scene = new Scene(grid, 300, 250);
  }

  public void addUserData(Stage primaryStage) {}
}
