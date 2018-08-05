package transit.pages;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import transit.system.Database;
import transit.system.LogWriter;
import transit.system.TransitTime;

import java.util.Optional;

/** Represents a general page serving scenes to be represented in the transit system program */
public abstract class Page {
  /** Represents the scene which this page serves */
  protected Scene scene;
  /** Represents the grid where elements on the page are placed */
  protected GridPane grid = new GridPane();

  /** The title of any window of this page */
  protected String title;

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
            LogWriter.getLogWriter().closeHandlers();
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
   * @param col the column in the gridPane where this label is displayed
   * @param row the row in the gridPane where this label is displayed
   */
  protected Label makeLabel(GridPane gridPane, String text, int col, int row) {
    Label label = new Label(text);
    gridPane.add(label, col, row);
    return label;
  }

  protected Separator makeSeparator(GridPane gridPane, int col, int row) {
    Separator horizontalSeparator = new Separator();
    gridPane.add(horizontalSeparator, col, row);
    return horizontalSeparator;
  }

  /**
   * A general helper method to place a text field at the given coordinates
   *
   * @param col the column in the gridPane where this text field is displayed
   * @param row the row in the gridPane where this text field is displayed
   * @return the created text field object, for reference in accessing entered data
   */
  protected TextField makeTextField(GridPane gridPane, String promptText, int col, int row) {
    TextField textField = new TextField();
    textField.setPromptText(promptText);
    gridPane.add(textField, col, row);
    return textField;
  }

  /**
   * A general helper method to place a password text field at the given coordinates and give it an
   * id
   *
   * @param col the column in the gridPane where this password field is displayed
   * @param row the row in the gridPane where this password is displayed
   * @return the created password field object, for reference in accessing entered data
   */
  protected PasswordField makePasswordField(
      GridPane gridPane, String promptText, int col, int row) {
    PasswordField passwordField = new PasswordField();
    passwordField.setPromptText(promptText);
    gridPane.add(passwordField, col, row);
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
  protected Button makeButton(GridPane gridPane, String text, Runnable function, int col, int row) {
    Button button = new Button(text);
    button.setOnAction(data -> function.run());
    gridPane.add(button, col, row);
    return button;
  }

  /**
   * Adds a combo box to this page
   *
   * @param grid The grid to add the box
   * @param choices The choices displayed by the combobox
   * @param col The column in which the combo box will be placed on the grid
   * @param row The row in which the combo box will be placed on the grid
   * @return A combobox with the given specifications
   */
  protected ComboBox<String> makeComboBox(GridPane grid, String[] choices, int col, int row) {
    ComboBox<String> comboBox = new ComboBox<>();
    comboBox.getItems().addAll(choices);
    comboBox.getSelectionModel().selectFirst();
    grid.add(comboBox, col, row);
    return comboBox;
  }

  /**
   * Adds an image to this page
   *
   * @param gridPane The grid to add the image
   * @param url The source url for this image
   * @param col The column in which this image will be placed in the grid
   * @param row The row in which this image will be placed in the grid
   * @return An image with the given specifications
   */
  protected ImageView makeImage(GridPane gridPane, String url, int col, int row) {
    ImageView image = new ImageView(new Image(url));
    gridPane.add(image, col, row);
    return image;
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

  /**
   * Helper method for creating a button that launches a popup window
   * @param gridPane The grid that this button will be added to
   * @param page The type of page of the popup window
   * @param col The column in the grid that this button will be added to
   * @param row The row in the grid that this button will be added to
   */
  protected Button makePopupButton(GridPane gridPane, Page page, int col, int row) {
    Stage window = new Stage();
    window.setTitle(page.title);
    window.setScene(page.getScene());

    return makeButton(gridPane, page.title,
      () -> {
        if (window.isShowing()) {
          window.toFront();
        } else {
          window.show();
        }
      }, col, row);
  }

  protected Button makeChangeViewButton(GridPane gridPane, Stage primaryStage, Page page, int col, int row) {
    return makeButton(gridPane, page.title, () -> {
      primaryStage.setScene(page.getScene());
      primaryStage.setTitle(page.title);
    }, col, row);
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
