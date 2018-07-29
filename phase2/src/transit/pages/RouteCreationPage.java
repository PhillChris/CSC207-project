package transit.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;
import transit.system.Route;
import transit.system.Station;

import java.util.ArrayList;
import java.util.Stack;

/** Represents a page used to make maintenance changes to this system */
public class RouteCreationPage extends Page {
  /** A dropDownList of stations */
  private CheckComboBox<Station> dropDownList = new CheckComboBox<>();
  /** A checkbox indicating the type of route */
  private ChoiceBox<String> routeType = new ChoiceBox<>();

  public RouteCreationPage(Stage primaryStage) {
    makeScene(primaryStage);
  }

  @Override
  void makeScene(Stage primaryStage) {
    grid.setPadding(new Insets(30, 20, 20, 40));
    grid.setHgap(10);
    grid.setVgap(10);
    placeButton("Add subway route", () -> createSubwayRoute(), 0, 0);
    placeButton("Add bus route", () -> createBusRoute(), 0, 2);
    placeLabel("Append to Existing Station:", 0, 4);
    for (int i = 0; i < Route.getRoutesCopy().size(); i++){
      placeButton(Route.getRoutesCopy().get(i).toString(), () -> appendExistingRoute(), 0, 6 + 2*i);
    }

    this.scene = new Scene(grid, 600, 400);

  }

  private void createSubwayRoute(){

  }

  private void createBusRoute(){

  }

  private void appendExistingRoute(){

  }
}
