package transit.pages;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import transit.system.MessageTransitException;
import transit.system.User;

public class ChangePasswordPage extends Page {

  /** The user associated with this page */
  private User user;

  /**
   * Initialized a new instance of ChangeNamePage
   *
   * @param stage The stage for this page to be displayed
   * @param user The user associated with this page
   */
  public ChangePasswordPage(Stage stage, User user) {
    super(stage);
    this.user = user;
    makeScene();
    setAndShow("Change Password Page");
  }

  @Override
  /** Makes and sets the scene attribute */
  public void makeScene() {
    // Initialize margins for grid
    grid.setPadding(new Insets(20, 20, 20, 20));
    grid.setHgap(10);
    grid.setVgap(10);

    // Add labels to the scene
    factory.makeLabel(grid, "Current password: ", 0, 0);
    factory.makeLabel(grid, "New password: ", 0, 1);
    Label changeSuccess = factory.makeLabel(grid, "", 2, 1);

    // Add password fields to the scene
    PasswordField currPassword = factory.makePasswordField(grid, "", 1, 0);
    PasswordField newPassword = factory.makePasswordField(grid, "", 1, 1);

    // Make the change password button and set it on action
    factory.makeButton(
        grid,
        "Change password!",
        () -> {
          try {
            user.getPersonalInfo().changePassword(currPassword.getText(), newPassword.getText());
            changeSuccess.setTextFill(Color.web("#33AF54"));
            changeSuccess.setText("Password changed");
          } catch (MessageTransitException e) {
            e.setMessage(changeSuccess);
          }
        },
        2,
        0);

    // Sets the scene
    scene = new Scene(grid, 575, 110);
    scene
        .getStylesheets()
        .add(getClass().getResource("styling/ChangeInfoPage.css").toExternalForm());
  }
}
