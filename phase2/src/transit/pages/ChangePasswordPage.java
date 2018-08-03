package transit.pages;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import transit.system.IncorrectPasswordException;
import transit.system.InvalidPasswordException;
import transit.system.MessageTransitException;
import transit.system.User;

public class ChangePasswordPage extends AuthenticatedPage {
  /**
   * Initialized a new instance of ChangeNamePage
   *
   * @param primaryStage The stage for this page to be displayed
   * @param user The user associated with this page
   */
  public ChangePasswordPage(Stage primaryStage, User user) {
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
    grid.setPadding(new Insets(20, 20, 20, 20));
    grid.setHgap(10);
    grid.setVgap(10);
    placeLabel("Current password: ", 0, 0);
    placeLabel("New password: ", 0, 1);
    Label changeSuccess = placeLabel("", 2, 1);
    PasswordField currPassword = placePasswordField(grid, "",1, 0, "currPassInput");
    PasswordField newPassword = placePasswordField(grid, "", 1, 1, "newPassInput");
    placeButton(
      "Change password!",
      () -> {
        try {
          user.changePassword(currPassword.getText(), newPassword.getText());
        } catch (MessageTransitException e) {
          e.setMessage(changeSuccess);
        }
        primaryStage.setScene(new UserPage(primaryStage, user).getScene());
      },
      2,
      0);

    this.scene = new Scene(grid, 500, 100);
  }
}
