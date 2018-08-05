package transit.pages;

import javafx.stage.Stage;
import transit.system.Statistics;

/** Represents a page used to show statistical data to admin users */
public class SystemGraphPage extends AnalyticsPage {

  /**
   * Initialized a new instance of AdminUserPage
   *
   * @param primaryStage The stage for this page to be displayed
   */
  public SystemGraphPage(Stage primaryStage) {
    super(primaryStage, Statistics.getSystemStatistics());
    title = "Monthly Revenue (Current Year)";
  }
}
