import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class UserPage extends Page {
  private Scene scene;
  private LoginPage parentPage;
  private User user;
  public UserPage(Stage primaryStage, LoginPage parentPage, User user) {
    this.scene = makeScene(primaryStage);
    this.parentPage = parentPage;
    this.user = user;
  }

  public Scene getScene() {
    return this.scene;
  }

  private Scene makeScene(Stage primaryStage) {
    GridPane grid = getGrid();
    placeButton("Cards",
        () -> System.out.println("Here is a list of my cards!"),
        grid,
        0, 0);
    placeButton("Logout",
        () -> primaryStage.setScene(parentPage.getScene()),
        grid,
        0, 1);
    return new Scene(grid, 300, 250);
  }
}
