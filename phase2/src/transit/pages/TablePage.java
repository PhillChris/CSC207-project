package transit.pages;

import java.util.ArrayList;
import java.time.LocalDate;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public abstract class TablePage extends Page {
  /** To be implemented by child classes */
  @Override
  abstract void makeScene(Stage primaryStage);

  /**
   * Makes a new column storing a String value
   *
   * @param title the title of this column
   * @param paramName the name of the parameter to be fetched by JavaFX
   * @return the table column storing the defined parameters
   */
  protected TableColumn makeNewStringColumn(String title, String paramName) {
    TableColumn tempColumn = new TableColumn(title);
    tempColumn.setCellValueFactory(new PropertyValueFactory<Row, String>(paramName));
    tempColumn.setMinWidth(200.0);
    return tempColumn;
  }

  /**
   * Makes a new column storing an Integer value
   *
   * @param title the title of this column
   * @param paramName the name of the parameter to be fetched by JavaFX
   * @return the table column storing the defined parameters
   */
   protected TableColumn makeNewIntColumn(String title, String paramName) {
    TableColumn tempColumn = new TableColumn(title);
    tempColumn.setCellValueFactory(new PropertyValueFactory<Row, Integer>(paramName));
    tempColumn.setMinWidth(200.0);
    return tempColumn;
  }

  /** @return a new empty table */
  protected TableView getTable() {
    TableView table = new TableView();
    table.setEditable(true);
    return table;
  }

  /**
   * Fetches data to be stored in this TablePage
   *
   * @param selectedDate the date which your data is to be retrieved
   * @return a list of the data rows to be represented in your TablePage
   */
  protected abstract ArrayList generateData(LocalDate selectedDate, int tableOption);

  /**
   * Creates the table to be displayed in this TablePage
   *
   * @param selectedDate the date which your data is to be retrieved
   * @return the table to be displayed
   */
  protected abstract TableView makeTable(LocalDate selectedDate, int tableOption);
}
