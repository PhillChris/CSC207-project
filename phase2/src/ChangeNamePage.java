import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangeNamePage extends AuthenticatedPage {
  public ChangeNamePage(
      Stage primaryStage,
      UserParser userParser,
      CardParser cardParser,
      User user,
      UserPage parentPage) {
    super(primaryStage, userParser, cardParser, user, parentPage); }

  public Scene makeScene(Stage primaryStage) {
    placeLabel("New Username: ", 0, 0);
    TextField newName = placeTextField(1, 0);
    placeButton(
        "Change name!",
        () -> {
          this.userParser.changeName(this.user, newName.getText());
          primaryStage.setScene(this.parentPage.getScene());
        },
        1,
        1);

    return new Scene(grid, 300, 250);
  }

  protected Scene makeUserScene(Stage primaryStage) {
    return this.scene; //todo: add user data here
  }
}
