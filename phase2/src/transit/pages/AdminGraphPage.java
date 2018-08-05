package transit.pages;

import javafx.stage.Stage;
import transit.system.Statistics;

/** Represents a page used to show statistical data to admin users */
public class AdminGraphPage extends AnalyticsPage {

  /**
   * Initialized a new instance of AdminUserPage
   *
   * @param primaryStage The stage for this page to be displayed
   */
  public AdminGraphPage(Stage primaryStage) {
    super(primaryStage, Statistics.getSystemStatistics());
  }
}
