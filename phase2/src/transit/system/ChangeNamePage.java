package transit.system;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangeNamePage extends AuthenticatedPage {
  public ChangeNamePage(
      Stage primaryStage, User user) {
    super(primaryStage, user);
    this.scene = makeScene(primaryStage);
  }

  public Scene makeScene(Stage primaryStage) {
    placeLabel("New Username: ", 0, 0);
    TextField newName = placeTextField(1, 0);
    placeButton(
        "Change name!",
        () -> {
          user.changeName(newName.getText());
          new UserPage(primaryStage, user).updatePage(primaryStage);
          primaryStage.setScene(new UserPage(primaryStage, user).getScene());
        },
        1,
        1);

    return new Scene(grid, 300, 250);
  }

  protected void addUserData(Stage primaryStage) {
    // return this.scene; //todo: add user data here
  }

  void updatePage(Stage primaryStage) {}
}
