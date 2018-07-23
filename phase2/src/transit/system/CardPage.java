package transit.system;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CardPage extends AuthenticatedPage {
  private ArrayList<Button> cardButtons = new ArrayList<>();

  public CardPage(
      Stage primaryStage,
      User user) {
    super(primaryStage, user);
    makeScene(primaryStage);
  }

  protected void makeScene(Stage primaryStage) {
    placeButton(
        "Add card",
        () -> {
          user.addCard();
          primaryStage.setScene(new CardPage(primaryStage, this.user).getScene());
        },
        0,
        0);
    TextField removeCardNumber = placeTextField(1, 1);
    placeButton("Remove card", () -> {
      makeConfirmationAlert("Removal confirmation", "Confirm removal:", "Are you sure that you want to remove this card?", () -> {
        try {
          user.removeCard(user.getCardsCopy().get(Integer.parseInt(removeCardNumber.getText())));
          primaryStage.setScene(new CardPage(primaryStage, this.user).getScene());
        } catch (NumberFormatException a) {
          Alert alert = makeAlert("Invalid number", "", "The provided argument is not a valid number", AlertType.ERROR);
          alert.showAndWait();
        }
      });
    }, 0, 1);
    placeButton("Go Back", () -> primaryStage.setScene(new UserPage(primaryStage, this.user).getScene()), 0, 2);

    // AddClock();
    this.scene = new Scene(grid, 300, 250);
  }

  protected void addUserData(Stage primaryStage) {
    int i = 0;
    for (Integer id: this.user.getCardsCopy().keySet()) {
      placeButton(
          "Card #" + user.getCardsCopy().get(id).getId(),
          () -> System.out.println("This is a card!"),
          0,
          3 + i);
      i++;
    }
  }
}
