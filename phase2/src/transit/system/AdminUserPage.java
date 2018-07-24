package transit.system;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminUserPage extends AuthenticatedPage {
  public AdminUserPage(Stage primaryStage, User user) {
    super(primaryStage, user);
    makeScene(primaryStage);
  }

  @Override
  protected void makeScene(Stage primaryStage) {
    placeLabel("Station name: ", 0, 1);
    TextField stationName = placeTextField(1, 1);

    placeLabel("Station type: ", 0, 2);
    ChoiceBox<String> stationTypes = new ChoiceBox<>();
    stationTypes.getItems().addAll("Bus", "Subway");
    stationTypes.getSelectionModel().select(0);

    grid.add(stationTypes, 1, 2);
    placeButton(
        "Make Station", () -> makeNewStation(stationName.getText(), stationTypes.getValue()), 1, 3);

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
        "See daily system reports",
        () -> {
          Alert alert =
              makeAlert(
                  "Daily report",
                  "Here is a daily system report",
                  ((AdminUser) user).dailyReports(),
                  AlertType.INFORMATION);
          alert.showAndWait();
        },
        0,
        3);

    placeButton(
        "Toggle user panel",
        () -> primaryStage.setScene(new UserPage(primaryStage, this.user).getScene()),
        0,
        4);

    this.scene = new Scene(grid, 300, 250);
  }

  public void addUserData(Stage primaryStage) {}

  private void makeNewStation(String name, String type) {
    try {
      new Station(name, type);
    } catch (InvalidInputException e) {
      System.out.println("found");
      makeAlert(
              "Station Construction",
              "Station name",
              "A station of the same type and name already exists.",
              Alert.AlertType.WARNING)
          .showAndWait();
    }
  }
}
