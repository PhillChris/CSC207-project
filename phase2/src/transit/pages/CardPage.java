package transit.pages;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.Card;
import transit.system.User;

/** Represents a page containing all functionality associated with a user's set of cards */
public class CardPage extends Page {
  /** The user associated with this CardPage */
  private User user;
  /** The label of current trips associated with the */
  private Label currentTrips;
  /**
   * Initialize a new instance of CardPage
   *
   * @param primaryStage The stage for this page to be displaced
   * @param user The user associated with this page
   */
  public CardPage(User user) {
    this.user = user;
    addUserData();
    makeScene();
    title = "Cards";
    stage.show();
  }

  /**
   * Constructs the scene for this page
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  protected void makeScene() {
    makeButton(
        grid,
        "Add card",
        () -> {
          user.getCardCommands().addCard();
          stage.setScene(new CardPage(this.user).getScene());
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
    this.currentTrips = makeLabel(grid, generateCurrentTripMessage(user), 0, 1);
    int i = 0;
    for (Integer id : this.user.getCardCommands().getCardsCopy().keySet()) {
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
    makeButton(
        grid,
        "Tap",
        () -> new TapPage(user, user.getCardCommands().getCardsCopy().get(id), "Bus"),
        0,
        3 + i);

    makeButton(
        grid,
        "Add funds",
        () ->
            stage.setScene(
                new AddFundsPage(stage, user, user.getCardCommands().getCardsCopy().get(id))
                    .getScene()),
        1,
        3 + i);

    makeButton(
        grid,
        "Remove This Card",
        () ->
            makeConfirmationAlert(
                "Removal confirmation",
                "Confirm removal:",
                "Are you sure that you want to remove this card?",
                () -> {
                  user.getCardCommands().removeCard(user.getCardCommands().getCardsCopy().get(id));
                  stage.setScene(new CardPage(this.user).getScene());
                }),
        2,
        3 + i);

    // if the current card is suspended
    if (!user.getCardCommands().getCardsCopy().get(id).isSuspended()) {
      makeButton(
          grid,
          "Report card stolen",
          () -> {
            user.getCardCommands().getCardsCopy().get(id).suspendCard();
            stage.setScene(new CardPage(this.user).getScene());
          },
          3,
          3 + i);
    } else {
      makeButton(
          grid,
          "Activate this card",
          () -> {
            user.getCardCommands().getCardsCopy().get(id).activateCard();
            stage.setScene(new CardPage(this.user).getScene());
          },
          3,
          3 + i);
    }
  }

  /** @return the current trips message of this page */
  private String generateCurrentTripMessage(User user) {
    String message = "Current trips:" + System.lineSeparator();
    for (Card card : user.getCardCommands().getCardsCopy().values()) {
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
