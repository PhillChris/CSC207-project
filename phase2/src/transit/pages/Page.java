package transit.pages;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/** Represents a general page serving scenes to be represented in the transit system program */
public abstract class Page {
  /** The stage which the */
  protected Stage stage;
  /** Represents the scene which this page serves */
  protected Scene scene;
  /** Represents the grid where elements on the page are placed */
  protected GridPane grid = new GridPane();
  /** The title of any window of this page */
  protected String title;
  /**
   * A factory to produce Nodes
   */
  protected NodeFactory factory = new NodeFactory();

  /**
   * A constructor that sets the primary stages close action to end the program.
   *
   * @param primaryStage the PRIMARY stage of this application.
   */
  public Page(Stage primaryStage) {
    this.stage = primaryStage;
  }

  /**
   * Places the the necessary elements to this page's scene
   *
   * @param primaryStage the stage which this scene is being served on, passed for button-action
   *     purposes
   */
  abstract void makeScene();

}
