package transit.pages;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import transit.system.User;

public class UserAnalyticsPage extends AnalyticsPage {
  private User user;
  VBox content = new VBox();

  public UserAnalyticsPage(Stage stage, User user) {
//    super(stage, user.getCardCommands().getCardStatistics());
    super();
    this.statistics = user.getCardCommands().getCardStatistics();
    this.user = user;
    Scene scene = new Scene(layout, 800, 600);
    setLayout();
    stage.setScene(scene);
    stage.show();
    stage.setTitle("Transit System Simulator");
  }

  @Override
  void setLayout() {
    content.getChildren().setAll(new Label(user.getCardCommands().lastThreeTripsString()));
    content.getChildren().add(new Label(user.getCardCommands().getMostFrequentMessage()));
    layout.setRight(content);
    super.setLayout();
  }
}
