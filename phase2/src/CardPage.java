import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CardPage extends Page {
  private User user;
  private UserPage parentPage;

  public CardPage(
      Stage primaryStage,
      UserParser userParser,
      CardParser cardParser,
      User user,
      UserPage parentPage) {
    super(primaryStage, userParser, cardParser);
    this.user = user;
    this.parentPage = parentPage;
  }

  protected Scene makeScene(Stage primaryStage) {
    GridPane grid = getGrid();
    placeButton("Add card", () -> System.out.println("Cards will be added here!"), grid, 0, 0);
    placeButton("Remove card", () -> System.out.println("Cards will be removed here!"), grid, 0, 1);
    placeButton("Go Back", () -> primaryStage.setScene(parentPage.getScene()), grid, 0, 2);
    return new Scene(grid, 300, 250);
  }
}
