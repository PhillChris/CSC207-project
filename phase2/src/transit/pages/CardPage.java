package transit.pages;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.Card;
import transit.system.UserCardCommands;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Represents a page containing all functionality associated with a user's set of cards */
public class CardPage extends Page {
  /** The user associated with this CardPage */
  private UserCardCommands cards;
  /** The label of current trips associated with the */
  private Label currentTrips;
  /** The combo box displaying the card options */
  private ComboBox<Card> cardComboBox;
  /** The selected card */
  private Card cardSelection;

  /**
   * Initialize a new instance of CardPage
   *
   * @param stage The stage for this page to be displaced
   * @param userCardCommands The card commands used by this page
   */
  public CardPage(Stage stage, UserCardCommands userCardCommands) {
    super(stage);
    this.cards = userCardCommands;
    // Default selection should be card with minimum number
    cardSelection =
        userCardCommands
            .getCardsCopy()
            .get(Collections.min(userCardCommands.getCardsCopy().keySet()));
    addUserData();
    makeScene();
    stage.setTitle("Cards");
    stage.setScene(scene);
    stage.show();
  }

  @Override
  protected void makeScene() {
    grid.setPadding(new Insets(20, 20, 20, 20));
    grid.setHgap(10);
    grid.setVgap(10);
    addCardComboBox();
    Button add =
        factory.makeButton(
            grid,
            "Add card",
            () -> {
              cards.addCard();
              pageCreator.makeCardPage(cards);
            },
            0,
            0);
    add.setMinWidth(cardComboBox.getMinWidth());
    GridPane.setColumnSpan(add, 2);

    addCardButtons();
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    scene.getStylesheets().add(getClass().getResource("styling/GeneralStyle.css").toExternalForm());
  }

  /** Adds personalized user data to this page */
  protected void addUserData() {
    this.currentTrips = factory.makeLabel(grid, generateCurrentTripMessage(), 2, 0);
    this.currentTrips.setMinWidth(200);
  }

  /** Helper to add the card combo box */
  private void addCardComboBox() {
    List<Integer> userCardKeys = new ArrayList<>(cards.getCardsCopy().keySet());
    Collections.sort(userCardKeys);
    ComboBox<Card> cardComboBox = new ComboBox<>();
    grid.add(cardComboBox, 0, 1);
    for (Integer key : userCardKeys) {
      cardComboBox.getItems().add(cards.getCardsCopy().get(key));
    }
    cardComboBox.setMinWidth(400);
    cardComboBox.setOnAction(event -> cardSelection = cardComboBox.getValue());
  }

  /** Adds the buttons specific to each card in this simulation */
  private void addCardButtons() {
    Button tap = factory.makeButton(grid, "Tap", () -> {}, 0, 2);
    tap.setMinWidth(cardComboBox.getMinWidth());
    if (!cardSelection.isSuspended()) {
      tap.setOnAction(evt -> pageCreator.makeTapPage(cards, cardSelection));
    } else {
      tap.setOnAction(
          actionEvent -> {
            Alert alert =
                factory.makeAlert(
                    "Card Suspended",
                    "Card Suspended",
                    cardSelection.toString() + " is suspended, reactivate this card to tap it",
                    Alert.AlertType.WARNING);
            alert.showAndWait();
          });
    }

    Button addFunds = factory.makeButton(grid, "Add funds", () -> {}, 0, 3);
    addFunds.setOnAction(evt -> pageCreator.makeAddFundsPage(cardSelection));
    addFunds.setMinWidth(cardComboBox.getMinWidth());

    GridPane bottom = new GridPane();
    bottom.setHgap(10);
    Button remove =
        factory.makeButton(
            bottom,
            "Remove this card",
            () -> {
              if (cards.getCardsCopy().size() > 1) {
                factory.makeConfirmationAlert(
                    "Removal confirmation",
                    "Confirm removal:",
                    "Are you sure that you want to remove this card?",
                    () -> {
                      cards.removeCard(cardSelection);
                      cardComboBox.getItems().remove(cardSelection);
                      pageCreator.makeCardPage(cards);
                    });
              } else {
                Alert warning =
                    factory.makeAlert(
                        "Removal",
                        "Removal denied:",
                        "Can't remove last card",
                        Alert.AlertType.WARNING);
                warning.showAndWait();
              }
            },
            0,
            0);
    remove.setMinWidth(cardComboBox.getMinWidth() / 2 - 5);

    // if the current card is suspended
    if (!cardSelection.isSuspended()) {
      Button report =
          factory.makeButton(
              bottom,
              "Report card stolen",
              () -> {
                cardSelection.suspendCard();
                pageCreator.makeCardPage(cards);
              },
              1,
              0);
      report.setMinWidth(cardComboBox.getMinWidth() / 2 - 5);

    } else {
      Button activate =
          factory.makeButton(
              bottom,
              "Activate this card",
              () -> {
                cardSelection.activateCard();
                pageCreator.makeCardPage(cards);
              },
              1,
              0);
      activate.setMinWidth(cardComboBox.getWidth() / 2 - 5);
    }
    grid.add(bottom, 0, 4);
  }

  /** @return the current trips message of this page */
  private String generateCurrentTripMessage() {
    String message = "Current trips:" + System.lineSeparator();
    for (Card card : cards.getCardsCopy().values()) {
      if (card.getCurrentTrip() != null) {
        message +=
            "Trip started with card #"
                + card.getId()
                + " at station "
                + card.getCurrentTrip().getStartStation()
                + System.lineSeparator();
      }
    }
    return message;
  }
}
