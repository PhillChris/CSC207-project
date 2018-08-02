package transit.pages;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import transit.system.AdminUser;
import transit.system.User;
import transit.system.UserRow;

/** Represents a page displaying all stats regarding users in this TransitSystem */
public class UserStatsPage extends Page {
  /** The admin accessing this UserStatsPage*/
  private AdminUser admin;
  /** The stats being displayed on this UserStatsPage*/
  private ArrayList<Label> userStats = new ArrayList<>();

  /**
   * Constructs a new UserStatsPage
   *
   * @param primaryStage the stage on which this UserStatsPage is shown
   * @param admin the adminUser tied to this
   */
  public UserStatsPage(Stage primaryStage, AdminUser admin) {
    this.admin = admin;
    makeScene(primaryStage);
  }

  /**
   * Constructs the scene which this page displays
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   */
  @Override
  void makeScene(Stage primaryStage) {
    placeLabel("User stats page", 0, 0);
    ChoiceBox<LocalDate> dateOptions = placeDateOptions(0, 1);
    TableView t = makeTable(dateOptions.getItems().get(0));
    dateOptions.setOnAction(e -> {
      t.setItems(FXCollections.observableList(generateUserData(dateOptions.getValue())));
    });
    placeButton(
        "Go back",
        () -> primaryStage.setScene(new AdminUserPage(primaryStage, admin).getScene()),
        0, 3);
    grid.add(t, 0, 2);
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  private TableView makeTable(LocalDate selectedDate) {
    TableView dataTable = new TableView();
    dataTable.setEditable(true);

    TableColumn usernameColumn = makeNewStringColumn("Username", "username");
    TableColumn tapsOnColumn = makeNewIntColumn("Taps In", "tapsIn");
    TableColumn tapsOffColumn = makeNewIntColumn("Taps Out","tapsOut");

    ArrayList<UserRow> userRows = generateUserData(selectedDate);
    ObservableList<UserRow> data = FXCollections.observableArrayList(userRows);

    dataTable.getColumns().addAll(usernameColumn, tapsOnColumn, tapsOffColumn);
    dataTable.setItems(data);

    return dataTable;
  }

  private TableColumn makeNewStringColumn(String title, String paramName) {
    TableColumn tempColumn = new TableColumn(title);
    tempColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>(paramName));
    tempColumn.setMinWidth(200.0);
    return tempColumn;
  }

  private TableColumn makeNewIntColumn(String title, String paramName) {
    TableColumn tempColumn = new TableColumn(title);
    tempColumn.setCellValueFactory(new PropertyValueFactory<UserRow, Integer>(paramName));
    tempColumn.setMinWidth(200.0);
    return tempColumn;
  }

  private ArrayList<UserRow> generateUserData(LocalDate selectedDate) {
    ArrayList<UserRow> temp = new ArrayList<>();
    for (User u : User.getAllUsersCopy().values()) {
      UserRow tempRow = new UserRow(u, selectedDate);
      temp.add(tempRow);
    }
    return temp;
  }
}
