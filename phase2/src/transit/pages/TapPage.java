package transit.pages;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
    Label choose = factory.makeLabel(grid, "Route type:", 0, 0);
    choose.setMinWidth(100);
    routeType.getItems().addAll(Station.POSSIBLE_TYPES);
    routeType.getSelectionModel().select(0);
    routeType.setOnAction(e -> refreshRouteOptionItems());
    routeType.setMinWidth(100);
    refreshRouteOptionItems(); // do this action to load the buttons initially
    grid.setHgap(10);
    grid.setHgap(10);
    grid.setPadding(new Insets(20, 20, 20, 20));
    grid.add(routeType, 1, 0);
    grid.add(stationLayout, 0, 1, 50, 30);
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    scene.getStylesheets().add(getClass().getResource("styling/GeneralStyle.css").toExternalForm());
  }

  /**
   * Refreshes the buttons displayed on the TapPage depending on which type is selected
   */
  private void refreshRouteOptionItems() {
    // Clear out all buttons
    stationLayout.getChildren().clear();
    String type = routeType.getValue();
    int i = 1;
    for (Route route : Route.getRoutesCopy().get(type)) {
      stationLayout.add(new Label(route.toString()), 0, i);
      int j = 1;
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

          } catch (TransitException e) {
            alert =
                    factory.makeAlert(
                            "Invalid Trip",
                            "Tapped Out",
                            String.format(
                                    "Tapped out on an invalid trip at %s at time %s, charged $%.2f ",
                                    station.toString(),
                                    TransitTime.getClock().getCurrentTimeString(),
                                    (card.getLastTrip().getFee() / 100.0)),
                            AlertType.WARNING);
          } catch (Exception b) {
            alert =
                factory.makeAlert(
                    "Tap error",
                    "Tap error",
                    "There was a problem in tapping at this point, no tap request could be processed",
                    AlertType.ERROR);
          }
          alert.getDialogPane().setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
          alert.showAndWait();
          stage.setTitle(card.toString());
        },
        col,
        row);
  }
}
