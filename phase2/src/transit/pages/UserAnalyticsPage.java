package transit.pages;

import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import transit.system.User;

public class UserAnalyticsPage extends AnalyticsPage {
  private User user;

  public UserAnalyticsPage(Stage stage, User user) {
    super(stage, user.getCardCommands().getCardStatistics());
    this.user = user;
//    setLayout();
//    stage.setScene(scene);
//    stage.show();
//    stage.setTitle("Transit System Simulator");
  }

  @Override
  void setLayout() {
    super.setLayout();
    Label lastThree = new Label(user.getCardCommands().lastThreeTripsString());
    lastThree.setMinWidth(375);
    GridPane.setValignment(lastThree, VPos.TOP);
    layout.add(lastThree, 1, 0);
    Label mostTapped = new Label(user.getCardCommands().mostFrequentStationMessage());
    layout.add(mostTapped, 2, 0);
    mostTapped.setMinWidth(100);
    GridPane.setValignment(mostTapped, VPos.TOP);
  }
}
