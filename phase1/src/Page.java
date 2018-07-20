import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public abstract class Page {
  protected UserParser userParser;
  protected CardParser cardParser;
  protected Scene scene;

  public Page(Stage primaryStage, UserParser userParser, CardParser cardParser) {
    this.userParser = userParser;
    this.cardParser = cardParser;
    this.scene = makeScene(primaryStage);
  }
  public abstract Scene getScene();

  abstract Scene makeScene(Stage primaryStage);

  protected GridPane getGrid() {
    return new GridPane();
  }

  protected void placeLabel(GridPane grid, String text, int col, int row) {
    Label tempLabel = new Label(text);
    grid.add(tempLabel, col, row);
  }

  protected TextField placeTextField(GridPane grid, int col, int row) {
    TextField textField = new TextField(
    );
    grid.add(textField, col, row);
    return textField;
  }

  protected PasswordField placePasswordField(GridPane grid, int col, int row) {
    PasswordField passwordField = new PasswordField();
    grid.add(passwordField, col, row);
    return passwordField;
  }


  protected void placeButton(String text, Runnable function, GridPane grid, int col, int row) {
    Button button = new Button(text);
    button.setOnAction(data -> function.run());
    grid.add(button, col, row);
  }

  protected CheckBox placeCheckBox(String text, GridPane grid, int col, int row) {
    CheckBox checkBox = new CheckBox(text);
    grid.add(checkBox, col, row);
    return checkBox;
  }

  protected Alert makeAlert(String title, String header, String content, AlertType type) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    return alert;
  }
}
