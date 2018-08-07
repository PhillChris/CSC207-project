package transit.pages;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.*;

/** Represents a page displaying all possibilities for tapping in this transit system */
public class TapPage extends Page {
  /** The type selected in the selection bar upon creation of this TapPage */
  ChoiceBox<String> routeType = new ChoiceBox<>();
  /** The user whose card is currently tapping */
  private UserCardCommands cards;
  /** The card which is currently tapping */
  private Card card;
  /** The grid where station buttons are placed */
  private GridPane stationLayout = new GridPane();

  /**
   * Constructs a new TapPage
   *
   * @param card the card which is currently tapping
   */
  public TapPage(Stage stage, UserCardCommands cards, Card card) {
    super(stage);
    this.cards = cards;
    this.card = card;
    makeScene();
    stage.setTitle("Tap " + card);
    stage.setScene(scene);
    stage.show();
  }

  /** Makes the scene which displays this page */
  @Override
  public void makeScene() {
    stationLayout.setVgap(10);
    stationLayout.setHgap(20);
    factory.makeLabel(grid, "Choose route type!", 0, 0);
    routeType.getItems().addAll(Station.POSSIBLE_TYPES);
    routeType.getSelectionModel().select(0);
    routeType.setOnAction(e -> refreshRouteOptionItems());
    grid.add(routeType, 1, 0);
    grid.add(stationLayout, 0, 1, 50, 30);
    this.scene = new Scene(grid, 500, 300);
  }

  /**
   * Refreshes the buttons displayed on the TapPage depending on which type is selected
   *
   * @param type the current type of routes being displayed
   */
  private void refreshRouteOptionItems() {
    // Clear out all buttons
    stationLayout.getChildren().clear();
    String type = routeType.getValue();
    int i = 1;
    for (Route route : Route.getRoutesCopy().get(type)) {
      int j = 0;
      for (Station station : route.getRouteStationsCopy()) {
        placeStationButton(stationLayout, station, j, i);
        j++;
      }
      i++;
    }
  }

  /**
   * Makes a button tapping at a given station, at the specified coordinates
   *
   * @param station the station being tapped at
   * @param col the column where this button will be placed in this page's grid
   * @param row the row where this button will be placed in this page's grid
   * @return the button created at this place in the grid
   */
  private Button placeStationButton(GridPane grid, Station station, int col, int row) {
    return factory.makeButton(
        grid,
        station.toString(),
        () -> {
          Alert alert;
          try {
            cards.tap(card, station);
            if (card.getCurrentTrip() != null) {
              alert =
                  factory.makeAlert(
                      "Tapped In",
                      "Tapped in",
                      String.format(
                          "Tap in at %s, at time %s",
                          station.toString(), TransitTime.getClock().getCurrentTimeString()),
                      AlertType.CONFIRMATION);
            } else {
              alert =
                  factory.makeAlert(
                      "Tapped Out",
                      "Tapped Out",
                      String.format(
                          "Tap out at %s, at time %s, with trip fee $%.2f.",
                          station.toString(),
                          TransitTime.getClock().getCurrentTimeString(),
                          (card.getLastTrip().getFee()) / 100.0),
                      AlertType.CONFIRMATION);
            }
          } catch (Exception b) {
            alert =
                factory.makeAlert(
                    "Tap error",
                    "Tap error",
                    "There was a problem in tapping at this point, no tap request could be processed",
                    AlertType.ERROR);
          }
          alert.showAndWait();
        },
        col,
        row);
  }
}
