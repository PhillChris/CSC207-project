import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

/** A transit system simulation. */
public class Main extends Application {
  /**
   * Runs the transit system simulation, with event input from events.txt
   *
   * @param args standard Java argument
   * @throws IOException
   */
  public static void main(String[] args) {
    launch(args);
  }

  public void start(Stage primaryStage) throws IOException {
    primaryStage.setTitle("Transit System Simulator");
    BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
    LoginPage loginPage = new LoginPage(primaryStage, writer);
    primaryStage.setScene(loginPage.getScene());
    primaryStage.show();
  }
}
