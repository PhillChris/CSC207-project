import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public abstract class AuthenticationPage {
  public abstract Scene getScene();

  protected void placeLabel(GridPane grid, String text, int row) {
    Label tempLabel = new Label(text);
    grid.add(tempLabel, 0, row);
  }

  protected void placeAuthenticationFields(GridPane grid) {
    TextField usernameField = new TextField();
    grid.add(usernameField, 1, 1);

    PasswordField passwordField = new PasswordField();
    grid.add(passwordField, 1, 2);
  }

  protected void placeButton(String text, Runnable function, GridPane grid, int row) {
    Button loginBtn = new Button(text);
    loginBtn.setOnAction(data -> function.run());
    grid.add(loginBtn, 2, row);
  }
}
