package transit.pages;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.*;

import java.util.ArrayList;

public class TapPage extends Page {
  private ArrayList<Button> stationButtons = new ArrayList<>();
  private User user;
  private Card card;

  public TapPage(Stage secondaryStage, User user, Card card) {
    this.user = user;
    this.card = card;
    makeScene(secondaryStage);
  }

  public void makeScene(Stage secondaryStage) {
    placeLabel("Choose route type!", 0, 0);
    ChoiceBox<String> routeType = new ChoiceBox();
    routeType.getItems().addAll(Station.POSSIBLE_TYPES);
    refreshRouteOptionItems("Bus");
    routeType.getSelectionModel().select(0);
    routeType.setOnAction(e -> {
      refreshRouteOptionItems(routeType.getValue());
    });
    grid.add(routeType, 1, 0);
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  private void refreshRouteOptionItems(String type) {
    // Clear out all buttons
    while (!stationButtons.isEmpty()) {
      grid.getChildren().remove(stationButtons.get(0));
      stationButtons.remove(stationButtons.get(0));
    }

    // Add new buttons from the routes hash map of the given type
    if (Route.getRoutesCopy().get(type) != null) {
      makeStationButtons(type);
    }
  }

  private void makeStationButtons(String type) {
    int i = 1;
    for (Route route : Route.getRoutesCopy().get(type)) {
      int j = 0;
      for (Station station : route.getRouteStationsCopy()) {
        stationButtons.add(
            placeButton(
                station.toString(),
                () -> {
                  try {
                    user.tap(card, station, TransitTime.getCurrentTime());
                  } catch (Exception b) {
                    Alert alert =
                        makeAlert(
                            "Tap error",
                            "Tap error",
                            "There was a problem in tapping at this point, no tap request could be processed",
                            AlertType.ERROR);
                    alert.showAndWait();
                  }
                },
                j,
                i));
        j++;
      }
      i++;
    }
  }
}
