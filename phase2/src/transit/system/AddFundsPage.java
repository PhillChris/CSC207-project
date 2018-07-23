package transit.system;

import javafx.stage.Stage;
import javafx.scene.Scene;

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
    placeCostButton(primaryStage, 20, 1, 1);
    placeCostButton(primaryStage, 50, 2, 1);
    this.scene = new Scene(grid, 300, 250);
  }

  private void placeCostButton(Stage primaryStage, int cost, int col, int row) {
    placeButton("$" + cost, () -> {
      card.addBalance(cost * 100);
      primaryStage.setScene(new CardPage(primaryStage, user).getScene());
    }, col, row);
  }
}
