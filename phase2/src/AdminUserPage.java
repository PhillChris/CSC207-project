import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminUserPage extends AuthenticatedPage {

  public AdminUserPage(
      Stage primaryStage,
      UserParser userParser,
      CardParser cardParser,
      User user,
      LoginPage loginPage) {
    super(primaryStage, userParser, cardParser, user, null, loginPage);
  }

  @Override
  Scene makeScene(Stage primaryStage) {
    placeLabel("Station name: ", 0, 1);
    TextField stationName = placeTextField(1, 1);

    placeLabel("Station type: ", 0, 2);
    ChoiceBox<String> stationTypes = new ChoiceBox<>();
    stationTypes.getItems().addAll("Bus", "Subway");
    stationTypes.getSelectionModel().select(0);

    grid.add(stationTypes, 1, 2);
    placeButton(
            "Make Station", () -> makeNewStation(stationName.getText(), stationTypes.getValue()), 1, 3);

    return new Scene(grid, 300, 250);
  }

  public void updatePage(Stage primaryStage) {}

  public void addUserData(Stage primaryStage) {}

  private void makeNewStation(String name, String type) {
    if (type.equals("Bus")) {
      new BusStation(name);
      System.out.println("New bus");
    } else {
      new SubwayStation(name);
      System.out.println("New subway");
    }
  }
}
