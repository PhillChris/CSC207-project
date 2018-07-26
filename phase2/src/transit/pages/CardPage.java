package transit.pages;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.User;

/** Represents a page containing all functionality associated with a user's set of cards */
public class CardPage extends AuthenticatedPage {

  /**
   * Initialize a new instance of CardPage
   *
   * @param primaryStage The stage for this page to be displaced
   * @param user The user associated with this page
   */
  public CardPage(Stage primaryStage, User user) {
    super(primaryStage, user);
    addUserData(primaryStage);
    makeScene(primaryStage);
  }

  /**
   * Sets the scene for this page
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  protected void makeScene(Stage primaryStage) {
    placeButton(
        "Add card",
        () -> {
          user.addCard();
          primaryStage.setScene(new CardPage(primaryStage, this.user).getScene());
        },
        0,
        0);

    placeButton(
        "Go Back",
        () -> primaryStage.setScene(new UserPage(primaryStage, this.user).getScene()),
        0,
        1);

    // addClock();
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  /**
   * Adds personalized user data to this page
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  protected void addUserData(Stage primaryStage) {
    int i = 0;
    for (Integer id : this.user.getCardsCopy().keySet()) {
      placeButton(
          "Tap Card #" + user.getCardsCopy().get(id).getId(),
          () -> {
            Stage secondaryStage = new Stage();
            secondaryStage.setTitle("Tap Card#" + user.getCardsCopy().get(id).getId());
            secondaryStage.setScene(
                new TapPage(secondaryStage, user, user.getCardsCopy().get(id)).getScene());
            secondaryStage.show();
          },
          0,
          3 + i);

      placeButton(
          "Add funds",
          () ->
              primaryStage.setScene(
                  new AddFundsPage(primaryStage, user, user.getCardsCopy().get(id)).getScene()),
          1,
          3 + i);

      placeButton(
          "Remove This Card",
          () ->
              makeConfirmationAlert(
                  "Removal confirmation",
                  "Confirm removal:",
                  "Are you sure that you want to remove this card?",
                  () -> {
                    user.removeCard(user.getCardsCopy().get(id));
                    primaryStage.setScene(new CardPage(primaryStage, this.user).getScene());
                  }),
          2,
          3 + i);

      // if the current card is suspended
      if (!user.getCardsCopy().get(id).isSuspended()) {
        placeButton(
            "Report card stolen",
            () -> {
              user.getCardsCopy().get(id).suspendCard();
              primaryStage.setScene(new CardPage(primaryStage, this.user).getScene());
            },
            3,
            3 + i);
      } else {
        placeButton(
            "Activate this card",
            () -> {
              user.getCardsCopy().get(id).activateCard();
              primaryStage.setScene(new CardPage(primaryStage, this.user).getScene());
            },
            3,
            3 + i);
      }
      i++;
    }
  }
}
