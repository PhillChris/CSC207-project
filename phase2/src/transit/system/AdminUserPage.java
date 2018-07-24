package transit.system;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminUserPage extends AuthenticatedPage {

  public AdminUserPage(Stage primaryStage, User user) {
    super(primaryStage, user);
    makeScene(primaryStage);
  }

  @Override
  protected void makeScene(Stage primaryStage) {
    placeButton(
            "Monthly Revenue (Current Year)",
            () -> {
              Stage secondaryStage = new Stage();
              UserGraphPage graphPage = new UserGraphPage(secondaryStage, this.user);
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
    this.scene = new Scene(grid, 300, 250);
  }

  public void addUserData(Stage primaryStage) {}
}
