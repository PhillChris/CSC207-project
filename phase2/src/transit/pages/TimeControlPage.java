package transit.pages;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import transit.system.TransitTime;

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
    placeButton("Pause time", () -> TransitTime.pauseTime(), 0, 0);

    placeButton("Start time", () -> TransitTime.startTime(), 0, 1);

    placeButton("Jump ahead 1 hour", () -> TransitTime.fastForward(), 0, 2);

    placeButton("Jump ahead 1 day", () -> TransitTime.skipDay(), 0, 3);

    placeButton("Jump ahead 1 month", () -> TransitTime.skipMonth(), 0, 4);

    grid.setAlignment(Pos.CENTER);

    this.scene = new Scene(grid, 150, 150);
  }
}
