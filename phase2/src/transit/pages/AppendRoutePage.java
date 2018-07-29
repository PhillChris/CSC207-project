package transit.pages;

import javafx.stage.Stage;
import transit.system.Route;
import transit.system.Station;

/**
 * Page used to add new stations to a route
 * */
public class AppendRoutePage extends Page {
    private Route route;

    public AppendRoutePage(Stage stage, Route route){
        this.route = route;
        makeScene(stage);
    }

    @Override
    void makeScene(Stage primaryStage) {

    }
}
