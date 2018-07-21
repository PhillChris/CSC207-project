import javafx.stage.Stage;
import javafx.scene.Scene;

public abstract class AuthenticatedPage extends Page {
  protected User user;
  protected Page parentPage;

  public AuthenticatedPage(Stage primaryStage,
      UserParser userParser,
      CardParser cardParser,
      User user,
      Page parentPage) {
    super(primaryStage, userParser, cardParser);
    this.user = user;
    this.parentPage = parentPage;
    this.scene = makeUserScene(primaryStage);
  }

  protected abstract Scene makeUserScene(Stage primaryStage);
}
