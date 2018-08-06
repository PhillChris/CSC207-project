package transit.pages;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import transit.system.User;

public class UserAnalyticsPage extends AnalyticsPage {
  private User user;
  private VBox content = new VBox();

  public UserAnalyticsPage(Stage stage, User user) {
    super(stage, user.getCardCommands().getCardStatistics());
    this.user = user;
  }

  @Override
  void setLayout() {
    content.getChildren().setAll(new Label(user.getCardCommands().lastThreeTripsString()));
    content.getChildren().add(new Label(user.getCardCommands().getMostFrequentMessage()));
    super.setLayout();
  }
}
