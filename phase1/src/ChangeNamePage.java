import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ChangeNamePage extends Page {
  private User user;
  private UserPage parentPage;

  public ChangeNamePage(
      Stage primaryStage,
      UserParser userParser,
      CardParser cardParser,
      User user,
      UserPage parentPage) {
    super(primaryStage, userParser, cardParser);
    this.user = user;
    this.parentPage = parentPage;
  }

  public Scene makeScene(Stage primaryStage) {
    GridPane grid = getGrid();

    placeLabel(grid, "New Username: ", 0, 0);
    TextField newName = placeTextField(grid, 1, 0);
    placeButton(
        "Change name!",
        () -> {
          this.userParser.changeName(this.user, newName.getText());
          primaryStage.setScene(this.parentPage.getScene());
        },
        grid,
        1,
        1);

    return new Scene(grid, 300, 250);
  }
}
