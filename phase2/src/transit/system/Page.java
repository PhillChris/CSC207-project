package transit.system;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Optional;

import static javafx.scene.paint.Color.BLACK;

public abstract class Page {
  protected Scene scene;
  protected GridPane grid = new GridPane();

  public Scene getScene() {
    return this.scene;
  }

  abstract Scene makeScene(Stage primaryStage);

  protected void placeLabel(String text, int col, int row) {
    Label tempLabel = new Label(text);
    grid.add(tempLabel, col, row);
  }

  protected TextField placeTextField(int col, int row) {
    TextField textField = new TextField();
    grid.add(textField, col, row);
    return textField;
  }

  protected PasswordField placePasswordField(int col, int row) {
    PasswordField passwordField = new PasswordField();
    grid.add(passwordField, col, row);
    return passwordField;
  }

  protected void placeButton(String text, Runnable function, int col, int row) {
    Button button = new Button(text);
    button.setOnAction(data -> function.run());
    grid.add(button, col, row);
  }

  protected CheckBox placeCheckBox(String text, int col, int row) {
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

  protected Alert makeConfirmationAlert(
    String title, String header, String content, Runnable function) {

    Alert alert = makeAlert(title, header, content, AlertType.CONFIRMATION);

    ButtonType confirm = new ButtonType("Proceed");
    ButtonType reject = new ButtonType("Cancel");
    alert.getButtonTypes().setAll(confirm, reject);

    Optional<ButtonType> result = alert.showAndWait();

    if (result.get() == confirm) {
      function.run();
    }

    return alert;
  }

  protected void AddClock(){
    grid.add(TransitTime.getTimeLabel(), 0, 0);
  }
}
