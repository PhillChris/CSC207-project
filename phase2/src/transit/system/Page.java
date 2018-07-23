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
  protected Label time;

  public Scene getScene() {
    return this.scene;
  }

  abstract Scene makeScene(Stage primaryStage);

  protected Label placeLabel(String text, int col, int row) {
    Label tempLabel = new Label(text);
    grid.add(tempLabel, col, row);
    return tempLabel;
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

  protected Button placeButton(String text, Runnable function, int col, int row) {
    Button button = new Button(text);
    button.setOnAction(data -> function.run());
    grid.add(button, col, row);
    return button;
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
    time = new Label();
    grid.add(time, 0, 0);
    Timeline timeline = new Timeline();
    timeline.setCycleCount(Timeline.INDEFINITE);

    KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event) {
        TransitTime.updateTime();
        time.setTextFill(BLACK);
        time.setText(TransitTime.getCurrentTime());
      }
    }
    );
    timeline.getKeyFrames().add(frame);
    timeline.playFromStart();
  }
}