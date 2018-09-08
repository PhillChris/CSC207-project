package transit.pages;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import transit.system.TransitTime;

/** Represents a time control window in this simulation */
public class TimeControlPage extends Page {

  /**
   * Constructs and displays new instance of TimeControlPage
   *
   * @param stage the stage on which this page is being served
   */
  public TimeControlPage(Stage stage) {
    super(stage);
    makeScene();
    stage.setX(1000);
    stage.setY(200);
    // Set and show the given scene

    setAndShow("Time Control Page");
    stage.setOnCloseRequest(Event::consume);
  }

  /**
   * Builds the contents of the TimeControlPage
   */
  void makeScene() {
    // Set grid gaps
    grid.setHgap(10);
    grid.setVgap(10);

    // Make resume/pause time button
    Button control = factory.makeButton(grid, "Pause time", () -> {}, 0, 0);
    control.setId("control");
    GridPane.setColumnSpan(control, 4);
    GridPane.setHalignment(control, HPos.CENTER);
    control.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            if (TransitTime.getInstance().isRunning()) {
              TransitTime.getInstance().pauseTime();
              control.setText("Resume time");
            } else {
              TransitTime.getInstance().startTime();
              control.setText("Pause time");
            }
          }
        });

    control.setPrefWidth(250);

    factory.makeLabel(grid, "Jump ahead: ", 0, 1);

    factory.makeButton(grid, "1 hour", () -> TransitTime.getInstance().skipHour(), 1, 1);

    factory.makeButton(grid, "1 day", () -> TransitTime.getInstance().skipDay(), 2, 1);

    factory.makeButton(grid, "1 month", () -> TransitTime.getInstance().skipMonth(), 3, 1);

    grid.setAlignment(Pos.CENTER);

    this.scene = new Scene(grid, 360, 100);
    scene
        .getStylesheets()
        .add(TimeControlPage.class.getResource("styling/TimeControlPage.css").toExternalForm());
  }
}
