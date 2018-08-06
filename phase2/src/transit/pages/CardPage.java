package transit.pages;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.Card;
import transit.system.User;
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
   * @param primaryStage The stage for this page to be displaced
   * @param user The user associated with this page
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
   * @param primaryStage the stage which this scene is being served on, passed for button-action
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
   * @param primaryStage the stage which this scene is being served on, passed for button-action
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
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   * @param id the id of the current card being represented
   * @param i the current iteration of the loop in CardPage.makeScene
   */
  private void addCardButtons(int id, int i) {
    factory.makeButton(
        grid,
        "Tap",
        () -> new TapPage(stage, cards, cards.getCardsCopy().get(id), "Bus"),
        0,
        3 + i);

    factory.makeButton(
        grid,
        "Add funds",
        () ->
                new AddFundsPage(stage, cards.getCardsCopy().get(id)),
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
                  new CardPage(stage, cards);
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
            new CardPage(stage, cards);
          },
          3,
          3 + i);
    } else {
      factory.makeButton(
          grid,
          "Activate this card",
          () -> {
            cards.getCardsCopy().get(id).activateCard();
            new CardPage(stage, cards);
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
