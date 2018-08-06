package transit.pages;

import transit.system.User;
import java.util.HashMap;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import transit.system.Statistics;

public class UserAnalyticsPage extends AnalyticsPage {
  private User user;
  private VBox content = new VBox();

  public UserAnalyticsPage(Stage stage, HashMap<String, Statistics> statistics, User user) {
    super(stage, statistics);
    this.user = user;
  }

  @Override
  void setLayout() {
    content.getChildren().setAll(new Label(user.getCardCommands().lastThreeTripsString()));
    content.getChildren().add(new Label(user.getCardCommands().getMostFrequentMessage()));
    super.setLayout();
  }
}
