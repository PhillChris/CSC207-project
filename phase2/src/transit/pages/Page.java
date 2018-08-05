package transit.pages;

import java.util.Optional;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import transit.system.Database;
import transit.system.LogWriter;
import transit.system.TransitTime;

/** Represents a general page serving scenes to be represented in the transit system program */
public abstract class Page {
  /** Represents the scene which this page serves */
  protected Scene scene;
  /** Represents the grid where elements on the page are placed */
  protected GridPane grid = new GridPane();

  /** The default constructor for this page */
  public Page() {}

  /**
   * A constructor that sets the primary stages close action to end the program. As a result, this
   * constructor should only be used by subclasses which create Pages on the primary stage, and want
   * the application to terminate when the stage is closed.
   *
   * @param primaryStage the PRIMARY stage of this application.
   */
  public Page(Stage primaryStage) {
    primaryStage.setOnCloseRequest(
        new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent windowEvent) {
            Database.writeToDatabase();
            Platform.exit();
            LogWriter.getLogWriter()
                .logInfoMessage(Page.class.getName(), "Page", "Program session terminated");
          }
        });
  }
  /** @return the scene to be represented in the program stage */
  public Scene getScene() {
    return this.scene;
  }

  /**
   * Called upon Page construction to build the scene, placing the necessary elements to a given
   * page. NOTE: ALL CHILD IMPLEMENTATIONS MUST SET THE SCENE ATTRIBUTE, OTHERWISE NO SCREEN IS
   * PASSED WHEN GETSCENE IS CALLED
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   *     purposes
   */
  abstract void makeScene(Stage primaryStage);

  /**
   * A general helper method to place a label with the given text at the given coordinates and give
   * it an id
   *
   * @param text the message to be displayed by this label
   * @param col the column in the grid where this label is displayed
   * @param row the row in the grid where this label is displayed
   */
  protected Label placeLabel(GridPane grid, String text, int col, int row, String id) {
    Label tempLabel = new Label(text);
    tempLabel.setId(id);
    grid.add(tempLabel, col, row);
    return tempLabel;
  }

  protected Label placeLabel(String text, int col, int row) {
    Label tempLabel = new Label(text);
    grid.add(tempLabel, col, row);
    return tempLabel;
  }

  /**
   * A general helper method to place a text field at the given coordinates
   *
   * @param col the column in the grid where this text field is displayed
   * @param row the row in the grid where this text field is displayed
   * @return the created text field object, for reference in accessing entered data
   */
  protected TextField placeTextField(int col, int row) {
    TextField textField = new TextField();
    grid.add(textField, col, row);
    return textField;
  }

  /**
   * A general helper method to place a text field at the given coordinates and give it an id
   *
   * @param col the column in the grid where this text field is displayed
   * @param row the row in the grid where this text field is displayed
   * @return the created text field object, for reference in accessing entered data
   */
  protected TextField placeTextField(
      GridPane grid, String promptText, int col, int row, String id) {
    TextField textField = new TextField();
    textField.setId(id);
    textField.setPromptText(promptText);
    grid.add(textField, col, row);
    return textField;
  }

  /**
   * A general helper method to place a password text field at the given coordinates and give it an
   * id
   *
   * @param col the column in the grid where this password field is displayed
   * @param row the row in the grid where this password is displayed
   * @return the created password field object, for reference in accessing entered data
   */
  protected PasswordField placePasswordField(
      GridPane grid, String promptText, int col, int row, String id) {
    PasswordField passwordField = new PasswordField();
    passwordField.setId(id);
    passwordField.setPromptText(promptText);
    grid.add(passwordField, col, row);
    return passwordField;
  }

  /**
   * A general helper method to place a button running a custom function at the given coordinates
   *
   * @param text the text to be displayed on the given button
   * @param function the function to be run upon, having return type void
   * @param col the column in the grid where this button is displayed
   * @param row the row in the grid where this button is displayed
   */
  protected Button placeButton(String text, Runnable function, int col, int row) {
    Button button = new Button(text);
    button.setOnAction(data -> function.run());
    grid.add(button, col, row);
    return button;
  }

  /**
   * A general helper method to place a button running a custom function at the given coordinates
   *
   * @param text the text to be displayed on the given button
   * @param function the function to be run upon, having return type void
   * @param col the column in the grid where this button is displayed
   * @param row the row in the grid where this button is displayed
   */
  protected Button placeButton(String text, Runnable function, GridPane grid, int col, int row) {
    Button button = new Button(text);
    button.setOnAction(data -> function.run());
    grid.add(button, col, row);
    return button;
  }

  /**
   * A general helper method to place a button running a custom function at the given coordinates
   * and give it an id
   *
   * @param text the text to be displayed on the given button
   * @param function the function to be run upon, having return type void
   * @param col the column in the grid where this button is displayed
   * @param row the row in the grid where this button is displayed
   */
  protected void placeButton(String text, Runnable function, int col, int row, String id) {
    Button button = new Button(text);
    button.setOnAction(data -> function.run());
    button.setId(id);
    grid.add(button, col, row);
  }

  protected Button placeButton(String text, Runnable function, int col, int row, int colSpan) {
    Button button = new Button(text);
    button.setOnAction(data -> function.run());
    grid.add(button, col, row, colSpan, 1);
    return button;
  }

  protected void placeSeparator(GridPane grid, int col, int row, int colSpan, String id) {
    Separator horizontalSeparator = new Separator();
    horizontalSeparator.setId(id);
    grid.add(horizontalSeparator, col, row, colSpan, 1);
  }

  /**
   * A general helper method to place a checkbox at the given coordinates
   *
   * @param text the text displayed next to the given text box
   * @param col the column in the grid where this checkbox is displayed
   * @param row the row in the grid where this checkbox is displayed
   * @return the created checkbox object, to listen to the status of the checkbox
   */
  protected CheckBox placeCheckBox(String text, int col, int row) {
    CheckBox checkBox = new CheckBox(text);
    grid.add(checkBox, col, row);
    return checkBox;
  }

  protected void placeImage(GridPane grid, String url, int col, int row, String id) {
    ImageView image = new ImageView(new Image(url));
    image.setId(id);
    grid.add(image, col, row);
  }

  /**
   * A general helper method to create a user alert
   *
   * @param title the name of this alert
   * @param header the text header in the body of this alert
   * @param content the text content in the body of this alert
   * @param type the type of alert given (NOTE: for confirmation, use built-in makeConfirmationAlert
   *     instead)
   * @return the created alert, to be able to display it on the screen later
   */
  protected Alert makeAlert(String title, String header, String content, AlertType type) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    return alert;
  }

  /**
   * A general helper method to create a user alert confirming a given action triggered in the
   * program. No need to showAndWait, as it is built in to the method.
   *
   * @param title the name of this alert
   * @param header the text header in the body of this alert
   * @param content the text content in the body of this alert
   * @param function the function to be executed upon the user confirming the action
   */
  protected void makeConfirmationAlert(
      String title, String header, String content, Runnable function) {

    Alert alert = makeAlert(title, header, content, AlertType.CONFIRMATION);

    ButtonType confirm = new ButtonType("Proceed");
    ButtonType reject = new ButtonType("Cancel");
    alert.getButtonTypes().setAll(confirm, reject);

    Optional<ButtonType> result = alert.showAndWait();

    if (result.get() == confirm) {
      function.run();
    }
  }

  /** Adds a clock with live updating time on the user page */
  protected void addClock() {
    Label tempLabel = TransitTime.getClock().getTimeLabel();
    grid.add(tempLabel, 0, 0, 2, 1);
  }

  /** Adds the logo of a train to the page */
  protected void addTrain() {
    GridPane trainPane = new GridPane();
    ImageView train = new ImageView(new Image("transit/pages/assets/train.png"));
    trainPane.setPadding(new Insets(30, 0, 0, 10));
    trainPane.add(train, 0, 0);
    grid.add(trainPane, 1, 0, 1, 2);
  }
}
