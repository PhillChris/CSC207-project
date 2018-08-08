package transit.pages;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/** Represents a general page serving scenes to be represented in the transit system program */
public abstract class Page {
  /** A page creator factory */
  protected PageCreator pageCreator = new PageCreator();
  /** The stage that this page will be displayed to */
  protected Stage stage;
  /** Represents the scene which this page serves */
  protected Scene scene;
  /** Represents the grid where elements on the page are placed */
  protected GridPane grid = new GridPane();
  /** A factory to produce Nodes */
  protected NodeFactory factory = new NodeFactory();

  /**
   * Sets the stage attribute
   *
   * @param stage The stage for this page to be viewed on
   */
  public Page(Stage stage) {
    this.stage = stage;
  }

  /** Places the the necessary elements to this page's scene */
  abstract void makeScene();

  /**
   * Sets a given title message to a stage and shows it
   */
  protected void setAndShow(String message) {
    stage.setScene(this.scene);
    stage.setTitle(message);
    stage.sizeToScene();
    stage.show();
  }
}
