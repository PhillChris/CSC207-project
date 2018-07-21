import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public abstract class AuthenticatedPage extends Page {
  protected User user;
  protected AuthenticatedPage parentPage;
  protected LoginPage loginPage;

  public AuthenticatedPage(Stage primaryStage,
      UserParser userParser,
      CardParser cardParser,
      User user,
      AuthenticatedPage parentPage,
      LoginPage loginPage) {
    super(primaryStage, userParser, cardParser);
    this.user = user;
    this.parentPage = parentPage;
    this.loginPage = loginPage;
    addUserData(primaryStage);
  }

  protected abstract void addUserData(Stage primaryStage);

  abstract void updatePage(Stage primaryStage);

  protected void updateLabel(Label label, String newMessage) {
    label.setText(newMessage);
  }
}
