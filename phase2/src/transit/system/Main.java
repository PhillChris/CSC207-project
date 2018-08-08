package transit.system;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import transit.pages.PageCreator;
import transit.pages.TimeControlPage;

import java.io.IOException;
import java.io.PrintWriter;

/** A transit system simulation. */
public class Main extends Application {

  public static Stage primaryStage;

  public static Stage secondaryStage = new Stage();

  /**
   * Runs the transit system simulation, with event input from events.txt
   *
   * @param args standard Java argument
   */
  public static void main(String[] args) {
    launch(args);
  }

  public void start(Stage primaryStage) {
    primaryStage.setOnCloseRequest(
      windowEvent -> {
        Database.writeToDatabase();
        Platform.exit();
        LogWriter.getLogWriter().logEndProgram();
      });

    LogWriter.getLogWriter().clearFile();
    Main.primaryStage = primaryStage;
    primaryStage.setTitle("Transit System Simulator");
    PageCreator pageCreator = new PageCreator();
    pageCreator.makeLoginPage();
    primaryStage.show();
    new TimeControlPage(new Stage());
    LogWriter.getLogWriter()
        .logInfoMessage(Main.class.getName(), "start", "Program initialization complete");
  }
}
