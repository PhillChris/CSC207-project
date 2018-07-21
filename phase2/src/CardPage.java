import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CardPage extends AuthenticatedPage {
  private ArrayList<Button> cardButtons = new ArrayList<>();

  public CardPage(
      Stage primaryStage,
      UserParser userParser,
      CardParser cardParser,
      User user,
      AuthenticatedPage parentPage,
      LoginPage loginPage) {
    super(primaryStage, userParser, cardParser, user, parentPage, loginPage);
  }

  protected Scene makeScene(Stage primaryStage) {
    placeButton(
        "Add card",
        () -> {
          cardParser.add(user);
          this.updatePage(primaryStage);
          primaryStage.setScene(this.getScene());
        },
        0,
        0);
    TextField removeCardNumber = placeTextField(1, 1);
    placeButton("Remove card", () -> {
      makeConfirmationAlert("Removal confirmation", "Confirm removal:", "Are you sure that you want to remove this card?", () -> {
        try {
        cardParser.remove(user, user.getCardsCopy().get(Integer.parseInt(removeCardNumber.getText())));
        this.updatePage(primaryStage);
        primaryStage.setScene(this.getScene());
        } catch (NumberFormatException a) {
          Alert alert = makeAlert("Invalid number", "", "The provided argument is not a valid number", AlertType.ERROR);
          alert.showAndWait();
        }
      });
    }, 0, 1);
    placeButton("Go Back", () -> primaryStage.setScene(parentPage.getScene()), 0, 2);
    return new Scene(grid, 300, 250);
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

  protected void updatePage(Stage primaryStage) {
    // removes all buttons currently in page
    while (!this.cardButtons.isEmpty()) {
      grid.getChildren().remove(this.cardButtons.get(0));
      this.cardButtons.remove(this.cardButtons.get(0));
    }

    // adds all buttons currently cards array
    int i = 0;
    for (Integer id: this.user.getCardsCopy().keySet()) {
      this.cardButtons.add(
          placeButton(
              "Card #" + user.getCardsCopy().get(id).getId(),
              () -> System.out.println("This is a card!"),
              0,
              3 + i));
      i++;
    }
  }
}
