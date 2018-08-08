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
  private ChoiceBox<String> routeType = new ChoiceBox<>();
  /** The user whose card is currently tapping */
  private UserCardCommands cards;
  /** The card which is currently tapping */
  private Card card;
  /** The grid where station buttons are placed */
  private GridPane stationLayout = new GridPane();

  /**
   * Constructs a new TapPage
   *
   * @param stage the given user's stage
   * @param cards the given user's card commands
   * @param card the card which is currently tapping
   */
  public TapPage(Stage stage, UserCardCommands cards, Card card) {
    super(stage);
    this.cards = cards;
    this.card = card;
    makeScene();
    setAndShow("Tap " + card);
  }

  /** Makes the scene which displays this page */
  @Override
  public void makeScene() {
    // Add sub-grid borders
    stationLayout.setVgap(10);
    stationLayout.setHgap(20);

    // Add route type label
    Label choose = factory.makeLabel(grid, "Route type:", 0, 0);
    choose.setMinWidth(100);
    routeType.getItems().addAll(Station.POSSIBLE_TYPES);
    routeType.getSelectionModel().select(0);

    // Set the action for the route type dropdown
    routeType.setOnAction(
        e -> {
          refreshRouteOptionItems();
          stage.sizeToScene();
        });
    routeType.setMinWidth(100);

    // Load the initial set of buttons for tapping
    refreshRouteOptionItems();

    // Add grid borders
    grid.setHgap(10);
    grid.setHgap(10);

    // Set the padding for the overall window
    grid.setPadding(new Insets(20, 20, 20, 20));

    // Add the dropdown and subgrid to larger grid
    grid.add(routeType, 1, 0);
    grid.add(stationLayout, 0, 1, 50, 1);
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    scene.getStylesheets().add(getClass().getResource("styling/GeneralStyle.css").toExternalForm());
  }

  /** Refreshes the buttons displayed on the TapPage depending on which type is selected */
  private void refreshRouteOptionItems() {
    // Clear out all buttons
    stationLayout.getChildren().clear();
    String type = routeType.getValue();
    int i = 1;
    for (Route route : Route.getRoutesCopy().get(type)) {
      stationLayout.add(new Label(route.toString().split("\n")[0]), 0, i);
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
   * @param station the station whose button is being made
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
              // if this card tapped into a station
              alert = makeTappedInAlert(station);

            } else {
              // if this card tapped out of a station
              alert = makeTappedOutAlert(station);
            }

          } catch (InsufficientFundsException d) {
            alert = makeInsufficientFundsAlert();
          } catch (TransitException e) {
            // if this card's trip tapped was an invalid trip (i.e. not along a single route)
            alert = makeInvalidTripAlert(station);

          } catch (Exception b) {
            // if this card can't tap for some other reason
            alert = makeTapErrorAlert();
          }
          // show the given alert
          alert.getDialogPane().setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
          alert.showAndWait();
          stage.setTitle(card.toString());
        },
        col,
        row);
  }

  /**
   * @param station the station whose alert is displaying
   * @return the alert indicating tapping in
   */
  private Alert makeTappedInAlert(Station station) {
    return factory.makeAlert(
        "Tapped In",
        "Tapped in",
        String.format(
            "Tap in at %s, at time %s",
            station.toString(), TransitTime.getInstance().getCurrentTimeString()),
        AlertType.CONFIRMATION);
  }

  /**
   * @param station the station whose alert is displaying
   * @return the alert indicating tapping out
   */
  private Alert makeTappedOutAlert(Station station) {
    return factory.makeAlert(
        "Tapped Out",
        "Tapped Out",
        String.format(
            "Tap out at %s, at time %s, with trip fee $%.2f.",
            station.toString(),
            TransitTime.getInstance().getCurrentTimeString(),
            (card.getLastTrip().getFee()) / 100.0),
        AlertType.CONFIRMATION);
  }

  /**
   * @param station the station whose alert is displaying
   * @return the alert indicating an invalid trip occuring
   */
  private Alert makeInvalidTripAlert(Station station) {
    return factory.makeAlert(
        "Invalid Trip",
        "Invalid Trip",
        String.format(
            "Tapped out on an invalid trip at %s at time %s, charged $%.2f ",
            station.toString(),
            TransitTime.getInstance().getCurrentTimeString(),
            (card.getLastTrip().getFee() / 100.0)),
        AlertType.WARNING);
  }

  /** @return the alert indicating an invalid tap occuring */
  private Alert makeTapErrorAlert() {
    return factory.makeAlert(
        "Tap error",
        "Tap error",
        "There was a problem in tapping at this point, no tap request could be processed",
        AlertType.ERROR);
  }

  /** @return the alert indicating a user tapping with insufficient funds */
  private Alert makeInsufficientFundsAlert() {
    return factory.makeAlert(
        "Insufficient Funds",
        "Insufficient Funds",
        "User attempted to tap with insufficient funds",
        AlertType.WARNING);
  }
}
