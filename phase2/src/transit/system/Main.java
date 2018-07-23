package transit.system;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

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

  public void start(Stage primaryStage) {
    primaryStage.setTitle("Transit System Simulator");
    LoginPage loginPage = new LoginPage(primaryStage);
    primaryStage.setScene(loginPage.getScene());
    TransitTime.updateTimeLabel();
    primaryStage.show();
  }
}
