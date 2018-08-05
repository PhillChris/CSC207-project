package transit.pages;

import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import transit.system.Statistics;
import transit.system.User;

/** Represents a page used to show statistical data to admin users */
public class AdminGraphPage extends GraphPage {

  private User user;

  private BorderPane layout = new BorderPane();
  /**
   * Initialized a new instance of AdminUserPage
   *
   * @param primaryStage The stage for this page to be displayed
   * @param user The user associated with this page
   */
  public AdminGraphPage(Stage primaryStage, User user) {
    this.user = user;
    makeScene(primaryStage);
  }

  @Override
  void makeScene(Stage stage) {
    stage.setTitle("Transit System Simulator");
    // the range of time you want the stats to cover
    ComboBox<String> timeOptions = setupTimeOptions();
    ComboBox<Statistics> statOptions = setupStatOptions(timeOptions);
    HBox dropDowns = new HBox();
    dropDowns.getChildren().addAll(timeOptions, statOptions);
    layout.setTop(dropDowns);
    this.scene = new Scene(layout, 800, 600);
  }

  public ComboBox<String> setupTimeOptions() {

    ComboBox<String> timeOptions = new ComboBox<>();

    timeOptions.getItems().addAll("Monthly", "Daily");
    timeOptions.getSelectionModel().select(0);
    return timeOptions;
  }

  public ComboBox<Statistics> setupStatOptions(ComboBox<String> timeOption) {
    ComboBox<Statistics> statOptions = new ComboBox<>();
    statOptions
            .getItems()
            .addAll(
                    Statistics.getSystemStatistics().get("SystemRevenue"),
                    Statistics.getSystemStatistics().get("SystemTripLengh"));
    statOptions.getSelectionModel().select(0);
    statOptions.setOnAction(
            actionEvent -> {
              if (timeOption.getValue().equals("Monthly")) {
                chart = makeYearChart(statOptions.getValue().generateMonthlyValues());
              } else {
                chart = makeWeekChart(statOptions.getValue().generateWeeklyValues());
              }
              layout.setCenter(chart);
            });
    return statOptions;
  }
}
