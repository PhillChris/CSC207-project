package transit.pages;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.Region;
import transit.pages.LoginPage;
import transit.pages.Page;
import transit.system.AdminUser;
import transit.system.EmailInUseException;
import transit.system.InvalidEmailException;
import transit.system.User;

public class SignUpPage extends Page {
  public SignUpPage(
      Stage primaryStage) {
    makeScene(primaryStage);
  }

  protected void makeScene(Stage primaryStage) {
    placeLabel("User: ", 0, 1);
    placeLabel("New Email: ", 0, 2);
    placeLabel("New Password: ", 0, 3);

    TextField username = placeTextField(1, 1);
    TextField email = placeTextField(1, 2);
    PasswordField password = placePasswordField(1, 3);

    CheckBox adminBox = placeCheckBox("Is Admin?", 1, 4);

    placeButton("Go back", () -> primaryStage.setScene(new LoginPage(primaryStage).getScene()), 1, 5);

    placeButton(
        "Make Account!",
        () -> {
          try {
            add(username.getText(), email.getText(), password.getText(), adminBox.isSelected());
            primaryStage.setScene(new LoginPage(primaryStage).getScene());
          } catch (EmailInUseException a) {
            Alert alert =
                makeAlert(
                    "Email in use!",
                    "Email in use:",
                    "The email you provided is currently in use by another user.",
                    AlertType.WARNING);
            alert.showAndWait();
          } catch (InvalidEmailException a) {
            Alert alert =
              makeAlert(
                "Invalid email!",
                "Invalid email:",
                "The email you provided is not valid.",
                AlertType.WARNING);
            alert.showAndWait();
          }
        },
        1,
        6);

    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  void add(String username, String email, String password, boolean isAdmin)
      throws InvalidEmailException, EmailInUseException {
    try {
      if (isAdmin) {
        AdminUser admin = new AdminUser(username, email, password);
      } else {
        User user = new User(username, email, password);
      }
    } catch (EmailInUseException a) {
      throw new EmailInUseException();
    } catch (InvalidEmailException a) {
      throw new InvalidEmailException();
    }
  }
}
