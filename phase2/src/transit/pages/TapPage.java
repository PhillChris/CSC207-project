package transit.pages;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.Card;
import transit.system.Route;
import transit.system.Station;
import transit.system.TransitTime;
import transit.system.User;

/** Represents a page displaying all possibilities for tapping in this transit system */
public class TapPage extends Page {
  /** The tap options of a given card */
  private ArrayList<Button> stationButtons = new ArrayList<>();
  /** The user whose card is currently tapping */
  private User user;
  /** The card which is currently tapping */
  private Card card;
  /** The label outlining current trips this user is taking */
  private Stage cardPageStage;
  /** The type selected in the selection bar upon creation of this TapPage */
  private String selectedType;

  /**
   * Constructs a new TapPage
   *
   * @param secondaryStage the stage on which this page is being served
   * @param user the user whose card is currently tapping
   * @param card the card which is currently tapping
   * @param cardPageStage the label to update when this page
   */
  public TapPage(
      Stage secondaryStage, User user, Card card, Stage cardPageStage, String selectedType) {
    this.user = user;
    this.card = card;
    this.cardPageStage = cardPageStage;
    this.selectedType = selectedType;
    title = "Tap " + card;
    makeScene(secondaryStage);
  }

  /**
   * Makes the scene which displays this page
   *
   * @param secondaryStage the popup stage on which this page is served
   */
  @Override
  public void makeScene(Stage secondaryStage) {
    makeLabel(grid, "Choose route type!", 0, 0);
    ChoiceBox<String> routeType = new ChoiceBox<>();
    setupRouteTypeBox(routeType, secondaryStage);
    grid.add(routeType, 1, 0);
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  /**
   * Refreshes the buttons displayed on the TapPage depending on which type is selected
   *
   * @param type the current type of routes being displayed
   */
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

  /**
   * Makes all of the station buttons to be displayed
   *
   * @param type the type of stations currently being displayed
   */
  private void makeStationButtons(String type) {
    int i = 1;
    for (Route route : Route.getRoutesCopy().get(type)) {
      int j = 0;
      for (Station station : route.getRouteStationsCopy()) {
        stationButtons.add(placeStationButton(station, j, i));
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
  private Button placeStationButton(Station station, int col, int row) {
    return makeButton(grid,
        station.toString(),
        () -> {
          Alert alert;
          try {
            user.getCardCommands().tap(card, station);
            if (card.getCurrentTrip() != null) {
              alert =
                  makeAlert(
                      "Tapped In",
                      "Tapped in",
                      String.format(
                          "Tap in at %s, at time %s",
                          station.toString(), TransitTime.getClock().getCurrentTimeString()),
                      AlertType.CONFIRMATION);
            } else {
              alert =
                  makeAlert(
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
                makeAlert(
                    "Tap error",
                    "Tap error",
                    "There was a problem in tapping at this point, no tap request could be processed",
                    AlertType.ERROR);
          }
          alert.showAndWait();
          this.cardPageStage.setScene(new CardPage(cardPageStage, user).getScene());
        },
        col,
        row);
  }

  /**
   * Sets up and initializes the checkbox selecting which type of route to display
   *
   * @param routeType the checkbox selecting route type
   */
  private void setupRouteTypeBox(ChoiceBox<String> routeType, Stage secondaryStage) {
    routeType.getItems().addAll(Station.POSSIBLE_TYPES);
    refreshRouteOptionItems(this.selectedType); /* Loads the first round of buttons*/
    routeType.getSelectionModel().select(this.selectedType);
    routeType.setOnAction(
        e ->
            secondaryStage.setScene(
                new TapPage(secondaryStage, user, card, cardPageStage, routeType.getValue()).getScene()));
    refreshRouteOptionItems(routeType.getValue());
  }
}
