package transit.system;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.Region;

public class ChangeNamePage extends AuthenticatedPage {
  public ChangeNamePage(
      Stage primaryStage, User user) {
    super(primaryStage, user);
    makeScene(primaryStage);
  }

  public void makeScene(Stage primaryStage) {
    placeLabel("New Username: ", 0, 0);
    TextField newName = placeTextField(1, 0);
    placeButton(
        "Change name!",
        () -> {
          user.changeName(newName.getText());
          primaryStage.setScene(new UserPage(primaryStage, user).getScene());
        },
        1,
        1);

    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  protected void addUserData(Stage primaryStage) {
    // return this.scene; //todo: add user data here
  }
}
