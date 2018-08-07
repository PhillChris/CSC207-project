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
  /** Constructs a new instance of TimeControlPage */
  public TimeControlPage(Stage stage) {
    super(stage);
    makeScene();
    stage.setX(1000);
    stage.setY(200);
    stage.setScene(this.scene);
    stage.setTitle("Time Control Page");
    stage.show();
    stage.setOnCloseRequest(Event::consume);
  }

  /**
   * Builds the contents of the TimeControlPage
   *
   */
  void makeScene() {
    grid.setHgap(10);
    grid.setVgap(10);

    Button control = factory.makeButton(grid, "Pause time", () -> {}, 0, 0);
    control.setId("control");
    GridPane.setColumnSpan(control, 4);
    GridPane.setHalignment(control, HPos.CENTER);


    control.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if (TransitTime.getClock().isRunning()) {
          TransitTime.getClock().pauseTime();
          control.setText("Resume time");
        } else {
          TransitTime.getClock().startTime();
          control.setText("Pause time");
        }
      }
    });

    control.setPrefWidth(250);

    factory.makeLabel(grid, "Jump ahead: ", 0, 1);

    factory.makeButton(grid, "1 hour", () -> TransitTime.getClock().skipHours(), 1, 1);

    factory.makeButton(grid, "1 day", () -> TransitTime.getClock().skipDay(), 2, 1);

    factory.makeButton(grid, "1 month", () -> TransitTime.getClock().skipMonth(), 3, 1);

    grid.setAlignment(Pos.CENTER);

    this.scene = new Scene(grid, 360, 100);
    scene.getStylesheets().add(TimeControlPage.class.getResource("styling/TimeControlPage.css").toExternalForm());
  }
}
