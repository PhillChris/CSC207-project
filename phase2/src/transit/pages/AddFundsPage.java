package transit.pages;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import transit.system.Card;
import transit.system.User;

public class AddFundsPage extends AuthenticatedPage {
  private Card card;
  public AddFundsPage(Stage primaryStage, User user, Card card) {
    super(primaryStage, user);
    this.card = card;
    makeScene(primaryStage);
  }

  public void addUserData(Stage primaryStage) {}

  public void makeScene(Stage primaryStage) {
    placeLabel("Add the following amount: ", 0, 0);
    placeCostButton(primaryStage, 10, 0, 1);
    placeCostButton(primaryStage, 20, 0, 2);
    placeCostButton(primaryStage, 50, 0, 3);
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  private void placeCostButton(Stage primaryStage, int cost, int col, int row) {
    placeButton("$" + cost, () -> {
      card.addBalance(cost * 100);
      primaryStage.setScene(new CardPage(primaryStage, user).getScene());
    }, col, row);
  }
}
