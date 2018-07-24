package transit.system;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class TapPage extends Page {
  private ArrayList<Button> routeButtons = new ArrayList<>();
  private ChoiceBox<String> routeOptions = new ChoiceBox<>();
  private Card card;

  public TapPage(Stage secondaryStage, Card card) {
    this.card = card;
    makeScene(secondaryStage);
  }

  public void makeScene(Stage secondaryStage) {
    placeLabel("Choose route type!", 0, 0);
    ChoiceBox<String> routeType = new ChoiceBox();
    routeType.getItems().addAll("Bus", "Subway");
    refreshRouteOptionItems("Bus");
    routeType.setOnAction(e -> {
      refreshRouteOptionItems(routeType.getValue());
    });
    grid.add(routeType, 1, 0);
    grid.add(this.routeOptions, 0, 2);
    this.scene = new Scene(grid, 300, 250);
  }

  private void refreshRouteOptionItems(String type) {
    if (Station.getStationsCopy(type) != null) {
      this.routeOptions.setItems(
          FXCollections.observableList(new ArrayList<>(Station.getStationsCopy("Bus").keySet())));
    } else {
      this.routeOptions.getItems().removeAll(this.routeOptions.getItems());
    }
  }
}