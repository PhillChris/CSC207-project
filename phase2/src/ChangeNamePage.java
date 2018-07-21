import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangeNamePage extends AuthenticatedPage {
  public ChangeNamePage(
      Stage primaryStage,
      UserParser userParser,
      CardParser cardParser,
      User user,
      UserPage parentPage,
      LoginPage loginPage) {
    super(primaryStage, userParser, cardParser, user, parentPage, loginPage); }

  public Scene makeScene(Stage primaryStage) {
    placeLabel("New Username: ", 0, 0);
    TextField newName = placeTextField(1, 0);
    placeButton(
        "Change name!",
        () -> {
          this.userParser.changeName(this.user, newName.getText());
          this.parentPage.updateUserData(primaryStage);
          primaryStage.setScene(this.parentPage.getScene());
        },
        1,
        1);

    return new Scene(grid, 300, 250);
  }

  protected void addUserData(Stage primaryStage) {
    // return this.scene; //todo: add user data here
  }

  void updateUserData(Stage primaryStage) {

  }
}
