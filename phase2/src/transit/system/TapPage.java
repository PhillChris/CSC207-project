package transit.system;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

public class TapPage extends Page {
  private ChoiceBox<Station> stationOptions = new ChoiceBox<>();
  private User user;
  private Card card;

  public TapPage(Stage secondaryStage, User user, Card card) {
    this.card = card;
    this.user = user;
    makeScene(secondaryStage);
  }

  public void makeScene(Stage secondaryStage) {
    placeLabel("Choose route type!", 0, 0);
    ChoiceBox<String> routeType = new ChoiceBox();
    routeType.getItems().addAll("Bus", "Subway");
    refreshRouteOptionItems("Bus");
    routeType.setOnAction(
        e -> {
          refreshRouteOptionItems(routeType.getValue());
        });
    grid.add(routeType, 1, 0);
    grid.add(this.stationOptions, 0, 2);
    placeButton(
        "Tap!",
        () -> {
          try {
            user.tap(card, this.stationOptions.getValue(), TransitTime.getCurrentTime());
          } catch (TransitException a) {
            System.out.println(a.getMessage());
          } catch (Exception b) {
            Alert alert =
                makeAlert(
                    "No station selected",
                    "No station selected",
                    "No station was selected to be tapped, no tap request could be processed",
                    AlertType.ERROR);
            alert.showAndWait();
          }
        },
        0,
        3);
    this.scene = new Scene(grid, 300, 250);
  }

  private void refreshRouteOptionItems(String type) {
    if (Station.getStationsCopy(type) != null) {
      this.stationOptions.setItems(
          FXCollections.observableList(new ArrayList<>(Station.getStationsCopy("Bus").values())));
    } else {
      this.stationOptions.getItems().removeAll(this.stationOptions.getItems());
    }
  }
}
