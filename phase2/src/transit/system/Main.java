package transit.system;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import transit.pages.LoginPage;
import transit.pages.TimeControlPage;

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
    Stage secondaryStage = new Stage();
    secondaryStage.setTitle("Transit System Time Control");
    secondaryStage.setScene(new TimeControlPage(secondaryStage).getScene());
    primaryStage.show();
    secondaryStage.setX(primaryStage.getX() + primaryStage.getWidth());
    secondaryStage.setY(primaryStage.getY() + 125);
    secondaryStage.show();
    try {
      LogWriter.getLogWriter()
          .logInfoMessage("Completed program initialization", Main.class.getName(), "start");
    } catch (IOException a) {
      System.out.println("Output file not found");
    }
  }
}
