package transit.pages;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.User;

/** Represents a page for user's to change their name */
public class ChangeNamePage extends AuthenticatedPage {

  /**
   * Initialized a new instance of ChangeNamePage
   *
   * @param primaryStage The stage for this page to be displayed
   * @param user The user associated with this page
   */
  public ChangeNamePage(Stage primaryStage, User user) {
    super(primaryStage, user);
    makeScene(primaryStage);
  }

  /**
   * Sets the scene for this page
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
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
}
