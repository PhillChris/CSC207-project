package transit.pages;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import transit.system.TransitTime;

/** Represents a time control window in this simulation*/
public class TimeControlPage extends Page {
  /** Contructs a new instance of the */
  public TimeControlPage(Stage secondaryStage) {
    makeScene(secondaryStage);
  }

  void makeScene(Stage secondaryStage) {
    placeButton("Pause time", () -> TransitTime.pauseTime(), 0, 15);

    placeButton("Start time", () -> TransitTime.startTime(), 0, 16);

    placeButton("Jump ahead 1 hour", () -> TransitTime.fastForward(), 0, 17);

    placeButton("Jump ahead 1 day", () -> TransitTime.skipDay(), 0, 18);

    placeButton("Jump ahead 1 month", () -> TransitTime.skipMonth(), 0, 19);

    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }
}
