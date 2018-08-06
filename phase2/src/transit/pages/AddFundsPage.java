package transit.pages;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.Card;
import transit.system.User;

/** Represents a page used by a user to add funds to a card */
public class AddFundsPage extends AuthenticatedPage {
  /** The card associated with this page */
  private Card card;

  /**
   * Initialize a new AddFundsPage
   *
   * @param primaryStage The stage for this page to be launched
   * @param user The user associated with this page
   * @param card The card associated with this page
   */
  public AddFundsPage(Stage primaryStage, User user, Card card) {
    super(primaryStage, user);
  }

  /**
   * Sets the scene for this page
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  public void makeScene() {
    this.card = card;
    factory.makeLabel(grid, "Add the following amount: ", 0, 0);
    placeCostButton(10, 0, 1);
    placeCostButton(20, 0, 2);
    placeCostButton(50, 0, 3);
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  /**
   * Places the CostButton on this page
   *
   * @param primaryStage The stage for this page to be displayed
   * @param cost The cost the button will add to the card when clicked
   * @param col The column number of the button
   * @param row The row number of the button
   */
  private void placeCostButton(int cost, int col, int row) {
    factory.makeButton(grid,
        "$" + cost,
        () -> {
          card.addBalance(cost * 100);
          new CardPage(user);
        },
        col,
        row);
  }
}
