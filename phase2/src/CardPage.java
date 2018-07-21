import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CardPage extends AuthenticatedPage {

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
    placeButton("Add card", () -> {
      cardParser.add(user);
      primaryStage.setScene(new CardPage(primaryStage, this.userParser, this.cardParser, this.user, this.parentPage, this.loginPage).getScene());
    }, 0, 0);
    placeButton("Remove card", () -> System.out.println("Cards will be removed here!"), 0, 1);
    placeButton("Go Back", () -> primaryStage.setScene(parentPage.getScene()), 0, 2);
    return new Scene(grid, 300, 250);
  }

  protected void addUserData(Stage primaryStage) {
    for (int i = 0; i < user.getCardsCopy().size(); i++) {
        placeButton(
            "Card #" + user.getCardsCopy().get(i).getId(),
            () -> System.out.println("This is a card!"),
            0,
            3 + i);
    }
  }

  protected void updateUserData(Stage primaryStage) {
  }
}
