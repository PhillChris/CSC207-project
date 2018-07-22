import javafx.scene.Scene;
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
    return new Scene(grid, 300, 250);
  }

  public void updatePage(Stage primaryStage) {}

  public void addUserData(Stage primaryStage) {}
}
