package transit.pages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import transit.system.TransitTime;

import java.sql.Time;

/** Represents a time control window in this simulation */
public class TimeControlPage extends Page {
  /** Constructs a new instance of TimeControlPage */
  public TimeControlPage(Stage secondaryStage) {
    makeScene(secondaryStage);
  }

  /**
   * Builds the contents of the TimeControlPage
   *
   * @param secondaryStage the alternate window on which this popup is served
   */
  void makeScene(Stage secondaryStage) {
    grid.setHgap(10);
    grid.setVgap(10);

    Button control = placeButton("Pause time", () -> {}, 0, 0, 4);
    control.setId("control");
    GridPane.setHalignment(control, HPos.CENTER);


    control.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if (TransitTime.isRunning()) {
          TransitTime.pauseTime();
          control.setText("Resume time");
        } else {
          TransitTime.startTime();
          control.setText("Pause time");
        }
      }
    });

    control.setPrefWidth(250);

    placeLabel("Jump ahead: ", 0, 1);

    placeButton("1 hour", () -> TransitTime.fastForward(), 1, 1);

    placeButton("1 day", () -> TransitTime.skipDay(), 2, 1);

    placeButton("1 month", () -> TransitTime.skipMonth(), 3, 1);

    grid.setAlignment(Pos.CENTER);

    this.scene = new Scene(grid, 360, 100);
    scene.getStylesheets().add(TimeControlPage.class.getResource("styling/TimeControlPage.css").toExternalForm());
  }
}
