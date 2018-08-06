package transit.pages;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import transit.system.User;

/** Represents a page for user's to change their name */
public class ChangeNamePage extends Page {

  private User user;

  /**
   * Initialized a new instance of ChangeNamePage
   *
   * @param primaryStage The stage for this page to be displayed
   * @param user The user associated with this page
   */
  public ChangeNamePage(Stage primaryStage, User user) {
    super(new Stage());
    this.user = user;
    makeScene();
    stage.setTitle("Change Name Page");
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Sets the scene for this page
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  public void makeScene() {
    grid.setPadding(new Insets(20, 20, 20, 20));
    grid.setHgap(10);
    grid.setVgap(10);
    makeLabel(grid, "New Username: ", 0, 0);
    TextField newName = makeTextField(grid, "",1, 0);
    makeButton(grid,
        "Change name!",
        () -> {
          user.getPersonalInfo().changeName(newName.getText());
          stage.setScene(new UserPage(stage, user).getScene());
        },
        2,
        0);

    scene = new Scene(grid, 550, 70);
    scene.getStylesheets().add(getClass().getResource("styling/ChangeInfoPage.css").toExternalForm());
  }
}
