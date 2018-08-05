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
  public CardPage(Stage primaryStage, User user) {
    this.user = user;
    addUserData(primaryStage);
    makeScene(primaryStage);
  }

  /**
   * Constructs the scene for this page
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  protected void makeScene(Stage primaryStage) {
    makeButton(grid,
        "Add card",
        () -> {
          user.getCardCommands().addCard();
          primaryStage.setScene(new CardPage(primaryStage, this.user).getScene());
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
  protected void addUserData(Stage primaryStage) {
    this.currentTrips = makeLabel(grid, generateCurrentTripMessage(user), 0, 1);
    int i = 0;
    for (Integer id : this.user.getCardCommands().getCardsCopy().keySet()) {
      addCardButtons(primaryStage, id, i);
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
  private void addCardButtons(Stage primaryStage, int id, int i) {
    makeButton(grid,
        "Tap Card #" + user.getCardCommands().getCardsCopy().get(id).getId(),
            () -> makeTapPage(id, primaryStage), 0, 3 + i);

    makeButton(grid,
        "Add funds",
        () ->
            primaryStage.setScene(
                new AddFundsPage(primaryStage, user, user.getCardCommands().getCardsCopy().get(id)).getScene()),
        1,
        3 + i);

    makeButton(grid,
        "Remove This Card",
        () ->
            makeConfirmationAlert(
                "Removal confirmation",
                "Confirm removal:",
                "Are you sure that you want to remove this card?",
                () -> {
                  user.getCardCommands().removeCard(user.getCardCommands().getCardsCopy().get(id));
                  primaryStage.setScene(new CardPage(primaryStage, this.user).getScene());
                }),
        2,
        3 + i);

    // if the current card is suspended
    if (!user.getCardCommands().getCardsCopy().get(id).isSuspended()) {
      makeButton(grid,
          "Report card stolen",
          () -> {
            user.getCardCommands().getCardsCopy().get(id).suspendCard();
            primaryStage.setScene(new CardPage(primaryStage, this.user).getScene());
          },
          3,
          3 + i);
    } else {
      makeButton(grid,
          "Activate this card",
          () -> {
            user.getCardCommands().getCardsCopy().get(id).activateCard();
            primaryStage.setScene(new CardPage(primaryStage, this.user).getScene());
          },
          3,
          3 + i);
    }
  }

  /**
   * Makes a new popup window containing only a tap page
   *
   * @param id The id of the card whose tap page is to be displayed
   */
  private void makeTapPage(int id, Stage primaryStage) {
    Stage secondaryStage = new Stage();
    secondaryStage.setTitle("Tap Card#" + user.getCardCommands().getCardsCopy().get(id).getId());
    secondaryStage.setScene(
        new TapPage(secondaryStage, user, user.getCardCommands().getCardsCopy().get(id), primaryStage, "Bus").getScene());
    secondaryStage.show();
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
