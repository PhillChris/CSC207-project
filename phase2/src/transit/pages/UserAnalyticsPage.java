package transit.pages;

import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import transit.system.User;

public class UserAnalyticsPage extends AnalyticsPage {
  private User user;

  public UserAnalyticsPage(Stage stage, User user) {
    super(stage);
    this.user = user;
    this.statistics = user.getCardCommands().getCardStatistics();
    makeScene();
    stage.setScene(scene);
    stage.show();
    stage.setTitle("Transit System Simulator");
  }

  @Override
  void makeScene() {
    super.makeScene();
    Label lastThree = new Label(user.getCardCommands().lastThreeTripsString());
    lastThree.setMinWidth(375);
    GridPane.setValignment(lastThree, VPos.TOP);
    grid.add(lastThree, 1, 0);
    Label mostTapped = new Label(user.getCardCommands().mostFrequentStationMessage());
    grid.add(mostTapped, 2, 0);
    mostTapped.setMinWidth(100);
    GridPane.setValignment(mostTapped, VPos.TOP);
    super.makeScene();
  }
}
