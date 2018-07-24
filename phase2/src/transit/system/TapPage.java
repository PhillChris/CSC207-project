package transit.system;

import javafx.stage.Stage;
import javafx.scene.Scene;

public class TapPage extends Page {
  private Card card;
  public TapPage(Stage secondaryStage, Card card) {

  }
  public void makeScene(Stage secondaryStage) {
    this.scene = new Scene(grid, 300, 250);
  }
}
