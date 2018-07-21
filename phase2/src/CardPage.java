import javafx.scene.Scene;
import javafx.stage.Stage;

public class CardPage extends AuthenticatedPage {
  public CardPage(
      Stage primaryStage,
      UserParser userParser,
      CardParser cardParser,
      User user,
      UserPage parentPage) {
    super(primaryStage, userParser, cardParser, user, parentPage);
  }

  protected Scene makeScene(Stage primaryStage) {
    placeButton("Add card", () -> System.out.println("Cards will be added here!"),  0, 0);
    placeButton("Remove card", () -> System.out.println("Cards will be removed here!"),  0, 1);
    placeButton("Go Back", () -> primaryStage.setScene(parentPage.getScene()),  0, 2);
    return new Scene(grid, 300, 250);
  }

  protected Scene makeUserScene(Stage primaryStage) {
    return this.scene; //todo: add user data here
  }
}
