package transit.pages;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.Card;

/** Represents a page used by a user to add funds to a card */
public class AddFundsPage extends Page {
  /** The card associated with this page */
  private Card card;

  /**
   * Initialize a new AddFundsPage
   *
   * @param card The card associated with this page
   */
  public AddFundsPage(Stage stage, Card card) {
    super(stage);
    this.card = card;
    makeScene();
    stage.setScene(scene);
    stage.setTitle("Add funds page");
    stage.show();
  }

  /**
   * Sets the scene for this page
   */
  public void makeScene() {
    factory.makeLabel(grid, "Add the following amount: ", 0, 0);
    placeCostButton(10, 0, 1);
    placeCostButton(20, 0, 2);
    placeCostButton(50, 0, 3);
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  /**
   * Places the CostButton on this page
   *
   * @param cost The cost the button will add to the card when clicked
   * @param col The column number of the button
   * @param row The row number of the button
   */
  private void placeCostButton(int cost, int col, int row) {
    factory.makeButton(
            grid,
        "$" + cost,
        () -> {
          card.addBalance(cost * 100);
          stage.close();
        },
        col,
        row);
  }
}
