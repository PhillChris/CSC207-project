package transit.system;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import org.controlsfx.control.CheckComboBox;

public class TapPage extends Page {
  private Card card;
  public TapPage(Stage secondaryStage, Card card) {
    this.card = card;
    makeScene(secondaryStage);
  }
  public void makeScene(Stage secondaryStage) {
    ChoiceBox<String> routeType = new ChoiceBox<>();
    this.scene = new Scene(grid, 300, 250);
  }
}
