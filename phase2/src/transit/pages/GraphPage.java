package transit.pages;

import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import transit.system.Statistics;

public abstract class GraphPage {
  /** The chart displayed by this page */
  protected LineChart<String, Number> chart;
  /** The layout of this page */
  protected BorderPane layout = new BorderPane();
  /** A factory to construct graphs */
  protected GraphFactory graphFactory = new GraphFactory();
  /** The drop down options displayed by this page */
  HBox dropDowns = new HBox();
  /** A combo box if the different statistics options for this page */
  ComboBox<Statistics> statOptions = new ComboBox<>();
  /** The time options displayed by this page */
  ComboBox<String> timeOptions = new ComboBox<>();
}