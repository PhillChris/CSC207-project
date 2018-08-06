package transit.pages;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.Card;
import transit.system.UserCardCommands;

/** Represents a page containing all functionality associated with a user's set of cards */
public class CardPage extends Page {
  /** The user associated with this CardPage */
  private UserCardCommands cards;
  /** The label of current trips associated with the */
  private Label currentTrips;
  /**
   * Initialize a new instance of CardPage
   *
   * @param stage The stage for this page to be displaced
   */
  public CardPage(Stage stage, UserCardCommands cards) {
    super(stage);
    this.cards = cards;
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
    factory.makeButton(
        grid,
        "Add card",
        () -> {
          cards.addCard();
          new CardPage(stage, cards);
        },
        0,
        0);

    // addClock();
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  /**
   * Adds personalized user data to this page
   *
   */
  protected void addUserData() {
    this.currentTrips = factory.makeLabel(grid, generateCurrentTripMessage(), 0, 1);
    int i = 0;
    for (Integer id : cards.getCardsCopy().keySet()) {
      addCardButtons(id, i);
      i++;
    }
  }

  /**
   * Adds the buttons specific to each card in this simulation
   *
   * @param id the id of the current card being represented
   * @param i the current iteration of the loop in CardPage.makeScene
   */
  private void addCardButtons(int id, int i) {
    factory.makeButton(
        grid,
        "Tap",
            () -> pageCreator.makeTapPage(cards, cards.getCardsCopy().get(id), "Bus"),
        0,
        3 + i);

    factory.makeButton(
        grid,
        "Add funds",
        () ->
                pageCreator.makeAddFundsPage(cards.getCardsCopy().get(id)),
        1,
        3 + i);

    factory.makeButton(
        grid,
        "Remove This Card",
        () ->
            factory.makeConfirmationAlert(
                "Removal confirmation",
                "Confirm removal:",
                "Are you sure that you want to remove this card?",
                () -> {
                  cards.removeCard(cards.getCardsCopy().get(id));
                  pageCreator.makeCardPage(cards);
                }),
        2,
        3 + i);

    // if the current card is suspended
    if (!cards.getCardsCopy().get(id).isSuspended()) {
      factory.makeButton(
          grid,
          "Report card stolen",
          () -> {
            cards.getCardsCopy().get(id).suspendCard();
            pageCreator.makeCardPage(cards);
          },
          3,
          3 + i);
    } else {
      factory.makeButton(
          grid,
          "Activate this card",
          () -> {
            cards.getCardsCopy().get(id).activateCard();
            pageCreator.makeCardPage(cards);
          },
          3,
          3 + i);
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
