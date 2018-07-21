import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SignUpPage extends Page{
  private LoginPage parentPage;

  public SignUpPage(Stage primaryStage, LoginPage parentPage, UserParser userParser, CardParser cardParser) {
    super(primaryStage, userParser, cardParser);
    this.parentPage = parentPage;
  }

  protected Scene makeScene(Stage primaryStage) {
    placeLabel( "New User: ", 0, 1);
    placeLabel( "New Email: ", 0, 2);
    placeLabel( "New Password: ", 0,3);

    TextField username = placeTextField( 1, 1);
    TextField email = placeTextField( 1, 2);
    PasswordField password = placePasswordField( 1, 3);

    CheckBox adminBox = placeCheckBox("Is Admin?",  1, 4);

    placeButton("Go back", () -> primaryStage.setScene(parentPage.getScene()),
         1, 5);

    placeButton("Make Account!", () -> {
      try {
        userParser.add(username.getText(), email.getText(), password.getText(), adminBox.isSelected());
        primaryStage.setScene(parentPage.getScene());
      } catch (EmailInUseException a) {
        Alert alert = makeAlert("Email in use!",
            "Email in use:",
            "The email you provided is currently in use by another user.",
            AlertType.WARNING);
        alert.showAndWait();
      }
    },  1, 6);

    return new Scene(grid, 300, 250);
  }
}
