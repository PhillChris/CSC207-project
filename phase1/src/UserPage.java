import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;

public class UserPage implements Page {
  private Scene scene;
  private User user;
  public UserPage(Stage primaryStage, User user) {
    this.scene = makeScene(primaryStage);
    this.user = user;
  }

  public Scene getScene() {
    return this.scene;
  }

  private Scene makeScene(Stage primaryStage) {
    StackPane stack = new StackPane();
    Button btn = new Button();
    stack.getChildren().add(btn);
    return new Scene(stack);
  }
}
