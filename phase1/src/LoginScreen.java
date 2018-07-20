import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginScreen extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Login Form");
    GridPane grid = new GridPane();

    placeLabel(grid, "Username: ",1);
    placeLabel(grid, "Password: ", 2);

    TextField usernameField = new TextField();
    grid.add(usernameField, 1, 1);

    PasswordField passwordField = new PasswordField();
    grid.add(passwordField, 1, 2);

    Button loginBtn = new Button("Log In");
    loginBtn.setOnAction(data -> {
      System.out.println("You've logged in!");
    });
    grid.add(loginBtn, 2, 1);

    Button newAccountButton = new Button("Sign Up");
    newAccountButton.setOnAction(data -> {
      System.out.println("Sign up!");
    });
    grid.add(newAccountButton, 2, 2);

    primaryStage.setScene(new Scene(grid, 300, 250));
    primaryStage.show();
  }

  private void placeLabel(GridPane grid, String text, int row) {
    Label tempLabel = new Label(text);
    grid.add(tempLabel, 0, row);
  }
}
