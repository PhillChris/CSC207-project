package transit.pages;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.Card;
import transit.system.UserCardCommands;

import java.util.ArrayList;
import java.util.List;

/** Represents a page containing all functionality associated with a user's set of cards */
public class CardPage extends Page {
  /** The user associated with this CardPage */
  private UserCardCommands cards;
  /** The label of current trips associated with the */
  private Label currentTrips;

  private ComboBox<Card> cardComboBox;

  private Card cardSelection;
  /**
   * Initialize a new instance of CardPage
   *
   * @param stage The stage for this page to be displaced
   */
  public CardPage(Stage stage, UserCardCommands cards) {
    super(stage);
    this.cards = cards;
    cardSelection = cards.getCardsCopy().get(1);
    addUserData();
    makeScene();
    stage.setTitle("Cards");
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Constructs the scene for this page
   *
   */
  @Override
  protected void makeScene() {
    addCardComboBox();
    factory.makeButton(
        grid,
        "Add card",
        () -> {
          cards.addCard();
          pageCreator.makeCardPage(cards);
        },
        0,
        0);

    // addClock();
    addCardButtons();
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  /**
   * Adds personalized user data to this page
   *
   */
  protected void addUserData() {
    this.currentTrips = factory.makeLabel(grid, generateCurrentTripMessage(), 0, 1);
  }

  private void addCardComboBox() {
    List<Card> userCards = new ArrayList<>();

    for (int i = 1; i <= cards.getCardsCopy().size(); i++) {
      userCards.add(cards.getCardsCopy().get(i));
    }

    cardComboBox = factory.makeCardComboBox(grid, userCards, 0, 2);
    cardComboBox.setOnAction(event -> cardSelection = cardComboBox.getValue());
  }
  /**
   * Adds the buttons specific to each card in this simulation
   *
   * @param id the id of the current card being represented
   * @param i the current iteration of the loop in CardPage.makeScene
   */
  private void addCardButtons() {
    Button tap = factory.makeButton(
        grid,
        "Tap",
            () -> {},
        0,
        3);
    tap.setOnAction(evt -> pageCreator.makeTapPage(cards, cardSelection));

    Button addFunds = factory.makeButton(
        grid,
        "Add funds",
        () -> {},
        1,
        3);
    addFunds.setOnAction(evt -> pageCreator.makeAddFundsPage(cardSelection));

    factory.makeButton(
        grid,
        "Remove This Card",
        () ->
            factory.makeConfirmationAlert(
                "Removal confirmation",
                "Confirm removal:",
                "Are you sure that you want to remove this card?",
                () -> {
                  cards.removeCard(cardSelection);
                  pageCreator.makeCardPage(cards);
                }),
        2,
        3);

    // if the current card is suspended
    if (!cardSelection.isSuspended()) {
      factory.makeButton(
          grid,
          "Report card stolen",
          () -> {
            cardSelection.suspendCard();
            pageCreator.makeCardPage(cards);
          },
          3,
          3);
    } else {
      factory.makeButton(
          grid,
          "Activate this card",
          () -> {
            cardSelection.activateCard();
            pageCreator.makeCardPage(cards);
          },
          3,
          3);
    }
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
