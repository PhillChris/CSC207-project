package transit.pages;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
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
    title = "Change Password";
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
    makeLabel(grid, "Current password: ", 0, 0);
    makeLabel(grid, "New password: ", 0, 1);
    Label changeSuccess = makeLabel(grid, "", 2, 1);
    PasswordField currPassword = makePasswordField(grid, "",1, 0);
    PasswordField newPassword = makePasswordField(grid, "", 1, 1);
    makeButton(grid,
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

    scene = new Scene(grid, 600, 130);
    scene.getStylesheets().add(getClass().getResource("styling/ChangeInfoPage.css").toExternalForm());
  }
}
