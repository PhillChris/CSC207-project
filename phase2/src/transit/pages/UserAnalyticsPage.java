package transit.pages;

import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import transit.system.User;

/**
 * Represents an analytics graph page in this transit system
 */
public class UserAnalyticsPage extends AnalyticsPage {

  /** The user whose statistics are being presented */
  private User user;

  /**
   * Constructs and displays a new UserAnalyticsPage
   *
   * @param stage the stage which this analytics page is being served on
   * @param user the user whose stats are being presented
   */
  public UserAnalyticsPage(Stage stage, User user) {
    super(stage);
    this.user = user;
    this.statistics = user.getCardCommands().getCardStatistics();
    makeScene();

    // set and show the given scene
    stage.setScene(scene);
    stage.show();
    stage.setTitle("Transit System Simulator");
  }

  @Override
  /** Makes the scene containing the content of this UserAnalyticsPage */
  void makeScene() {
    // Add and place the last three trips label
    Label lastThree = new Label(user.getCardCommands().lastThreeTripsString());
    lastThree.setMinWidth(375);
    GridPane.setValignment(lastThree, VPos.TOP);
    grid.add(lastThree, 1, 0);

    // Add and place the most frequently tapped station label
    Label mostTapped = new Label(user.getCardCommands().mostFrequentStationMessage());
    grid.add(mostTapped, 2, 0);
    mostTapped.setMinWidth(100);
    GridPane.setValignment(mostTapped, VPos.TOP);

    // Fetch the user analytics graph
    super.makeScene();
  }
}
