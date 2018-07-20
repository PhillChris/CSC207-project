import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public abstract class AuthenticationPage {
  protected void placeLabel(GridPane grid, String text, int row) {
    Label tempLabel = new Label(text);
    grid.add(tempLabel, 0, row);
  }

  protected TextField placeTextField(GridPane grid, int row) {
    TextField textField = new TextField(
    );
    grid.add(textField, 1, row);
    return textField;
  }

  protected PasswordField placePasswordField(GridPane grid, int row) {
    PasswordField passwordField = new PasswordField();
    grid.add(passwordField, 1, row);
    return passwordField;
  }


  protected void placeButton(String text, Runnable function, GridPane grid, int row) {
    Button loginBtn = new Button(text);
    loginBtn.setOnAction(data -> function.run());
    grid.add(loginBtn, 2, row);
  }
}
