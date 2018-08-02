package transit.pages;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import transit.system.User;
import transit.system.UserRow;

/** Represents a page displaying all stats regarding users in this TransitSystem */
public class UserTablePage extends TablePage {
  /** The admin accessing this UserTablePage */
  private User admin;
  /** The stats being displayed on this UserTablePage */
  private ArrayList<Label> userStats = new ArrayList<>();

  /**
   * Constructs a new UserTablePage
   *
   * @param primaryStage the stage on which this UserTablePage is shown
   * @param admin the adminUser tied to this
   */
  public UserTablePage(Stage primaryStage, User admin) {
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
    placeLabel("User statistics!", 0, 0);
    ChoiceBox<LocalDate> dateOptions = placeDateOptions(0, 1);
    TableView t = makeTable(dateOptions.getItems().get(0), 0);
    dateOptions.setOnAction(
        e -> {
          t.setItems(FXCollections.observableList(generateData(dateOptions.getValue(), 0)));
        });
    placeButton(
        "Go back",
        () -> primaryStage.setScene(new AdminUserPage(primaryStage, admin).getScene()),
        0,
        3);
    grid.add(t, 0, 2);
    this.scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

    // Associate CSS with this page
    this.scene
        .getStylesheets()
        .add(UserTablePage.class.getResource("styling/TableRows.css").toExternalForm());
  }

  /**
   * Creates a new table of statistics for the UserStats page
   *
   * @param selectedDate the current date in the program
   * @return a table containing the
   */
  protected TableView makeTable(LocalDate selectedDate, int tableOption) {
    TableView dataTable = getTable();

    TableColumn usernameColumn = makeNewStringColumn("Username", "name");
    TableColumn tapsOnColumn = makeNewIntColumn("Taps In", "tapsIn");
    TableColumn tapsOffColumn = makeNewIntColumn("Taps Out", "tapsOut");

    ArrayList<UserRow> userRows = generateData(selectedDate, 0);
    ObservableList<UserRow> data = FXCollections.observableArrayList(userRows);

    dataTable.getColumns().addAll(usernameColumn, tapsOnColumn, tapsOffColumn);
    dataTable.setItems(data);

    return dataTable;
  }



  /** Generates all of the user rows for use in the UserTablePage
   *
   * @param selectedDate the date selected for data retrieval
   * @return the rows of data to be represented in this table
   */
  protected ArrayList generateData(LocalDate selectedDate, int tableOption) {
    ArrayList<UserRow> tempList = new ArrayList<>();
    if (tableOption == 0){
      for (User u : User.getAllUsersCopy().values()) {
        UserRow tempRow = new UserRow(u, selectedDate);
        tempList.add(tempRow);
      }
    }
    return tempList;
  }
}
