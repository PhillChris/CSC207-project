package transit.pages;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import transit.system.User;

/** Represents a page for user's to change their name */
public class ChangeNamePage extends Page {

  /** The user associated with this page */
  private User user;

  /**
   * Initialized a new instance of ChangeNamePage
   *
   * @param stage The stage for this page to be displayed
   * @param user The user associated with this page
   */
  public ChangeNamePage(Stage stage, User user) {
    super(stage);
    this.user = user;
    makeScene();
    setAndShow("Change Name Page");
  }

  @Override
  public void makeScene() {
    grid.setPadding(new Insets(20, 20, 20, 20));
    grid.setHgap(10);
    grid.setVgap(10);
    factory.makeLabel(grid, "New Username: ", 0, 0);
    TextField newName = factory.makeTextField(grid, "", 1, 0);
    factory.makeButton(
        grid,
        "Change name!",
        () -> {
          user.getPersonalInfo().changeName(newName.getText());
          pageCreator.makeUserPage(user);
        },
        2,
        0);

    scene = new Scene(grid, 530, 70);
    scene
        .getStylesheets()
        .add(getClass().getResource("styling/ChangeInfoPage.css").toExternalForm());
  }
}
