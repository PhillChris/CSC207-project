package transit.pages;

import javafx.stage.Stage;
import transit.system.Statistics;

import java.util.HashMap;

/** Represents a page displaying a User's expenditure trends */
public class UserGraphPage extends AnalyticsPage {
  /** The user associated with this page */

  /**
   * Constructs a new UserGraphPage
   *
   * @param primaryStage the stage on which this page is being served
   */
  public UserGraphPage(Stage primaryStage, HashMap<String, Statistics> statistics) {
    super(primaryStage, statistics);
  }
}
