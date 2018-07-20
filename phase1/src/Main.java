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
  public static void main(String[] args) throws IOException  {
    launch(args);
  }

  public void start(Stage primaryStage) {
    primaryStage.setTitle("Transit System Simulator");
    LoginPage loginPage = new LoginPage(primaryStage);
    primaryStage.setScene(loginPage.getScene());
    primaryStage.show();
  }
}
