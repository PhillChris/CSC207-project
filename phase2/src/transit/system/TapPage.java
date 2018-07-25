package transit.system;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class TapPage extends Page {
  private ChoiceBox<Station> stationOptions = new ChoiceBox<>();
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
    routeType.setOnAction(e -> refreshRouteOptionItems(routeType.getValue()));
    grid.add(routeType, 1, 0);
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  private void refreshRouteOptionItems(String type) {
    // Clear out all buttons
    for (Button button: stationButtons) {
      grid.getChildren().remove(button);
      stationButtons.remove(button);
    }

    // Add new buttons from the routes hash map of the given type
    if (Route.getRoutesCopy().get(type) != null) {
      int i = 1;
      for (Route route: Route.getRoutesCopy().get(type)) {
        int j = 0;
        for (Station station: route.getRouteStationsCopy()) {
          placeButton(station.getName(), () -> {
            try {
              user.tap(card, station, TransitTime.getCurrentTime());
            } catch (TransitException a) {
              System.out.println(a.getMessage());
            } catch (Exception b) {
              Alert alert =
                  makeAlert(
                      "Tap error",
                      "Tap error",
                      "There was a problem in tapping at this point, no tap request could be processed",
                      AlertType.ERROR);
              alert.showAndWait();
            }
          }, j, i);
          j++;
        }
        i++;
      }
    }
  }
}
