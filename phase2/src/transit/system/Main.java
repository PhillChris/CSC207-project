package transit.system;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
        new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent windowEvent) {
            Database.writeToDatabase();
            Platform.exit();
            LogWriter.getLogWriter().logEndProgram();
          }
        });

    clearFile("log.txt");
    this.primaryStage = primaryStage;
    primaryStage.setTitle("Transit System Simulator");
    PageCreator pageCreator = new PageCreator();
    pageCreator.makeLoginPage();
    primaryStage.show();
    new TimeControlPage(new Stage());
    LogWriter.getLogWriter()
        .logInfoMessage(Main.class.getName(), "start", "Program initialization complete");
  }

  private void clearFile(String fileName) {
    try {
      PrintWriter fileClear = new PrintWriter(fileName);
      fileClear.write("");
      fileClear.close();
    } catch (IOException a) {
      System.out.println("File to be cleared not found");
    }
  }
}
